package com.codenjoy.clientrunner.service;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.clientrunner.config.DockerConfig;
import com.codenjoy.clientrunner.dto.SolutionSummary;
import com.codenjoy.clientrunner.exception.SolutionNotFoundException;
import com.codenjoy.clientrunner.model.LogType;
import com.codenjoy.clientrunner.model.Solution;
import com.codenjoy.clientrunner.model.Token;
import com.codenjoy.clientrunner.service.facade.DockerService;
import com.codenjoy.clientrunner.service.facade.LogWriter;
import com.github.dockerjava.api.model.HostConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.codenjoy.clientrunner.model.Solution.Status.*;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolutionManager {

    public static final String DOCKERFILE = "Dockerfile";
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    private final DockerConfig config;
    private HostConfig hostConfig;
    private final DockerService docker;
    private final Set<Solution> solutions = ConcurrentHashMap.newKeySet();

    @PostConstruct
    protected void init() {
        hostConfig = HostConfig.newHostConfig()
                .withCpuPeriod(config.getContainer().getCpuPeriod())
                .withCpuQuota(config.getContainer().getCpuQuota())
                .withMemory(config.getContainer().getMemoryLimitBytes());
    }

    public int runSolution(Token token, File sources) {
        getSolutions(token).forEach(this::kill);

        Solution solution = Solution.from(token, sources);
        solution.setId(idGenerator.incrementAndGet());
        solutions.add(solution);

        /* TODO: try to avoid copy Dockerfile.
            https://docs.docker.com/engine/api/v1.41/#operation/ImageBuild */
        addDockerfile(solution);

        // TODO this is multithreading crunch, to use synchronized section
        if (!solution.getStatus().isActive()) {
            log.debug("Attempt to run inactive solution with id: {} and status: {}",
                    solution.getId(), solution.getStatus());
            return solution.getId();
        }

        try {
            solution.setStatus(COMPILING);
            docker.buildImage(sources,
                    solution.getGameToRun(),
                    solution.getServerUrl(),
                    new LogWriter(solution, true),
                    imageId -> runContainer(solution, imageId));
        } catch (Throwable e) {
            if (solution.getStatus() != KILLED) {
                solution.setStatus(ERROR);
            }
        }

        return solution.getId();
    }

    public void killAll(Token token) {
        getSolutions(token).forEach(this::kill);
    }

    public void kill(Token token, int solutionId) {
        Solution solution = getSolution(token, solutionId)
                .orElseThrow(() -> new SolutionNotFoundException(solutionId));
        kill(solution);
    }

    public SolutionSummary getSolutionSummary(Token token, int solutionId) {
        return getSolution(token, solutionId)
                .map(SolutionSummary::new)
                .orElseThrow(() -> new SolutionNotFoundException(solutionId));
    }

    public List<SolutionSummary> getAllSolutionSummary(Token token) {
        return getSolutions(token).stream()
                .map(SolutionSummary::new)
                .sorted(Comparator.comparingInt(SolutionSummary::getId))
                .collect(toList());
    }

    public List<String> getLogs(Token token, int solutionId, LogType logType, int offset) {
        Solution solution = getSolution(token, solutionId)
                .orElseThrow(() -> new SolutionNotFoundException(solutionId));

        if (!logType.existsWhen(solution.getStatus())) {
            return Collections.emptyList();
        }

        return readLogs(solution, logType, offset);
    }

    private List<Solution> getSolutions(Token token) {
        return solutions.stream()
                .filter(s -> s.allows(token))
                .collect(toList());
    }

    private Optional<Solution> getSolution(Token token, int solutionId) {
        return getSolutions(token).stream()
                .filter(s -> s.getId() == solutionId)
                .findFirst();
    }

    private void runContainer(Solution solution, String imageId) {
        if (solution.getStatus() == KILLED) {
            log.info("Attempt to run killed solution with id: {}", solution.getId());
            return;
        }
        solution.setImageId(imageId);
        solution.setStatus(RUNNING);
        solution.setStarted(LocalDateTime.now());

        String containerId = docker.createContainer(imageId, hostConfig);
        solution.setContainerId(containerId);

        docker.startContainer(solution.getContainerId());

        docker.logContainer(solution.getContainerId(),
                new LogWriter(solution, false));

        docker.waitContainer(solution.getContainerId(),
                () -> cleanupSolution(solution));
    }

    private void cleanupSolution(Solution solution) {
        solution.finish();
        docker.removeContainer(solution.getContainerId());
        // TODO: remove images
    }

    private void kill(Solution solution) {
        if (!solution.getStatus().isActive()) {
            return;
        }
        solution.setStatus(KILLED);
        if (solution.getContainerId() != null) {
            docker.killContainer(solution.getContainerId());
        }
    }

    private void addDockerfile(Solution solution) {
        String platformFolder = solution.getPlatform().getFolderName();
        try {
            File destination = new File(solution.getSources(), DOCKERFILE);
            String path = config.getDockerfilesFolder() + "/" + platformFolder + "/" + DOCKERFILE;
            URL url = getClass().getResource(path);
            FileUtils.copyURLToFile(url, destination);
        } catch (IOException e) {
            log.error("Can not add Dockerfile to solution with id: {}", solution.getId());
            solution.setStatus(ERROR);
        }
    }

    private List<String> readLogs(Solution solution, LogType type, int offset) {
        String logFilePath = solution.getSources() + "/" + type.getFilename();
        try (Stream<String> log =
                     Files.lines(Paths.get(logFilePath),
                        StandardCharsets.UTF_8)) {
            return log.skip(offset)
                    .collect(toList());
        } catch (IOException e) {
            log.debug("Solution with id: '{}' is in '{}' status, therefore log file '{}' not exists",
                    solution.getId(),
                    solution.getStatus(),
                    logFilePath);
            return Arrays.asList();
        }
    }

    // for testing only
    void clear() {
        solutions.clear();
    }
}

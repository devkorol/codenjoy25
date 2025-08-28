package com.codenjoy.clientrunner;

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

import com.codenjoy.clientrunner.dto.CheckRequest;
import com.codenjoy.clientrunner.dto.SolutionSummary;
import com.codenjoy.clientrunner.model.LogType;
import com.codenjoy.clientrunner.service.ClientRunnerService;
import com.codenjoy.clientrunner.service.SolutionManager;
import com.codenjoy.clientrunner.service.facade.DockerService;
import com.codenjoy.clientrunner.service.facade.GitService;
import lombok.SneakyThrows;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

import static com.codenjoy.clientrunner.model.Solution.Status.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@SpringBootTest(classes = ClientRunnerApplication.class,
        properties = "spring.main.allow-bean-definition-overriding=true")
@ContextConfiguration(initializers = IntegrationTest.Initializer.class)
public class IntegrationTest extends AbstractTestNGSpringContextTests {

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            if (SystemUtils.IS_OS_WINDOWS) {
                System.setProperty("DOCKER_HOST", "tcp://localhost:2375");
            }
        }
    }

    @SpyBean
    private DockerService docker;

    @SpyBean
    private GitService git;

    @SpyBean
    private SolutionManager solutionManager;

    @Autowired
    private ClientRunnerService service;

    private List<SolutionSummary> solutions;
    private List<String> logs;

    @Test
    @Ignore // TODO fix me
    @SneakyThrows
    public void shouldCheckTwoSolutions_forSameServerUrlAndRepo() {
        String serverUrl = "http://5.189.144.144/codenjoy-contest/board/player/0?code=000000000000";
        String repo = "https://github.com/codenjoyme/codenjoy-javascript-client.git";

        // given empty solutions at start
        refreshSolutions(serverUrl);
        assertEquals(0, solutions.size());

        // when try to check one solution
        createSolution(serverUrl, repo);
        waitForBuildSolution(serverUrl, 1);

        // then new solution in RUNNING mode
        assertThat(solution(0)).hasId(1).inStatus(RUNNING);

        // when run another solution for same player/code
        createSolution(serverUrl, repo);
        waitForBuildSolution(serverUrl, 2);

        // then one was FINISHED
        assertThat(solution(0)).hasId(1).inStatus(FINISHED);
        // another one is still RUNNING
        assertThat(solution(1)).hasId(2).inStatus(RUNNING);

        // when get RUNNING solution
        SolutionSummary solution = service.getSolutionSummary(serverUrl, 2);
        assertThat(solution).hasId(2).inStatus(RUNNING);

        // when get FINISHED solution
        solution = service.getSolutionSummary(serverUrl, 1);
        assertThat(solution).hasId(1).inStatus(FINISHED);

        // when get build logs of RUNNING solution
        readBuildLogs(serverUrl, 2);
        assertLastLineStartsWith("Successfully built");

        // when get running logs of RUNNING solution
        readRuntimeLogs(serverUrl, 2);
        assertFistLineStartsWith("STDOUT: Got url from Environment: " + serverUrl);

        // when get build logs of KILLED solution
        readBuildLogs(serverUrl, 1);
        assertLastLineStartsWith("Successfully built");

        // when get running logs of KILLED solution
        readRuntimeLogs(serverUrl, 1);
        assertFistLineStartsWith("STDOUT: Got url from Environment: " + serverUrl);

        // when kill RUNNING solution
        service.killSolution(serverUrl, 2);
        refreshSolutions(serverUrl);

        // then one was FINISHED
        assertThat(solution(0)).hasId(1).inStatus(FINISHED);
        // another one is also FINISHED
        assertThat(solution(1)).hasId(2).inStatus(FINISHED);
    }

    @SneakyThrows
    private void readRuntimeLogs(String serverUrl, int solutionId) {
        do {
            logs = service.getLogs(serverUrl, solutionId, LogType.RUNTIME, 0);
            Thread.sleep(1000);
        } while (logs.isEmpty());
    }

    @SneakyThrows
    private void readBuildLogs(String serverUrl, int solutionId) {
        do {
            logs = service.getLogs(serverUrl, solutionId, LogType.BUILD, 0);
            Thread.sleep(1000);
        } while (logs.isEmpty());
    }

    private void assertFistLineStartsWith(String prefix) {
        assertTrue(logs.get(0).startsWith(prefix));
    }

    private void assertLastLineStartsWith(String prefix) {
        assertTrue(logs.get(logs.size() - 1).startsWith(prefix));
    }

    private AssertSolution assertThat(SolutionSummary solution) {
        return new AssertSolution(solution);
    }

    private void refreshSolutions(String serverUrl) {
        solutions = service.getAllSolutionsSummary(serverUrl);
    }

    private SolutionSummary solution(int index) {
        return solutions.get(index);
    }

    private void createSolution(String serverUrl, String repo) {
        service.checkSolution(new CheckRequest() {{
            setRepo(repo);
            setServerUrl(serverUrl);
        }});
    }

    private void waitForBuildSolution(String serverUrl, int total) throws InterruptedException {
        do {
            Thread.sleep(1000);
            refreshSolutions(serverUrl);
            if (solutions.size() != total) continue;
        } while (!RUNNING.name().equals(last().getStatus()));
    }

    private SolutionSummary last() {
        return solutions.get(solutions.size() - 1);
    }
}

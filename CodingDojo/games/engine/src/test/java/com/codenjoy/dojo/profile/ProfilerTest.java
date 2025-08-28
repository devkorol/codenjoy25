package com.codenjoy.dojo.profile;

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

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicLong;

import static com.codenjoy.dojo.client.Utils.clean;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class ProfilerTest {

    private AtomicLong time;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setup() {
        P.reset();

        time = new AtomicLong(1);
        P.p.getTime = () -> time.addAndGet(0) * 1000L;

        outputStream = new ByteArrayOutputStream();
        P.p.OUT = new PrintStream(outputStream);
    }

    @SneakyThrows
    private void assertOutput(String expected) {
        assertEquals(expected, clean(outputStream.toString(UTF_8)));
    }

    private void tick() {
        time.incrementAndGet();
    }

    @Test
    public void testSameIntervals() {
        // when
        P.start();

        tick();
        P.done("phase1");

        tick();
        P.done("phase2");

        tick();
        P.done("phase3");

        // then
        P.print();
        assertOutput(
                "phase1 = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "phase2 = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "phase3 = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testDifferentIntervals() {
        // when
        P.start();

        tick();
        P.done("phase1");

        tick();
        tick();
        P.done("phase2");

        tick();
        tick();
        tick();
        P.done("phase3");

        // then
        P.print();
        assertOutput(
                "phase1 = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "phase2 = AVG{count:      1, time:   2000, average: 2000.00}\n" +
                "phase3 = AVG{count:      1, time:   3000, average: 3000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testRepeatOneInterval() {
        // when
        P.start();

        tick();
        P.done("phase1");

        tick();
        tick();
        P.done("phase2");

        tick();
        tick();
        tick();
        P.done("phase1");

        // then
        P.print();
        assertOutput(
                "phase1 = AVG{count:      2, time:   4000, average: 2000.00}\n" +
                "phase2 = AVG{count:      1, time:   2000, average: 2000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testRepeatOneInterval_append() {
        // when
        P.start();

        P.beginCycle("preffix");

        tick();
        P.done("phase1");

        tick();
        tick();
        P.done("phase2");

        tick();
        tick();
        tick();
        P.done("phase1");

        P.endCycle();

        // then
        P.print();
        assertOutput(
                "preffix.phase1 = AVG{count:      1, time:   4000, average: 4000.00}\n" +
                "preffix.phase2 = AVG{count:      1, time:   2000, average: 2000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testRepeatOneInterval_appendInCycle() {
        // when
        P.start();

        tick();
        P.done("phase1");

        for (int outer = 0; outer < 2; outer++) {
            P.beginCycle("preffix");
            for (int inner = 0; inner < 10; inner++) {
                tick();
                P.done("phase2");

                tick();
                P.done("phase1");
            }
            P.endCycle();
        }

        // then
        P.print();
        assertOutput(
                "phase1         = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "preffix.phase2 = AVG{count:      2, time:  20000, average: 10000.00}\n" +
                "preffix.phase1 = AVG{count:      2, time:  20000, average: 10000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testRepeatOneInterval_appendInCycle_case2() {
        // when
        P.start();

        tick();
        P.done("start");

        P.beginCycle("preffix");
        for (int outer = 0; outer < 2; outer++) {
            tick();
            P.done("before");

            for (int inner = 0; inner < 10; inner++) {
                tick();
                P.done("phase2");

                tick();
                P.done("phase1");
            }

            tick();
            P.done("after");
        }
        P.endCycle();

        // then
        P.print();
        assertOutput(
                "start          = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "preffix.before = AVG{count:      1, time:   2000, average: 2000.00}\n" +
                "preffix.phase2 = AVG{count:      1, time:  20000, average: 20000.00}\n" +
                "preffix.phase1 = AVG{count:      1, time:  20000, average: 20000.00}\n" +
                "preffix.after  = AVG{count:      1, time:   2000, average: 2000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testRepeatOneInterval_appendInCycle_case3() {
        // when
        P.start();

        tick();
        P.done("start");

        for (int outer = 0; outer < 2; outer++) {
            P.beginCycle("preffix" + (outer + 1));

            for (int inner = 0; inner < 10; inner++) {
                tick();
                P.done("phase2");

                tick();
                P.done("phase1");
            }

            P.endCycle();
        }

        // then
        P.print();
        assertOutput(
                "start           = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "preffix1.phase2 = AVG{count:      1, time:  10000, average: 10000.00}\n" +
                "preffix1.phase1 = AVG{count:      1, time:  10000, average: 10000.00}\n" +
                "preffix2.phase2 = AVG{count:      1, time:  10000, average: 10000.00}\n" +
                "preffix2.phase1 = AVG{count:      1, time:  10000, average: 10000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testPadPhaseName() {
        // when
        P.start();

        tick();
        P.done("1");

        tick();
        P.done("phase2");

        tick();
        P.done("phase3-long-string");

        // then
        P.print();
        assertOutput(
                "1                  = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "phase2             = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "phase3-long-string = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "--------------------------------------------------\n");
    }

    @Test
    public void testOnePhase() {
        // when
        P.start();

        tick();
        P.done("phase1");

        // then
        P.print();
        assertOutput(
                "phase1 = AVG{count:      1, time:   1000, average: 1000.00}\n" +
                "--------------------------------------------------\n");
    }
}

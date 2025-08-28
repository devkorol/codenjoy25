package com.codenjoy.dojo.services.generator.manual;

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

import com.codenjoy.dojo.utils.RedirectOutput;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static com.codenjoy.dojo.services.generator.manual.ManualGeneratorTest.pathInsideCodingDojo;
import static com.codenjoy.dojo.services.generator.manual.ManualGeneratorTest.skipTestWarning;
import static com.codenjoy.dojo.utils.SmokeUtils.assertSmokeEquals;

@Slf4j // because logger prints to console some data that we don't need at the assertEquals phase
public class ManualGeneratorRunnerTest {

    @Rule
    public TestName test = new TestName();

    private RedirectOutput output = new RedirectOutput();

    @Test
    public void shouldGenerate_allGames_andLanguages() {
        // given
        output.redirect();
        ManualGenerator.READONLY = true;

        try {
            // when
            ManualGeneratorRunner.main(new String[]{
                    "..",
                    "mollymage,namdreab,rawelbbub,sample,sampletext,verland,tetris",
                    "ALL"});

            // then
            String actual = output.toString();
            output.rollback();
            if (pathInsideCodingDojo() == null) {
                skipTestWarning();
                return;
            }
            assertEquals(actual);
        } finally {
            // when maven run tests it uses same test class instance for each test
            ManualGenerator.READONLY = false;
        }
    }

    private void assertEquals(String actual) {
        assertSmokeEquals(actual, getClass(), test.getMethodName());
    }
}
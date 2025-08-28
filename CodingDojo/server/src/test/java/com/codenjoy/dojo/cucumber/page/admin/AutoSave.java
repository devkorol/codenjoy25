package com.codenjoy.dojo.cucumber.page.admin;

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

import com.codenjoy.dojo.cucumber.page.PageObject;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.codenjoy.dojo.cucumber.utils.Assert.assertEquals;
import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@RequiredArgsConstructor
public class AutoSave extends PageObject {

    public WebElement status() {
        return web.element("#autoSave td b");
    }

    private WebElement stopButton() {
        return web.button("#autoSave", actions.stopAutoSave);
    }

    private WebElement startButton() {
        return web.button("#autoSave", actions.startAutoSave);
    }

    public void assertSuspended() {
        assertEquals("The auto save was suspended", status().getText());
        assertEquals(actions.startAutoSave, startButton().getAttribute("value"));
    }

    public void assertStarted() {
        assertEquals("The auto save in progress", status().getText());
        assertEquals(actions.stopAutoSave, stopButton().getAttribute("value"));
    }

    public void stop() {
        stopButton().click();
    }

    public void start() {
        startButton().click();
    }
}

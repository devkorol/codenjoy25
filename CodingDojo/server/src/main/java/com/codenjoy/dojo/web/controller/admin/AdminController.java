package com.codenjoy.dojo.web.controller.admin;

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


import com.codenjoy.dojo.services.AdminService;
import com.codenjoy.dojo.services.GameService;
import com.codenjoy.dojo.services.Player;
import com.codenjoy.dojo.services.PlayerService;
import com.codenjoy.dojo.services.security.GameAuthoritiesConstants;
import com.codenjoy.dojo.services.security.ViewDelegationService;
import com.codenjoy.dojo.web.controller.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(AdminController.URI)
@Secured(GameAuthoritiesConstants.ROLE_ADMIN)
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    public static final String URI = "/admin";
    public static final String CUSTOM_ADMIN_PAGE_KEY = "custom";

    private final PlayerService playerService;
    private final GameService gameService;
    private final ViewDelegationService viewDelegationService;
    private final AdminService adminService;

    // используется как rest для обновления конкретного поля заданного player на admin page
    @PostMapping("/player")
    public @ResponseBody String update(@RequestBody Player player) {
        try {
            playerService.update(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @PostMapping()
    public String saveSettings(AdminSettings settings,
                               BindingResult result,
                               HttpServletRequest request)
    {
        if (!result.hasErrors()) {
            // do nothing
        }

        adminService.saveSettings(settings);

        String room = settings.getRoom();
        request.setAttribute("room", room);
        return getAdmin(room);
    }

    private String getAdmin(String room) {
        if (room == null) {
            return getAdmin();
        }
        return "redirect:/admin?" + "room" + "=" + room;
    }

    private String getAdmin() {
        return getAdmin(gameService.getDefaultRoom());
    }

    @GetMapping()
    public String getAdmin(Model model,
                           @RequestParam(value = "room", required = false) String room,
                           @RequestParam(value = "game", required = false) String game,
                           @RequestParam(value = CUSTOM_ADMIN_PAGE_KEY, required = false, defaultValue = "false")
                           boolean gameSpecificAdminPage)
    {
        // каждый из этих параметров может быть null, "", "null"
        room = Validator.isEmpty(room) ? null : room;
        game = Validator.isEmpty(game) ? null : game;

        AdminSettings data = adminService.loadAdminPage(game, room);

        if (data == null) {
            return getAdmin();
        }

        // проверяем не надо ли нам перейти на custom страничку
        if (gameSpecificAdminPage && data.getGame() != null) {
            return viewDelegationService.adminView(game);
        }

        model.addAttribute("data", data);
        return "admin";
    }
}
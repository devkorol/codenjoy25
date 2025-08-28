package com.codenjoy.dojo.services.security;

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

import com.codenjoy.dojo.services.dao.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Сервис отвечает за поиск юзера по его email со стороны SpringSecurity.
 * Скажем, если во время авторизации (Basic auth) в Postman для какого-то get запроса
 * передается Username/Password то Spring захочет получить инфу про юзера через этот метод,
 * а уже внутри себя проверит Password (который, к слову, md5(realPassword) должен быть).
 */
@Component
@RequiredArgsConstructor
public class GameUserDetailsService implements UserDetailsService {

    private final Registration registration;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return registration.getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email '%s' does not exist", email)));
    }
}

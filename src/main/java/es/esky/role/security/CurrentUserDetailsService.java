/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.esky.role.security;

import es.esky.role.users.api.exception.UserNotFoundException;
import es.esky.role.users.domain.User;
import es.esky.role.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Load user data from a {@link UsersService}.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class CurrentUserDetailsService implements UserDetailsService {

    private final UsersService usersService;

    /**
     * Constructor of CurrentDetailsService.
     *
     * @param usersService Provide the way to get user data.
     */
    @Autowired
    public CurrentUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user;
        try {
            user = usersService.findByUsername(username);
        } catch (UserNotFoundException e) {
            String error = String.format("User %s was not found.", username);
            throw new UsernameNotFoundException(error, e);
        }
        return new CurrentUserDetails(user);
    }
}

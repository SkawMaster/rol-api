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

package es.esky.rol.users.service;

import es.esky.rol.users.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Provide users data.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public interface UsersService {

    /**
     * Find a users page by a criteria and a page request.
     *
     * @param page Page request.
     * @return Users page.
     */
    Page<User> findByCriteria(Pageable page);

    /**
     * Find a user by its username
     * <p>
     * Can throw a {@link es.esky.rol.users.api.exception.UserNotFoundException} if user not exist.
     * </p>
     *
     * @param username User username
     * @return User
     */
    User findByUsername(String username);

    /**
     * Delete a user by its username.
     * <p>
     * Can throw a {@link es.esky.rol.users.api.exception.UserNotFoundException} if user not exist.
     * </p>
     *
     * @param username User username
     */
    void deleteByUsername(String username);

    /**
     * Save a new user.
     *
     * @param user New user data.
     * @return User after save it.
     */
    User save(User user);

    /**
     * Return if exist a user with the username.
     *
     * @param username User username
     * @return True if exist, otherwise false.
     */
    boolean exists(String username);
}

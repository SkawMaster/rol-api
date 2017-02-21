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

package es.esky.rol.users.domain.builder;

import es.esky.rol.users.domain.User;

/**
 * User instance builder.
 */
public class UserBuilder {

    private User user;

    private UserBuilder() {
        this.user = new User();
    }

    /**
     * Instance a new user builder.
     *
     * @return New user builder.
     */
    public static UserBuilder user() {
        return new UserBuilder();
    }

    /**
     * Set a username for new user.
     *
     * @param username New username.
     * @return User builder.
     */
    public UserBuilder withUsername(String username) {
        user.setUsername(username);
        return this;
    }

    /**
     * Set a password for new user.
     *
     * @param password New password.
     * @return User builder
     */
    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    /**
     * Builds the user.
     *
     * @return A user built.
     */
    public User build() {
        return this.user;
    }
}

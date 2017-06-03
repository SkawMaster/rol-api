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

package es.esky.role.users.domain.builder;

import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import es.esky.role.users.domain.User;

/**
 * Help to build a new {@link User} instance.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class UserBuilder {
	private final User user;

	private UserBuilder() {
		this.user = new User();
	}

	/**
	 * Construct a new {@link UserBuilder} instance.
	 *
	 * @return New user builder.
	 * @since 1.0.0
	 */
	public static UserBuilder user() {
		return new UserBuilder();
	}

	/**
	 * Set username to the building user.
	 *
	 * @param username Username for the current building user.
	 * @return Current {@link UserBuilder} instance.
	 * @since 1.0.0
	 */
	public UserBuilder withUsername(@NotNull String username) {
		Assert.notNull(username, "Username must not be null");
		this.user.setUsername(username);
		return this;
	}

	/**
	 * Set password to the building user.
	 *
	 * @param password Password for the current building user.
	 * @return Current {@link UserBuilder} instance.
	 * @since 1.0.0
	 */
	public UserBuilder withPassword(@NotNull String password) {
		Assert.notNull(password, "Password must not be null");
		this.user.setPassword(password);
		return this;
	}

	/**
	 * Build the user instance.
	 *
	 * @return The {@link User} build instance.
	 * @since 1.0.0
	 */
	public User build() {
		return this.user;
	}
}

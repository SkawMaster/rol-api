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

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import es.esky.role.users.api.exception.UserNotFoundException;
import es.esky.role.users.domain.User;
import es.esky.role.users.service.UsersService;

/**
 * Load user data from {@link UsersService}.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class CurrentUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CurrentUserDetailsService.class);

	private final UsersService usersService;

	/**
	 * Create a new instance.
	 *
	 * @param usersService Provide the way to get user data.
	 */
	@Autowired
	public CurrentUserDetailsService(@NotNull UsersService usersService) {
		Assert.notNull(usersService, "UsersService must not be null");
		this.usersService = usersService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user;
		try {
			user = this.usersService.findByUsername(username);
			logger.info("Trying login {} user", username);
		} catch (UserNotFoundException e) {
			String error = String.format("User %s was not found.", username);
			throw new UsernameNotFoundException(error, e);
		}
		return new CurrentUserDetails(user);
	}
}

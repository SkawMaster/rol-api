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

package es.esky.role.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.esky.role.users.domain.User;

/**
 * Provide methods to work with {@link User} entities.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public interface UsersService {
	/**
	 * Find a users page by a page info request.
	 *
	 * @param page Page request.
	 * @return Users page.
	 * @since 1.0.0
	 */
	Page<User> findByCriteria(Pageable page);

	/**
	 * Find a user by an username
	 *
	 * @param username User username
	 * @return User
	 * @throws es.esky.role.users.api.exception.UserNotFoundException if user not exist.
	 * @since 1.0.0
	 */
	User findByUsername(String username);

	/**
	 * Save a new user.
	 *
	 * @param user New user data.
	 * @return User after save it.
	 * @since 1.0.0
	 */
	User save(User user);
}

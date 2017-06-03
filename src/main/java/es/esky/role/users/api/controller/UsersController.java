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

package es.esky.role.users.api.controller;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.esky.role.users.domain.User;
import es.esky.role.users.service.UsersService;

/**
 * User resource controller.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/users")
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	private final UsersService usersService;

	/**
	 * Construct a new instance.
	 *
	 * @param usersService Users service.
	 * @since 1.0.0
	 */
	@Autowired
	public UsersController(@NotNull UsersService usersService) {
		Assert.notNull(usersService, "UsersService must not be null");
		this.usersService = usersService;
	}

	/**
	 * Find a page of users by a criteria.
	 *
	 * @param page Requested page.
	 * @return Page requested.
	 * @since 1.0.0
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<User> findByCriteria(Pageable page) {
		Page<User> users = usersService.findByCriteria(page);
		logger.info("Found users {} on page {} with size {}", CollectionUtils.collect(users, User::getUsername), page
				.getPageNumber(), page.getPageSize());
		return users;
	}
}

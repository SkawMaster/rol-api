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

package es.esky.rol.users.api.controller;

import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User resource controller.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = UsersController.BASE_URL)
public class UsersController {
    static final String BASE_URL = "/users";

    private final UsersService usersService;

    /**
     * Constructor of UsersController.
     *
     * @param usersService Users service.
     */
    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Find a page of users by a criteria.
     *
     * @param page               Requested page.
     * @param resourcesAssembler Link assembler.
     * @return Page requested.
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<User> findByCriteria(Pageable page, PagedResourcesAssembler<User> resourcesAssembler) {
        return usersService.findByCriteria(page);
    }
}

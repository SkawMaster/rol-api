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

import es.esky.rol.arch.domain.ApiError;
import es.esky.rol.arch.pagination.PaginationUtils;
import es.esky.rol.users.api.exception.UserNotFoundException;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * User resource controller.
 */
@RestController
@RequestMapping(value = UsersController.BASE_URL)
class UsersController {
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
    public ResponseEntity findByCriteria(Pageable page, PagedResourcesAssembler<User> resourcesAssembler) {
        Page<User> userPage = usersService.findByCriteria(page);
        Link link = linkTo(methodOn(UsersController.class).findByCriteria(page, resourcesAssembler)).withSelfRel();
        return PaginationUtils.buildResponse(userPage, resourcesAssembler, link);
    }

    /**
     * Find an user by a username.
     *
     * @param username Unique username.
     * @return User requested.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
        User user = usersService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Delete a user.
     *
     * @param username User's username that will be deleted.
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUsername(@PathVariable("username") String username) {
        usersService.deleteByUsername(username);
    }

    /**
     * Save a new user.
     *
     * @param user User will be saved.
     * @return The user after save it.
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> save(@RequestBody @Validated User user) {
        User userCreated = usersService.save(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    /**
     * Handler {@link UserNotFoundException}.
     *
     * @param e Exception will be handled.
     * @return Api error response.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> userNotFound(UserNotFoundException e) {
        ApiError error = new ApiError("user_not_found", "User not exist.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

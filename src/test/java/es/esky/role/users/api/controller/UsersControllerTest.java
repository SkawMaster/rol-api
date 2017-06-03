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

import es.esky.role.users.domain.User;
import es.esky.role.users.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class UsersControllerTest {

    private static final User USER_1 = user().withUsername("user1").withPassword("1234").build();
    private static final User USER_2 = user().withUsername("user2").withPassword("1234").build();
    private final static PageRequest REQUEST_PAGE = new PageRequest(0, 10);

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    @Test
    public void findByCriteria_callsServiceWithCorrectPageRequest() throws Exception {
        final Page<User> expectedPage = new PageImpl<>(asList(USER_1, USER_2));
        when(usersService.findByCriteria(REQUEST_PAGE)).thenReturn(expectedPage);

        final Page<User> page = usersController.findByCriteria(REQUEST_PAGE);

        assertThat(page, equalTo(expectedPage));
    }

}
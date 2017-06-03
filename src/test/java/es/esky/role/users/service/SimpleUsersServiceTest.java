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

import es.esky.role.users.api.exception.UserNotFoundException;
import es.esky.role.users.domain.User;
import es.esky.role.users.repository.UsersRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleUsersServiceTest {

    private static final User USER_1 = user().withUsername("user1").withPassword("1234").build();
    private static final User USER_2 = user().withUsername("user2").withPassword("1234").build();

    @Mock
    private UsersRepository usersRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SimpleUsersService usersService;

    @Test
    public void findByCriteria_validPage_returnUsersPage() {
        final Pageable page = new PageRequest(0, 10);
        final Page<User> expectedUsers = new PageImpl<>(Arrays.asList(USER_1, USER_2));

        when(usersRepositoryMock.findAll(page)).thenReturn(expectedUsers);

        Page<User> users = usersService.findByCriteria(page);

        assertThat(users, equalTo(expectedUsers));
    }

    @Test
    public void findByUsername_validUsername_returnUser() {
        final String correctUsername = "user1";

        when(usersRepositoryMock.findOne(correctUsername)).thenReturn(USER_1);

        User user = usersService.findByUsername(correctUsername);

        assertThat(user, equalTo(USER_1));
    }

    @Test(expected = UserNotFoundException.class)
    public void findByUsername_InvalidUsername_throwException() {
        final String incorrectUsername = "user4";

        when(usersRepositoryMock.findOne(incorrectUsername)).thenReturn(null);

        usersService.findByUsername(incorrectUsername);

        fail("Exception should be throw");
    }

    @Test
    public void save_ValidUser_ReturnHashedUser() {
        final String encodedPassword = "4321";

        when(usersRepositoryMock.save(USER_1)).thenReturn(USER_1);
        when(passwordEncoder.encode(USER_1.getPassword())).thenReturn(encodedPassword);

        User user = usersService.save(USER_1);

        assertThat(user.getUsername(), equalTo(USER_1.getUsername()));
        assertThat(user.getPassword(), equalTo(encodedPassword));
    }
}
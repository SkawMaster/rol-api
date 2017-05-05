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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.esky.role.users.api.exception.UserNotFoundException;
import es.esky.role.users.domain.User;
import es.esky.role.users.service.UsersService;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class CurrentUserDetailsServiceTest {

	@InjectMocks
	private CurrentUserDetailsService userDetailsService;

	@Mock
	private UsersService usersService;

	@Test
	public void loadUserByUsername_WhenUserExist_ReturnUserDetailsWithUser() {
		final String username = "dummy";
		final User expectedUser = user().withUsername("dummy").withPassword("1234").build();

		when(usersService.findByUsername(username)).thenReturn(expectedUser);

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		assertThat(userDetails.getUsername(), equalTo(expectedUser.getUsername()));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsername_WhenUserNotExist_ThrowUsernameNotFoundException() {
		final String username = "dummy";
		final UserNotFoundException exception = new UserNotFoundException();

		when(usersService.findByUsername(username)).thenThrow(exception);

		userDetailsService.loadUserByUsername(username);

		fail();
	}
}
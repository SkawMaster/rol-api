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

import java.util.List;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import es.esky.role.users.domain.User;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class CurrentUserDetailsTest {

	private static final User user = user().withUsername("dummy").withPassword("1234").build();

	@Test
	public void getAuthorities_returnAlwaysRoleUserAndRoleAdmin() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		List<GrantedAuthority> expected = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

		assertThat(userDetails.getAuthorities(), hasSize(2));
		assertThat(userDetails.getAuthorities(), equalTo(expected));
	}

	@Test
	public void getPassword_returnUserPassword() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		assertThat(userDetails.getPassword(), equalTo(user.getPassword()));
	}

	@Test
	public void getUsername_returnUserUsername() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		assertThat(userDetails.getUsername(), equalTo(user.getUsername()));
	}

	@Test
	public void isAccountNonExpired_returnAlwaysTrue() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		assertThat(userDetails.isAccountNonExpired(), equalTo(Boolean.TRUE));
	}

	@Test
	public void isAccountNonLocked_returnAlwaysTrue() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		assertThat(userDetails.isAccountNonLocked(), equalTo(Boolean.TRUE));
	}

	@Test
	public void isCredentialsNonExpired_returnAlwaysTrue() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		assertThat(userDetails.isCredentialsNonExpired(), equalTo(Boolean.TRUE));
	}

	@Test
	public void isEnabled_returnAlwaysTrue() {
		CurrentUserDetails userDetails = new CurrentUserDetails(user);

		assertThat(userDetails.isEnabled(), equalTo(Boolean.TRUE));
	}

}
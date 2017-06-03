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
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import es.esky.role.users.domain.User;

/**
 * Stores information about the current user logged.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class CurrentUserDetails implements UserDetails {
	private final User userInfo;

	/**
	 * Construct a new instance with an {@code User}.
	 *
	 * @param userInfo User logged information.
	 */
	public CurrentUserDetails(@NotNull User userInfo) {
		Assert.notNull(userInfo, "User must not be null");
		this.userInfo = userInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPassword() {
		return this.userInfo.getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsername() {
		return this.userInfo.getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonExpired() {
		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAccountNonLocked() {
		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {
		return Boolean.TRUE;
	}
}

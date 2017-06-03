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

package es.esky.role.integration.authentication;

import org.springframework.stereotype.Component;

import es.esky.role.integration.WorldLifecycle;

/**
 * Share authentication data between steps.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class AuthenticationWorld implements WorldLifecycle {

	private String username;
	private String password;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void before() {
		username = null;
		password = null;
	}

	/**
	 * Save user credentials.
	 *
	 * @param username User username.
	 * @param password User password.
	 */
	public void saveCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Check if some credential is store.
	 *
	 * @return True if exist credentials, otherwise false.
	 */
	public boolean haveCredentials() {
		return this.username != null && this.password != null;
	}

	/**
	 * Get username credentials if exist.
	 *
	 * @return Username credentials if exist, otherwise null.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Get password credentials if exist.
	 *
	 * @return Password credentials if exist, otherwise null.
	 */
	public String getPassword() {
		return this.password;
	}
}

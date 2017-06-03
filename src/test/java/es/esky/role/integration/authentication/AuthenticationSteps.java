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

import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class AuthenticationSteps {

	@Autowired
	private AuthenticationWorld authenticationWorld;

	/**
	 * Login a user with user and password credentials.
	 */
	@Given("^I am authenticated as (\\w+) user with password (\\w+)$")
	public void i_am_authenticate_as(String username, String password) {
		authenticationWorld.saveCredentials(username, password);
	}
}

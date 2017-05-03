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

package es.esky.role.users.domain.builder;

import es.esky.role.users.domain.User;
import org.junit.Test;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class UserBuilderTest {

    @Test
    public void build_MakeACorrectUser() throws Exception {
        final String expectedUsername = "username1";
        final String expectedPassword = "password1";

        User user = user().withUsername(expectedUsername).withPassword(expectedPassword).build();

        assertThat(user.getUsername(), equalTo(expectedUsername));
        assertThat(user.getPassword(), equalTo(expectedPassword));
    }
}
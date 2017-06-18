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

package es.esky.role.users.domain;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static es.esky.role.users.domain.builder.UserBuilder.user;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class UserTest {

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void equals_SameUser_ReturnTrue() {
        User user1 = user().withUsername("user1").withPassword("1234").build();

        boolean isEquals = user1.equals(user1);

        assertThat(isEquals, is(Boolean.TRUE));
    }

    @Test
    public void equals_EqualUser_ReturnTrue() {
        User user1 = user().withUsername("user1").withPassword("1234").build();
        User user2 = user().withUsername("user1").withPassword("1234").build();

        boolean isEquals = user1.equals(user2);

        assertThat(isEquals, is(Boolean.TRUE));
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void equals_Null_ReturnFalse() {
        User user1 = user().withUsername("user1").withPassword("1234").build();

        boolean isEquals = user1.equals(null);

        assertThat(isEquals, is(Boolean.FALSE));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void equals_AnotherType_ReturnFalse() {
        User user1 = user().withUsername("user1").withPassword("1234").build();
        String user2 = "user1";

        boolean isEquals = user1.equals(user2);

        assertThat(isEquals, is(Boolean.FALSE));
    }

    @Test
    public void equals_NonEqualUser_ReturnTrue() {
        User user1 = user().withUsername("user1").withPassword("1234").build();
        User user2 = user().withUsername("user2").withPassword("1234").build();

        boolean isEquals = user1.equals(user2);

        assertThat(isEquals, is(Boolean.FALSE));
    }

    @Test
    public void hashCode_usersEqualsHaveSameHash() {
        User user1 = user().withUsername("user1").withPassword("1234").build();
        User user2 = user().withUsername("user1").withPassword("1234").build();

        int hash1 = user1.hashCode();
        int hash2 = user2.hashCode();

        assertThat(user1, equalTo(user2));
        assertThat(hash1, equalTo(hash2));
    }

	@Test
	public void toString_returnUserStringRepresentation() {
    	final String expectedRepresentation = "[username=user]";
    	User user = user().withUsername("user").withPassword("aaa").build();
    	String value = user.toString();

    	assertThat(value, containsString(expectedRepresentation));
	}
}
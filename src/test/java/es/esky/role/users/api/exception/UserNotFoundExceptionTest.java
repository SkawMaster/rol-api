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

package es.esky.role.users.api.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class UserNotFoundExceptionTest {

    @Test(expected = UserNotFoundException.class)
    public void UserNotFoundException_EmptyConstructor_ConstructOk() {
        throw new UserNotFoundException();
    }

    @Test(expected = UserNotFoundException.class)
    public void UserNotFoundException_OnlyCauseConstructor_ConstructOk() {
        final RuntimeException exception = new RuntimeException();
        final UserNotFoundException userNotFoundException = new UserNotFoundException(exception);

        assertThat(userNotFoundException.getCause(), equalTo(exception));

        throw  userNotFoundException;
    }

    @Test(expected = UserNotFoundException.class)
    public void UserNotFoundException_OnlyMessageConstructor_ConstructOk() {
        final String message = "dummy message";
        final UserNotFoundException userNotFoundException = new UserNotFoundException(message);

        assertThat(userNotFoundException.getMessage(), equalTo(message));

        throw  userNotFoundException;
    }

    @Test(expected = UserNotFoundException.class)
    public void UserNotFoundException_MessageAndCauseConstructor_ConstructOk() {
        final RuntimeException exception = new RuntimeException();
        final String message = "dummy message";
        final UserNotFoundException userNotFoundException = new UserNotFoundException(message, exception);

        assertThat(userNotFoundException.getCause(), equalTo(exception));
        assertThat(userNotFoundException.getMessage(), equalTo(message));

        throw  userNotFoundException;
    }
}
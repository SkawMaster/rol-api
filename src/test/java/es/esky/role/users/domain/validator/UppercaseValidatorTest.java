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

package es.esky.role.users.domain.validator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class UppercaseValidatorTest {

    private static final UppercaseValidator validator = new UppercaseValidator();

    @Test
    public void initialize_DoNothing() {
        Uppercase uppercase = mock(Uppercase.class);

        validator.initialize(uppercase);

        verifyZeroInteractions(uppercase);
    }

    @Test
    public void isValid_StringWithUpperCharacters_ReturnTrue() {
        final String validString = "Aaa";

        boolean isValid = validator.isValid(validString, null);

        assertThat(isValid, is(Boolean.TRUE));
    }

    @Test
    public void isValid_StringWithoutUpperCharacters_ReturnTrue() {
        final String invalidString = "aaa";

        boolean isValid = validator.isValid(invalidString, null);

        assertThat(isValid, is(Boolean.FALSE));
    }
}
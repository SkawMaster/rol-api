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
public class LowercaseValidatorTest {

    private static final LowercaseValidator validator = new LowercaseValidator();

    @Test
    public void initialize_DoNothing() {
        Lowercase lowercase = mock(Lowercase.class);

        validator.initialize(lowercase);

        verifyZeroInteractions(lowercase);
    }

    @Test
    public void isValid_StringWithLowerCharacters_ReturnTrue() throws Exception {
        final String validString = "aAA";

        boolean isValid = validator.isValid(validString, null);

        assertThat(isValid, is(Boolean.TRUE));
    }

    @Test
    public void isValid_StringWithoutLowerCharacters_ReturnTrue() throws Exception {
        final String invalidString = "AAA";

        boolean isValid = validator.isValid(invalidString, null);

        assertThat(isValid, is(Boolean.FALSE));
    }
}
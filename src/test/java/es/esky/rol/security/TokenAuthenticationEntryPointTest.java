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

package es.esky.rol.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenAuthenticationEntryPointTest {

    @Captor
    private ArgumentCaptor<Integer> httpStatusCaptor;

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @Test
    public void commence_Send401ResponseError() throws Exception {
        final String expectedMessage = "dummy message";

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final AuthenticationException exception = mock(AuthenticationException.class);

        TokenAuthenticationEntryPoint entryPoint = new TokenAuthenticationEntryPoint();

        when(exception.getMessage()).thenReturn(expectedMessage);

        entryPoint.commence(request, response, exception);

        verify(response).sendError(httpStatusCaptor.capture(), messageCaptor.capture());

        assertThat(httpStatusCaptor.getValue(), equalTo(HttpServletResponse.SC_UNAUTHORIZED));
        assertThat(messageCaptor.getValue(), equalTo(expectedMessage));
    }

}
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

package es.esky.role.http.logging;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.spy;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class BaseLoggingFilterTest {
	private static final String RESPONSE_BODY_TEST = "dummy";

	private static final BaseLoggingFilter FILTER = new BaseLoggingFilter();

	@Test
	public void doFilterInternal_CacheRequestAndResponse() throws Exception {
		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = spy(new MockFilterChain());

		FILTER.doFilterInternal(request, response, filterChain);

		Mockito.verify(filterChain).doFilter(isA(ContentCachingRequestWrapper.class), isA
				(ContentCachingResponseWrapper.class));
	}

	@Test
	public void doFilterInternal_UpdateCorrectlyResponseBody() throws Exception {
		HttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain filterChain = new TestFilterChain();

		FILTER.doFilterInternal(request, response, filterChain);

		assertThat(response.getContentAsString(), is(RESPONSE_BODY_TEST));
	}

	@Test
	public void buildRequestLog_ReturnStringRequestInfo() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod(HttpMethod.GET.name());
		request.setRequestURI("/dummy");

		String log = FILTER.buildRequestLog(new ContentCachingRequestWrapper(request));

		assertThat(log, is("Server has received a request\n > GET http://localhost/dummy\n[empty]"));
	}

	@Test
	public void buildResponseLog_ReturnStringResponseInfo() {
		MockHttpServletResponse response = new MockHttpServletResponse();

		String log = FILTER.buildResponseLog(new ContentCachingResponseWrapper(response));

		assertThat(log, is("Server responded with a response\n > 200\n[empty]"));
	}

	private class TestFilterChain implements FilterChain {
		@Override
		public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
			servletResponse.getWriter().print(RESPONSE_BODY_TEST);
		}
	}
}
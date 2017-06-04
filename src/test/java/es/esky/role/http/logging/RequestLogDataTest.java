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

import java.net.URI;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class RequestLogDataTest {
	private final static URI URL = URI.create("test");
	private final static HttpMethod METHOD = HttpMethod.GET;
	private final static HttpHeaders HEADERS = new HttpHeaders();
	private final static String BODY = "body";
	private final static RequestLogData DATA = new RequestLogData(URL, METHOD, HEADERS, BODY);

	@Test
	public void getUri_returnUri() {
		assertThat(DATA.getUri(), is(URL));
	}

	@Test
	public void getMethod_returnMethod() {
		assertThat(DATA.getMethod(), is(METHOD));
	}

	@Test
	public void getHeaders_returnHeaders() {
		assertThat(DATA.getHeaders(), is(HEADERS));
	}

	@Test
	public void getBody_returnBody() {
		assertThat(DATA.getBody(), is(BODY));
	}

	@Test
	public void toString_returnRequestStringRepresentation() {
		final String expectedRepresentation = "[uri=test,method=GET,headers={},body=body]";

		assertThat(DATA.toString(), containsString(expectedRepresentation));
	}
}
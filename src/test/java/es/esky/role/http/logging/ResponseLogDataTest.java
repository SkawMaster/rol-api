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

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class ResponseLogDataTest {
	private static final HttpStatus STATUS = HttpStatus.OK;
	private static final String BODY = "body";
	private static final ResponseLogData DATA = new ResponseLogData(STATUS, BODY);

	@Test
	public void getStatus_returnStatus() {
		assertThat(DATA.getStatus(), is(STATUS));
	}

	@Test
	public void getBody_returnBody() {
		assertThat(DATA.getBody(), is(BODY));
	}

	@Test
	public void toString_returnRequestStringRepresentation() {
		final String expectedRepresentation = "[status=200,body=body]";

		assertThat(DATA.toString(), containsString(expectedRepresentation));
	}
}
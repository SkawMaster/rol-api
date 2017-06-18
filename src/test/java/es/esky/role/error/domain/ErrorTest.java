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

package es.esky.role.error.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ErrorTest {
	private static final String ERROR_CODE = "code dummy";
	private static final String ERROR_MESSAGE = "message dummy";
	private static final String ERROR_DESCRIPTION = "description dummy";
	private static final Error ERROR = new Error(ERROR_CODE, ERROR_MESSAGE, ERROR_DESCRIPTION);

	@Test
	public void getCode_ReturnCurrentErrorCode() throws Exception {
		assertThat(ERROR.getCode(), is(ERROR_CODE));
	}

	@Test
	public void getMessage_ReturnCurrentErrorMessage() throws Exception {
		assertThat(ERROR.getMessage(), is(ERROR_MESSAGE));
	}

	@Test
	public void getDescription_ReturnCurrentErrorDescription() throws Exception {
		assertThat(ERROR.getDescription(), is(ERROR_DESCRIPTION));
	}

	@Test
	public void toString_ReturnStringWithCodeMessageAndDescription() throws Exception {
		assertThat(ERROR.toString(), containsString("[code=" + ERROR_CODE + ",message=" + ERROR_MESSAGE + "," +
				"description=" + ERROR_DESCRIPTION + "]"));
	}

}
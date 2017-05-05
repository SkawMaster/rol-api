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

package es.esky.role.http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class ApiHttpHeadersTest {

	@Test
	public void constructor_constructCorrectly() {
		ApiHttpHeaders httpHeaders = new ApiHttpHeaders();

		assertThat(httpHeaders.size(), equalTo(0));
	}

	@Test
	public void totalCountHeader_valueIsCorrect() {
		assertThat(ApiHttpHeaders.TOTAL_COUNT, equalTo("X-Total-Count"));
	}
}
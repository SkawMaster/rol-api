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

package es.esky.role.http.header;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HateoasLinkHeaderBuilderTest {

	private static HateoasLinkHeaderBuilder linkHeaderBuilder;
	private static Page<?> page;

	@BeforeClass
	public static void setup() {
		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromUriString("dummy");
		linkHeaderBuilder = new HateoasLinkHeaderBuilder(builder);

		Pageable nextPageable = mock(Pageable.class);
		when(nextPageable.getPageNumber()).thenReturn(4);

		Pageable prevPageable = mock(Pageable.class);
		when(prevPageable.getPageNumber()).thenReturn(2);

		page = mock(Page.class);
		when(page.nextPageable()).thenReturn(nextPageable);
		when(page.previousPageable()).thenReturn(prevPageable);
		when(page.getTotalPages()).thenReturn(6);
	}

	@Test
	public void buildFirst_ReturnFirstPageLink() {
		final String expectedLink = "<dummy?page=0>; rel=\"first\"";
		String link = linkHeaderBuilder.buildFirst();
		assertThat(link, equalTo(expectedLink));
	}

	@Test
	public void buildLast_ReturnLastPageLink() {
		final String expectedLink = "<dummy?page=5>; rel=\"last\"";
		String link = linkHeaderBuilder.buildLast(page);
		assertThat(link, equalTo(expectedLink));
	}

	@Test
	public void buildNext_ReturnNextPageLink() {
		final String expectedLink = "<dummy?page=4>; rel=\"next\"";
		String link = linkHeaderBuilder.buildNext(page);
		assertThat(link, equalTo(expectedLink));
	}

	@Test
	public void buildPrev_ReturnPreviousPageLink() {
		final String expectedLink = "<dummy?page=2>; rel=\"prev\"";
		String link = linkHeaderBuilder.buildPrev(page);
		assertThat(link, equalTo(expectedLink));
	}
}

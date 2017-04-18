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

package es.esky.rol.http;

import es.esky.rol.http.LinkHeaderBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public class LinkHeaderBuilderTest {

    private final static LinkHeaderBuilder builder = new LinkHeaderBuilder();

    @Test
    public void first_ReturnCorrectURI() {
        UriComponentsBuilder uriComponentsBuilder = Mockito.mock(UriComponentsBuilder.class);
        UriComponents uriComponents = Mockito.mock(UriComponents.class);

        Mockito.when(uriComponentsBuilder.replaceQueryParam("page", 0)).thenReturn(uriComponentsBuilder);
        Mockito.when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        Mockito.when(uriComponents.toString()).thenReturn("dummy");

        String uri = builder.first(uriComponentsBuilder);

        assertThat(uri, equalTo("<dummy>; rel=\"first\""));
    }

    @Test
    public void prev_ReturnCorrectURI() {
        UriComponentsBuilder uriComponentsBuilder = Mockito.mock(UriComponentsBuilder.class);
        UriComponents uriComponents = Mockito.mock(UriComponents.class);
        Page<?> page = Mockito.mock(Page.class);
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(page.previousPageable()).thenReturn(pageable);
        Mockito.when(pageable.getPageNumber()).thenReturn(4);
        Mockito.when(uriComponentsBuilder.replaceQueryParam("page", 4)).thenReturn(uriComponentsBuilder);
        Mockito.when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        Mockito.when(uriComponents.toString()).thenReturn("dummy");

        String uri = builder.prev(uriComponentsBuilder, page);

        assertThat(uri, equalTo("<dummy>; rel=\"prev\""));
    }

    @Test
    public void next_ReturnCorrectURI() {
        UriComponentsBuilder uriComponentsBuilder = Mockito.mock(UriComponentsBuilder.class);
        UriComponents uriComponents = Mockito.mock(UriComponents.class);
        Page<?> page = Mockito.mock(Page.class);
        Pageable pageable = Mockito.mock(Pageable.class);

        Mockito.when(page.nextPageable()).thenReturn(pageable);
        Mockito.when(pageable.getPageNumber()).thenReturn(4);
        Mockito.when(uriComponentsBuilder.replaceQueryParam("page", 4)).thenReturn(uriComponentsBuilder);
        Mockito.when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        Mockito.when(uriComponents.toString()).thenReturn("dummy");

        String uri = builder.next(uriComponentsBuilder, page);

        assertThat(uri, equalTo("<dummy>; rel=\"next\""));
    }

    @Test
    public void last_ReturnCorrectURI() {
        UriComponentsBuilder uriComponentsBuilder = Mockito.mock(UriComponentsBuilder.class);
        UriComponents uriComponents = Mockito.mock(UriComponents.class);
        Page<?> page = Mockito.mock(Page.class);

        Mockito.when(page.getTotalPages()).thenReturn(3);
        Mockito.when(uriComponentsBuilder.replaceQueryParam("page", 2)).thenReturn(uriComponentsBuilder);
        Mockito.when(uriComponentsBuilder.build()).thenReturn(uriComponents);
        Mockito.when(uriComponents.toString()).thenReturn("dummy");

        String uri = builder.last(uriComponentsBuilder, page);

        assertThat(uri, equalTo("<dummy>; rel=\"last\""));
    }
}
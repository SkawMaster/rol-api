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

package es.esky.rol.pagination.api.controller;

import es.esky.rol.http.ApiHttpHeaders;
import es.esky.rol.http.TotalCountHeaderBuilder;
import es.esky.rol.pagination.service.PaginationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.net.URI;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PaginationControllerTest {

    private Method pageableMethod;
    private Method nonPageableMethod;

    @InjectMocks
    private PaginationController paginationController;

    @Mock
    private PaginationService paginationService;

    @Mock
    private TotalCountHeaderBuilder totalCountHeaderBuilder;

    @Before
    public void setup() throws NoSuchMethodException {
        pageableMethod = SampleController.class.getMethod("pageableMethod");
        nonPageableMethod = SampleController.class.getMethod("nonPageableMethod");
    }

    @Test
    public void beforeBodyWriteInternal_WriteHeaderPaginationData() {
        final String expectedTotalCount = "11";
        final String expectedLink = "dummy";

        Page<?> samplePage = mock(Page.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        MappingJacksonValue value = new MappingJacksonValue(samplePage);
        HttpHeaders headers = new HttpHeaders();

        when(paginationService.buildHttpHeaderLinks(any(UriComponentsBuilder.class), eq(samplePage))).thenReturn(expectedLink);
        when(response.getHeaders()).thenReturn(headers);
        when(request.getURI()).thenReturn(URI.create("http://www.dummy.dum/"));
        when(request.getHeaders()).thenReturn(headers);
        when(totalCountHeaderBuilder.buildFromPage(samplePage)).thenReturn(expectedTotalCount);

        paginationController.beforeBodyWriteInternal(value, null, null, request, response);

        assertThat(headers, hasKey(ApiHttpHeaders.LINK));
        assertThat(headers, hasKey(ApiHttpHeaders.TOTAL_COUNT));
        assertThat(headers.get(ApiHttpHeaders.LINK), equalTo(singletonList(expectedLink)));
        assertThat(headers.get(ApiHttpHeaders.TOTAL_COUNT), equalTo(singletonList(expectedTotalCount)));
    }

    @Test
    public void supports_PageReturnTypeAndJacksonConverter_ReturnTrue() {
        MethodParameter returnType = new MethodParameter(pageableMethod, -1, 0);
        boolean support = paginationController.supports(returnType, AbstractJackson2HttpMessageConverter.class);

        assertThat(support, equalTo(Boolean.TRUE));
    }

    @Test
    public void supports_NonPageReturnTypeAndJacksonConverter_ReturnFalse() {
        MethodParameter returnType = new MethodParameter(nonPageableMethod, -1, 0);
        boolean support = paginationController.supports(returnType, AbstractJackson2HttpMessageConverter.class);

        assertThat(support, equalTo(Boolean.FALSE));
    }

    @Test
    public void supports_PageReturnTypeAndNonJacksonConverter_ReturnFalse() {
        MethodParameter returnType = new MethodParameter(pageableMethod, -1, 0);
        boolean support = paginationController.supports(returnType, NonJacksonConverter.class);

        assertThat(support, equalTo(Boolean.FALSE));
    }

    @SuppressWarnings("unused")
    interface SampleController {
        Page<Object> pageableMethod();

        Object nonPageableMethod();
    }

    interface NonJacksonConverter extends HttpMessageConverter<Object> {
    }
}

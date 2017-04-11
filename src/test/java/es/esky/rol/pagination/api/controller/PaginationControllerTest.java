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

import java.lang.reflect.Method;

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
import org.springframework.http.server.ServerHttpResponse;

import es.esky.rol.http.ApiHttpHeaders;
import es.esky.rol.pagination.service.PaginationService;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasEntry;

@RunWith(MockitoJUnitRunner.class)
public class PaginationControllerTest {
    
    private Method pageableMethod;
    private Method nonPageableMethod;
    
    @InjectMocks
    private PaginationController paginationController;
    
    @Mock
    private PaginationService paginationService;
    
    @Before
    public void setup() throws NoSuchMethodException {
        pageableMethod = SampleController.class.getMethod("pageableMethod");
        nonPageableMethod = SampleController.class.getMethod("nonPageableMethod");
    }
    
    @Test
    public void beforeBodyWriteInternal_WriteHeaderPaginationData() {
        Page<?> samplePage = mock(Page.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        
        MappingJacksonValue value = new MappingJacksonValue(samplePage);
        HttpHeaders headers = new HttpHeaders();
        
        when(samplePage.getTotalElements()).thenReturn(11L);
        when(response.getHeaders()).thenReturn(headers);
        
        paginationController.beforeBodyWriteInternal(value, null, null, null, response);
        
        assertThat(headers, hasKey(ApiHttpHeaders.LINK));
        assertThat(headers, hasEntry(ApiHttpHeaders.TOTAL_COUNT, "11"));
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
    
    static interface SampleController {
        Page<Object> pageableMethod();
        Object nonPageableMethod();
    }
    
    static interface NonJacksonConverter extends HttpMessageConverter<Object> {}
}

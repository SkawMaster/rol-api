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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import org.springframework.web.util.UriComponentsBuilder;

@ControllerAdvice
public class PaginationController extends AbstractMappingJacksonResponseBodyAdvice {

    private PaginationService paginationService;
    private TotalCountHeaderBuilder totalCountHeaderBuilder;

    @Autowired
    public PaginationController(PaginationService paginationService, TotalCountHeaderBuilder totalCountHeaderBuilder) {
        this.paginationService = paginationService;
        this.totalCountHeaderBuilder = totalCountHeaderBuilder;
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue value,
                                           MediaType mediaType,
                                           MethodParameter methodParameter,
                                           ServerHttpRequest request,
                                           ServerHttpResponse response) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpRequest(request);

        Page<?> page = (Page<?>) value.getValue();

        response.getHeaders().add(ApiHttpHeaders.TOTAL_COUNT, totalCountHeaderBuilder.buildFromPage(page));
        response.getHeaders().add(ApiHttpHeaders.LINK, paginationService.buildHttpHeaderLinks(uriComponentsBuilder, page));

        value.setValue(page.getContent());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) &&
                Page.class.isAssignableFrom(returnType.getParameterType());
    }
}

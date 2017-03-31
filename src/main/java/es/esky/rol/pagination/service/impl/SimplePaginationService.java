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

package es.esky.rol.pagination.service.impl;

import es.esky.rol.pagination.service.PaginationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Service
public class SimplePaginationService implements PaginationService {

    public static final String LINK_STANDARD_FMT = "<%s>; rel=\"%s\"";
    public static final String QUERY_PARAM_PAGE = "page";
    public static final String LINK_HEADER_FIRST = "first";
    public static final String LINK_HEADER_PREVIOUS = "prev";
    public static final String LINK_HEADER_NEXT = "next";
    public static final String LINK_HEADER_LAST = "last";

    @Override
    public String buildHttpHeaderLinks(ServerHttpRequest request, Page<?> page) {
        List<String> headerLinks = new ArrayList<>();

        if (!page.isFirst()) {
            headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
                    .replaceQueryParam(QUERY_PARAM_PAGE, 0)
                    .build(), LINK_HEADER_FIRST));
        }

        if (page.hasPrevious()) {
            headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
                    .replaceQueryParam(QUERY_PARAM_PAGE, page.previousPageable().getPageNumber())
                    .build(), LINK_HEADER_PREVIOUS));
        }

        if (page.hasNext()) {
            headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
                    .replaceQueryParam(QUERY_PARAM_PAGE, page.nextPageable().getPageNumber())
                    .build(), LINK_HEADER_NEXT));
        }

        if (!page.isLast()) {
            headerLinks.add(String.format(LINK_STANDARD_FMT, UriComponentsBuilder.fromHttpRequest(request)
                    .replaceQueryParam(QUERY_PARAM_PAGE, page.getTotalPages() - 1)
                    .build(), LINK_HEADER_LAST));
        }

        return StringUtils.join(headerLinks, ", ");
    }
}

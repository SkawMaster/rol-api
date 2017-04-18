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

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class LinkHeaderBuilder {
    private static final String LINK_STANDARD_FMT = "<%s>; rel=\"%s\"";
    private static final String QUERY_PARAM_PAGE = "page";
    private static final String LINK_HEADER_FIRST = "first";
    private static final String LINK_HEADER_PREVIOUS = "prev";
    private static final String LINK_HEADER_NEXT = "next";
    private static final String LINK_HEADER_LAST = "last";

    public String first(UriComponentsBuilder builder) {
        UriComponents components = builder.replaceQueryParam(QUERY_PARAM_PAGE, 0).build();
        return buildLinkHeader(components, LINK_HEADER_FIRST);
    }

    public String prev(UriComponentsBuilder builder, Page<?> page) {
        UriComponents components = builder.replaceQueryParam(QUERY_PARAM_PAGE, page.previousPageable().getPageNumber()).build();
        return buildLinkHeader(components, LINK_HEADER_PREVIOUS);
    }

    public String next(UriComponentsBuilder builder, Page<?> page) {
        UriComponents components = builder.replaceQueryParam(QUERY_PARAM_PAGE, page.nextPageable().getPageNumber()).build();
        return buildLinkHeader(components, LINK_HEADER_NEXT);
    }

    public String last(UriComponentsBuilder builder, Page<?> page) {
        UriComponents components = builder.replaceQueryParam(QUERY_PARAM_PAGE, page.getTotalPages() - 1).build();
        return buildLinkHeader(components, LINK_HEADER_LAST);
    }

    private String buildLinkHeader(UriComponents components, String header) {
        return String.format(LINK_STANDARD_FMT, components, header);
    }
}

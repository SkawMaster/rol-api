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

package es.esky.role.pagination;

import es.esky.role.http.ApiHttpHeaders;
import es.esky.role.http.header.LinkHeaderBuilder;
import es.esky.role.http.header.TotalCountHeaderBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class HateoasPaginationHeadersBuilder implements PaginationHeadersBuilder {

    private final LinkHeaderBuilder linkHeaderBuilder;
    private final TotalCountHeaderBuilder totalCountHeaderBuilder;

    @Autowired
    public HateoasPaginationHeadersBuilder(LinkHeaderBuilder linkHeaderBuilder, TotalCountHeaderBuilder totalCountHeaderBuilder) {
        this.linkHeaderBuilder = linkHeaderBuilder;
        this.totalCountHeaderBuilder = totalCountHeaderBuilder;
    }
    
    @Override
    public HttpHeaders addPaginationData(HttpHeaders headers, Page<?> page) {
        headers.add(ApiHttpHeaders.TOTAL_COUNT, totalCountHeaderBuilder.buildTotal(page));

        if (!page.isFirst()) {
            headers.add(ApiHttpHeaders.LINK, linkHeaderBuilder.buildFirst());
        }

        if (!page.isLast()) {
            headers.add(ApiHttpHeaders.LINK, linkHeaderBuilder.buildLast(page));
        }

        if (page.hasPrevious()) {
            headers.add(ApiHttpHeaders.LINK, linkHeaderBuilder.buildPrev(page));
        }

        if (page.hasNext()) {
            headers.add(ApiHttpHeaders.LINK, linkHeaderBuilder.buildNext(page));
        }
        
        return headers;
    }
}

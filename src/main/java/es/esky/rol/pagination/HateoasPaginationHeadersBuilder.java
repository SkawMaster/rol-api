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

package es.esky.rol.pagination;

import es.esky.rol.http.header.LinkHeaderBuilder;
import es.esky.rol.http.header.TotalCountHeaderBuilder;
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

    private LinkHeaderBuilder linkHeaderBuilder;
    private TotalCountHeaderBuilder totalCountHeaderBuilder;

    @Autowired
    public HateoasPaginationHeadersBuilder(LinkHeaderBuilder linkHeaderBuilder, TotalCountHeaderBuilder totalCountHeaderBuilder) {
        this.linkHeaderBuilder = linkHeaderBuilder;
        this.totalCountHeaderBuilder = totalCountHeaderBuilder;
    }

    @Override
    public HttpHeaders buildFrom(Page<?> page) {
        HttpHeaders headers = new HttpHeaders();

        headers.putAll(linkHeaderBuilder.buildFrom(page));
        headers.putAll(totalCountHeaderBuilder.buildFrom(page));

        return headers;
    }
}

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

import es.esky.rol.http.LinkHeaderBuilder;
import es.esky.rol.pagination.service.PaginationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    private LinkHeaderBuilder linkHeaderBuilder;

    @Autowired
    public SimplePaginationService(LinkHeaderBuilder linkHeaderBuilder) {
        this.linkHeaderBuilder = linkHeaderBuilder;
    }

    @Override
    public String buildHttpHeaderLinks(UriComponentsBuilder builder, Page<?> page) {
        List<String> headerLinks = new ArrayList<>();

        if (!page.isFirst()) {
            String link = linkHeaderBuilder.first(builder);
            headerLinks.add(link);
        }

        if (page.hasPrevious()) {
            String link = linkHeaderBuilder.prev(builder, page);
            headerLinks.add(link);
        }

        if (page.hasNext()) {
            String link = linkHeaderBuilder.next(builder, page);
            headerLinks.add(link);
        }

        if (!page.isLast()) {
            String link = linkHeaderBuilder.last(builder, page);
            headerLinks.add(link);
        }

        return StringUtils.join(headerLinks, ", ");
    }
}

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

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import es.esky.role.http.ApiHttpHeaders;
import es.esky.role.http.header.HateoasLinkHeaderBuilder;
import es.esky.role.http.header.LinkHeaderBuilder;
import es.esky.role.http.header.TotalCountHeaderBuilder;

/**
 * Implement {@link PaginationHeadersBuilder} adding pagination data in {@code X-Total-Count} and {@code Link} headers.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class HateoasPaginationHeadersBuilder implements PaginationHeadersBuilder {
	private static final Logger logger = LoggerFactory.getLogger(HateoasLinkHeaderBuilder.class);

	private final LinkHeaderBuilder linkHeaderBuilder;
	private final TotalCountHeaderBuilder totalCountHeaderBuilder;

	/**
	 * Construct a new instance.
	 *
	 * @param linkHeaderBuilder       Builder of {@code Link} header.
	 * @param totalCountHeaderBuilder Builder of {@code X-Total-Count} header.
	 * @since 1.0.0
	 */
	@Autowired
	public HateoasPaginationHeadersBuilder(@NotNull LinkHeaderBuilder linkHeaderBuilder, @NotNull TotalCountHeaderBuilder totalCountHeaderBuilder) {
		Assert.notNull(linkHeaderBuilder, "LinkHeaderBuild must not be null");
		Assert.notNull(totalCountHeaderBuilder, "TotalCountHeaderBuilder must not be null");
		this.linkHeaderBuilder = linkHeaderBuilder;
		this.totalCountHeaderBuilder = totalCountHeaderBuilder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpHeaders addPaginationData(HttpHeaders headers, Page<?> page) {
		addHeaderAndLog(headers, ApiHttpHeaders.TOTAL_COUNT, this.totalCountHeaderBuilder.buildTotal(page));

		if (!page.isFirst()) {
			addHeaderAndLog(headers, ApiHttpHeaders.LINK, this.linkHeaderBuilder.buildFirst());
		}

		if (!page.isLast()) {
			addHeaderAndLog(headers, ApiHttpHeaders.LINK, this.linkHeaderBuilder.buildLast(page));
		}

		if (page.hasPrevious()) {
			addHeaderAndLog(headers, ApiHttpHeaders.LINK, this.linkHeaderBuilder.buildPrev(page));
		}

		if (page.hasNext()) {
			addHeaderAndLog(headers, ApiHttpHeaders.LINK, this.linkHeaderBuilder.buildNext(page));
		}

		return headers;
	}

	private void addHeaderAndLog(HttpHeaders headers, String header, String value) {
		logger.debug("Adding [{}: {}] header", header, value);
		headers.add(header, value);
	}
}

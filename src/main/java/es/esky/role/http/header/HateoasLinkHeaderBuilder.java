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

package es.esky.role.http.header;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Implement {@link LinkHeaderBuilder} using RFC 5988.
 *
 * @author Cristian Mateos López
 * @see <a href="https://tools.ietf.org/html/rfc5988#section-5">Section 5 of RFC 5988</a>
 * @since 1.0.0
 */
@Component
public class HateoasLinkHeaderBuilder implements LinkHeaderBuilder {
	private static final String LINK_STANDARD_FMT = "<%s>; rel=\"%s\"";
	private static final String QUERY_PARAM_PAGE = "page";
	private static final String LINK_REL_FIRST = "first";
	private static final String LINK_REL_PREVIOUS = "prev";
	private static final String LINK_REL_NEXT = "next";
	private static final String LINK_REL_LAST = "last";

	private final UriComponentsBuilder builder;

	/**
	 * Construct a new instance.
	 *
	 * @param builder Builder for the URIs.
	 */
	@Autowired
	public HateoasLinkHeaderBuilder(@NotNull UriComponentsBuilder builder) {
		Assert.notNull(builder, "UriComponentsBuilder must not be null");
		this.builder = builder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildFirst() {
		final UriComponents components = this.builder.replaceQueryParam(QUERY_PARAM_PAGE, 0).build();
		return buildLink(components, LINK_REL_FIRST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildLast(final Page<?> page) {
		final UriComponents components = this.builder.replaceQueryParam(QUERY_PARAM_PAGE, page.getTotalPages() - 1).build();
		return buildLink(components, LINK_REL_LAST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildNext(final Page<?> page) {
		final UriComponents components = this.builder.replaceQueryParam(QUERY_PARAM_PAGE, page.nextPageable().getPageNumber()).build();
		return buildLink(components, LINK_REL_NEXT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildPrev(final Page<?> page) {
		final UriComponents components = this.builder.replaceQueryParam(QUERY_PARAM_PAGE, page.previousPageable().getPageNumber()).build();
		return buildLink(components, LINK_REL_PREVIOUS);
	}

	/**
	 * Build link and log the value before return.
	 *
	 * @param components Uri to convert to {@code String}.
	 * @param rel        Rel value to mark in the link.
	 * @return Formed link representation.
	 */
	private String buildLink(final UriComponents components, final String rel) {
		return String.format(LINK_STANDARD_FMT, components, rel);
	}
}

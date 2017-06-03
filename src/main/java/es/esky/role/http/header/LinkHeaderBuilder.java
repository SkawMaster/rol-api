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

import org.springframework.data.domain.Page;

/**
 * Build the links for the api on the header {@code Link}
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public interface LinkHeaderBuilder {
	/**
	 * Build pagination link {@code first}.
	 *
	 * @return Url string representation of link {@code first}.
	 * @see es.esky.role.pagination.PaginationHeadersBuilder
	 * @since 1.0.0
	 */
	String buildFirst();

	/**
	 * Build pagination link {@code next}.
	 *
	 * @param page Pagination data.
	 * @return Url string representation of link {@code next}.
	 * @see es.esky.role.pagination.PaginationHeadersBuilder
	 * @since 1.0.0
	 */
	String buildNext(Page<?> page);

	/**
	 * Build pagination link {@code last}.
	 *
	 * @param page Pagination data.
	 * @return Url string representation of link {@code last}.
	 * @see es.esky.role.pagination.PaginationHeadersBuilder
	 * @since 1.0.0
	 */
	String buildLast(Page<?> page);

	/**
	 * Build pagination link {@code prev}.
	 *
	 * @param page Pagination data.
	 * @return Url string representation of link {@code prev}.
	 * @see es.esky.role.pagination.PaginationHeadersBuilder
	 * @since 1.0.0
	 */
	String buildPrev(Page<?> page);
}

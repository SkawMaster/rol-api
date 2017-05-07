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

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

/**
 * Build the pagination headers.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
public interface PaginationHeadersBuilder {
	/**
	 * Add pagination data to the headers.
	 *
	 * @param headers Headers that will store pagination data.
	 * @param page    Pagination data.
	 * @return Headers with the pagination data.
	 * @since 1.0.0
	 */
	HttpHeaders addPaginationData(HttpHeaders headers, Page<?> page);
}

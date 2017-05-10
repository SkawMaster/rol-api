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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Implement {@link TotalCountHeaderBuilder} using a simple conversion to {@code String}.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class SimpleTotalCountHeaderBuilder implements TotalCountHeaderBuilder {
	private static final Logger logger = LoggerFactory.getLogger(SimpleTotalCountHeaderBuilder.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String buildTotal(Page<?> page) {
		String value = String.valueOf(page.getTotalElements());
		logger.debug("Value of header <X-Total-Count> formed with value: {}", value);
		return value;
	}
}

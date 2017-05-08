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

package es.esky.role.users.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * Validate if a {@code String} is lowercase. Use for {@link Lowercase} interface.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class LowercaseValidator implements ConstraintValidator<Lowercase, String> {
	private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(Lowercase constraintAnnotation) {
		// Not initialization needed.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return HAS_LOWERCASE.matcher(value).find();
	}
}

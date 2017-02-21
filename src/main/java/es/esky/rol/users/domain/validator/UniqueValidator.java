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

package es.esky.rol.users.domain.validator;

import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate a {@link Unique} constraint for type {@link User}.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Component
public class UniqueValidator implements ConstraintValidator<Unique, User> {

    private final UsersService usersService;

    /**
     * Constructor of UniqueValidator.
     *
     * @param usersService UsersService dependency.
     */
    @Autowired
    public UniqueValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Unique constraint) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        return !usersService.exists(user.getUsername());
    }
}

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

package es.esky.role.users.service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import es.esky.role.users.api.exception.UserNotFoundException;
import es.esky.role.users.domain.User;
import es.esky.role.users.repository.UsersRepository;

/**
 * Implement {@link UsersService}.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Service
public class SimpleUsersService implements UsersService {
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * Constructor a new instance.
	 *
	 * @param usersRepository Users repository.
	 * @param passwordEncoder Password encoder.
	 * @since 1.0.0
	 */
	@Autowired
	public SimpleUsersService(@NotNull UsersRepository usersRepository, @NotNull PasswordEncoder passwordEncoder) {
		Assert.notNull(usersRepository, "UsersRepository must not be null");
		Assert.notNull(passwordEncoder, "PasswordEncoder must not be null");
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<User> findByCriteria(Pageable page) {
		return this.usersRepository.findAll(page);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return Optional.ofNullable(this.usersRepository.findOne(username)).orElseThrow(UserNotFoundException::new);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public User save(User user) {
		String unHashedPassword = user.getPassword();
		user.setPassword(this.passwordEncoder.encode(unHashedPassword));
		return this.usersRepository.save(user);
	}
}

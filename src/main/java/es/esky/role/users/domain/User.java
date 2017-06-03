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

package es.esky.role.users.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import es.esky.role.users.domain.validator.Lowercase;
import es.esky.role.users.domain.validator.Uppercase;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * User domain model.
 *
 * @author Cristian Mateos López
 * @since 1.0.0
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	@JsonProperty(access = WRITE_ONLY)
	@Lowercase
	@Uppercase
	@Size(min = 8)
	private String password;

	/**
	 * Get username.
	 *
	 * @return username.
	 * @since 1.0.0
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username.
	 *
	 * @param username username.
	 * @since 1.0.0
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get password.
	 *
	 * @return password.
	 * @since 1.0.0
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password.
	 *
	 * @param password password.
	 * @since 1.0.0
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		return new EqualsBuilder()
				.append(username, user.username)
				.append(password, user.password)
				.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(username)
				.append(password)
				.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("username", username)
				.toString();
	}
}

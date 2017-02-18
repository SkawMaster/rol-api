package es.esky.rol.users.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.esky.rol.users.domain.validator.Lowercase;
import es.esky.rol.users.domain.validator.Unique;
import es.esky.rol.users.domain.validator.Uppercase;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @author Cristian Mateos López
 */
@Entity
@Table(name = "USERS")
@Unique
public class User {

    @Id
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    @Lowercase
    @Uppercase
    @Size(min = 8, max = 30)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(username, user.username)
                .append(password, user.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(username)
                .append(password)
                .toHashCode();
    }
}

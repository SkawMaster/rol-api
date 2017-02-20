package es.esky.rol.security;

import es.esky.rol.users.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Stores information about the current user logged.
 */
class CurrentUserDetails implements UserDetails {

    private User userInfo;

    /**
     * Construct a new CurrentUserDetails.
     * @param userInfo User logged information.
     */
    public CurrentUserDetails(User userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}

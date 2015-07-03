package com.quantium.web.bean.security;

import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.dao.annotation.Index;
import com.quantium.web.dao.annotation.Row;
import com.quantium.web.dao.annotation.Table;
import com.quantium.web.util.Primitives;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Михаил on 30.08.14.
 */
@Table("security_users")
public class UserSecurity implements UserDetails {

    @Index
    @Row(name = "userId")
    private int userId;

    @Row(name = "userName")
    private String userName;

    @Row(name = "password")
    private String password;

    @Row(name = "accountNonExpired")
    private boolean accountNonExpired;

    @Row(name = "accountNonLocked")
    private boolean accountNonLocked;

    @Row(name = "credentialsNonExpired")
    private boolean credentialsNonExpired;

    @Row(name = "enabled")
    private boolean enabled;

    @Row(name = "email")
    private String email;

    @Row(name = "phone")
    private String phone;

    @Row(name = "languageCode")
    private String languageCode;

    private Collection<GrantedAuthority> authorities;

    @Row(name = "roleId")
    private int roleId;
    private AccessManager accessManager;

    private UserProfile profile;

    public int getUserId() {
        return userId;
    }
    public UserSecurity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    public String getUserName() {
        return userName;
    }
    public UserSecurity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }
    public UserSecurity setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    public UserSecurity setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
        return this;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    public UserSecurity setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    public UserSecurity setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public UserSecurity setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getEmail() {
        return email;
    }
    public UserSecurity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }
    public UserSecurity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getLanguageCode() {
        return languageCode;
    }
    public UserSecurity setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    public String getAuthoritiesAsString() {
        if (authorities != null)
            return Primitives.slice(",", authorities);
        else
            return "ROLE_USER";
    }
    public UserSecurity setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }
    public UserSecurity setAuthorities(String authorities) {
        this.authorities = new HashSet<GrantedAuthority>();

        for (final String role : authorities.split(","))
            if (role != null && !"".equals(role.trim())) {
                GrantedAuthority grandAuthority = new GrantedAuthority() {
                    private static final long serialVersionUID = 3958183417696804555L;

                    public String getAuthority() {
                        return role.trim();
                    }
                };

                this.authorities.add(grandAuthority);
            }

        return this;
    }

    public int getRoleId() {
        return roleId;
    }
    public UserSecurity setRoleId(int roleId) {
        this.roleId = roleId;
        return this;
    }
    public AccessManager getAccessManager() {
        return accessManager;
    }
    public UserSecurity setAccessManager(AccessManager accessManager) {
        this.accessManager = accessManager;
        return this;
    }

    public UserProfile getProfile() {
        return profile;
    }
    public UserSecurity setProfile(UserProfile profile) {
        this.profile = profile;
        return this;
    }
}

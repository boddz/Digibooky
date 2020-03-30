package com.teamawsome.infrastructure.authentication.external;

import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExternalAuthentication {
    private String username;
    private String password;
    private List<BookstoreRole> roles;

    public static ExternalAuthentication externalAuthentication() {
        return new ExternalAuthentication();
    }

    public ExternalAuthentication withUsername(String username) {
        this.username = username;
        return this;
    }

    public ExternalAuthentication withPassword(String password) {
        this.password = password;
        return this;
    }

    public ExternalAuthentication withRoles(List<BookstoreRole> roles) {
        this.roles = roles;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<BookstoreRole> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalAuthentication that = (ExternalAuthentication) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, roles);
    }

    @Override
    public String toString() {
        return "ExternalAuthentication{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}

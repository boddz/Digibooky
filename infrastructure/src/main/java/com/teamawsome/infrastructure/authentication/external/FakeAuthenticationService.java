package com.teamawsome.infrastructure.authentication.external;

import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeAuthenticationService {

    private final List<ExternalAuthentication> externalAuthentications;

    @Autowired
    public FakeAuthenticationService(List<ExternalAuthentication> externalAuthentications) {
        this.externalAuthentications = externalAuthentications;
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("dries").withPassword("admin").withRoles(List.of(BookstoreRole.ADMIN)));
    }

    public ExternalAuthentication getUser(String username, String password) {
        return externalAuthentications.stream()
                .filter(externalAuthentication -> externalAuthentication.getUsername().equals(username))
                .filter(externalAuthentication -> externalAuthentication.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void addMember(String name, String password, List<BookstoreRole> roles){
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername(name).withPassword(password).withRoles(roles));
    }
}

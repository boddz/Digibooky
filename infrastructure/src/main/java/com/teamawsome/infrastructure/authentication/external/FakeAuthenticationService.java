package com.teamawsome.infrastructure.authentication.external;

import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class FakeAuthenticationService {

    private final List<ExternalAuthentication> externalAuthentications;

    @Autowired
    public FakeAuthenticationService(List<ExternalAuthentication> externalAuthenticationss) {
        this.externalAuthentications = externalAuthenticationss;
        externalAuthentications.remove(0);
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("admin").withPassword("admin").withRoles(List.of(BookstoreRole.ADMIN)));
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("librarian").withPassword("librarian").withRoles(List.of(BookstoreRole.LIBRARIAN)));
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("member").withPassword("member").withRoles(List.of(BookstoreRole.MEMBER)));
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

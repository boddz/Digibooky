package com.teamawsome.infrastructure.authentication.external;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeAuthenticationService {

    private final List<ExternalAuthentication> externalAuthentications;

    public FakeAuthenticationService(List<ExternalAuthentication> externalAuthentications) {
        this.externalAuthentications = externalAuthentications;
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("user").withPassword("password").withRoles(List.of("fakeRole")));
    }

    public ExternalAuthentication getUser(String username, String password) {
        return externalAuthentications.stream()
                .filter(externalAuthentication -> externalAuthentication.getUsername().equals(username))
                .filter(externalAuthentication -> externalAuthentication.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void addMember(String name, String password, List<String> roles){
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername(name).withPassword(password).withRoles(roles));
    }
}

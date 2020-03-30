package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.member.NotUniqueException;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    MemberRepository memberRepository;
    MemberMapper memberMapper;
    FakeAuthenticationService fakeAuthenticationService;

    @Autowired
    public MemberController(MemberRepository memberRepository, MemberMapper memberMapper, FakeAuthenticationService fakeAuthenticationService) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.fakeAuthenticationService = fakeAuthenticationService;
    }

    @PostMapping(path = "{user}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewUser(@RequestBody MemberRegistryDTO memberRegistryDTO, @PathVariable String user) {
        checkIfValidInput(memberRegistryDTO);
        Member newMember = new Member(memberRegistryDTO.getInss(), memberRegistryDTO.geteMail(), memberRegistryDTO.getFirstName(), memberRegistryDTO.getLastName(), memberRegistryDTO.getStreetName(), memberRegistryDTO.getHouseNumber(), memberRegistryDTO.getPostalCode(), memberRegistryDTO.getCity());
        memberRepository.addMember(newMember);
        switch (user) {
            case "member":
                addUserToAuthenticationService(memberRegistryDTO, "member", BookstoreRole.MEMBER);
                break;
            case "admin":
                addAdminToAuthenticationService(memberRegistryDTO);
                break;
            case "librarian":
                addLibrarianToAuthenticationService(memberRegistryDTO);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "use: admin or librarian or member as pathvariable");
        }
        return new MemberDto(newMember);
    }

    private void addUserToAuthenticationService(MemberRegistryDTO memberRegistryDTO, String pasword, BookstoreRole bookstoreRoles) {
        fakeAuthenticationService.addMember(memberRegistryDTO.getFirstName(), pasword, List.of(bookstoreRoles));
    }

    @PreAuthorize("hasAuthority('MAKE_ADMIN')")
    private void addAdminToAuthenticationService(MemberRegistryDTO memberRegistryDTO) {
        addUserToAuthenticationService(memberRegistryDTO, "admin", BookstoreRole.ADMIN);
    }

    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    private void addLibrarianToAuthenticationService(MemberRegistryDTO memberRegistryDTO) {
        addUserToAuthenticationService(memberRegistryDTO, "librarian", BookstoreRole.LIBRARIAN);
    }


    private void checkIfValidInput(MemberRegistryDTO memberRegistryDTO) {
        memberRepository.isUniqueEmail(memberRegistryDTO.geteMail());
        memberRepository.isUniqueInss(memberRegistryDTO.getInss());
        if (memberRegistryDTO.getLastName().isEmpty() || memberRegistryDTO.getCity().isEmpty()) {
            throw new IllegalArgumentException("city and lastname must be inserted");
        }
    }

    @ExceptionHandler(NotUniqueException.class)
    protected void notUniqueException(NotUniqueException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}

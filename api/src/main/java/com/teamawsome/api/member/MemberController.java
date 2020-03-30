package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.member.NotUniqueException;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.teamawsome.domain.member.Member.MemberBuilder.*;

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

    @PostMapping(path = "member", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewMember(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        checkIfValidInput(memberRegistryDTO);
        Member newMember = buildMember()
                .withInss(memberRegistryDTO.getInss())
                .withEmail(memberRegistryDTO.geteMail())
                .withFirstName(memberRegistryDTO.getFirstName())
                .withLastName(memberRegistryDTO.getLastName())
                .withStreetName(memberRegistryDTO.getStreetName())
                .withHouseNumber(memberRegistryDTO.getHouseNumber())
                .withPostalCode(memberRegistryDTO.getPostalCode())
                .withCity(memberRegistryDTO.getCity())
                .build();
        memberRepository.addMember(newMember);
        addUserToAuthenticationService(memberRegistryDTO, "member", BookstoreRole.MEMBER);
        return new MemberDto(newMember);

    }

    @PreAuthorize("hasAuthority('MAKE_ADMIN')")
    @PostMapping(path = "admin", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewAdmin(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        checkIfValidInput(memberRegistryDTO);
        Member newMember = buildMember()
                .withInss(memberRegistryDTO.getInss())
                .withEmail(memberRegistryDTO.geteMail())
                .withFirstName(memberRegistryDTO.getFirstName())
                .withLastName(memberRegistryDTO.getLastName())
                .withStreetName(memberRegistryDTO.getStreetName())
                .withHouseNumber(memberRegistryDTO.getHouseNumber())
                .withPostalCode(memberRegistryDTO.getPostalCode())
                .withCity(memberRegistryDTO.getCity())
                .build();
        memberRepository.addMember(newMember);
        addUserToAuthenticationService(memberRegistryDTO, "admin", BookstoreRole.ADMIN);
        return new MemberDto(newMember);
    }

    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @PostMapping(path = "librarian", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewLibrarian(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        checkIfValidInput(memberRegistryDTO);
        Member newMember = buildMember()
                .withInss(memberRegistryDTO.getInss())
                .withEmail(memberRegistryDTO.geteMail())
                .withFirstName(memberRegistryDTO.getFirstName())
                .withLastName(memberRegistryDTO.getLastName())
                .withStreetName(memberRegistryDTO.getStreetName())
                .withHouseNumber(memberRegistryDTO.getHouseNumber())
                .withPostalCode(memberRegistryDTO.getPostalCode())
                .withCity(memberRegistryDTO.getCity())
                .build();
        memberRepository.addMember(newMember);
        addUserToAuthenticationService(memberRegistryDTO, "librarian", BookstoreRole.LIBRARIAN);
        return new MemberDto(newMember);
    }

    private void addUserToAuthenticationService(MemberRegistryDTO memberRegistryDTO, String pasword, BookstoreRole bookstoreRoles) {
        fakeAuthenticationService.addMember(memberRegistryDTO.getFirstName(), pasword, List.of(bookstoreRoles));
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

    @ExceptionHandler(AccessDeniedException.class)
    protected void notException(AccessDeniedException e, HttpServletResponse response) throws IOException {
        response.sendError(403, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}

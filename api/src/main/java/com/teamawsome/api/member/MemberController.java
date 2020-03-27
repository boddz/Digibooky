package com.teamawsome.api.member;

import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;
import com.teamawsome.service.MemberMapper;
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

    @PostMapping (path = "{user}",  consumes = "application/json", produces = "application/json")
//    @PreAuthorize("hasAuthority('MAKE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewUser(@RequestBody MemberRegistryDTO memberRegistryDTO, @PathVariable String user){
        checkIfValidInput(memberRegistryDTO);
        Member newMember = new Member(memberRegistryDTO.getInss(), memberRegistryDTO.geteMail(),memberRegistryDTO.getFirstName(),memberRegistryDTO.getLastName(),memberRegistryDTO.getStreetName(),memberRegistryDTO.getHouseNumber(),memberRegistryDTO.getPostalCode(),memberRegistryDTO.getCity());
        memberRepository.addMember(newMember);
        if  (user.equals("member")) {
            fakeAuthenticationService.addMember(memberRegistryDTO.getFirstName(), "member", List.of(BookstoreRole.MEMBER));
        }
        else if (user.equals("admin")) {
            fakeAuthenticationService.addMember(memberRegistryDTO.getFirstName(), "admin", List.of(BookstoreRole.ADMIN));
        }
        else if (user.equals("librarian")) {
            fakeAuthenticationService.addMember(memberRegistryDTO.getFirstName(), "librarian", List.of(BookstoreRole.LIBRARIAN));
        } else { throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"use: admin or librarian or member as pathvariable");}
        return new MemberDto(newMember);
    }



    private void checkIfValidInput(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        if (!memberRepository.isUniqueEmail(memberRegistryDTO.geteMail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"not unique Email");
        }
        if (!memberRepository.isUniqueInss(memberRegistryDTO.getInss())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"not unique inss");
        }
        if(memberRegistryDTO.getLastName().isEmpty() || memberRegistryDTO.getCity().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"city and lastname must be inserted");
        }
    }



}

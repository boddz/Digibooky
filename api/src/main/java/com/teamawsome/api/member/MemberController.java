package com.teamawsome.api.member;

import com.teamawsome.domain.dto.MemberDto;
import com.teamawsome.domain.service.MemberManagement;
import com.teamawsome.domain.dto.MemberRegistryDTO;
import com.teamawsome.domain.member.NotUniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    MemberManagement memberManagement;

    @Autowired
    public MemberController(MemberManagement memberManagement) {
        this.memberManagement = memberManagement;
    }

    @PostMapping(path = "member", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewMember(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        return memberManagement.registerMember(memberRegistryDTO);
    }

    @PreAuthorize("hasAuthority('MAKE_ADMIN')")
    @PostMapping(path = "admin", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewAdmin(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        return memberManagement.registerAdministrator(memberRegistryDTO);
    }

    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @PostMapping(path = "librarian", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewLibrarian(@RequestBody MemberRegistryDTO memberRegistryDTO) {
        return memberManagement.registerLibrarian(memberRegistryDTO);
    }

    @PreAuthorize("hasAuthority('GET_ALL_BOOKS')")
    @GetMapping(produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDto> getListOfMembers() {
        return memberManagement.getListOfMembers();
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

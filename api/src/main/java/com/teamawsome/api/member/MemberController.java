package com.teamawsome.api.member;

import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.service.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    MemberRepository memberRepository;
    MemberMapper memberMapper;

    @Autowired
    public MemberController(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @PostMapping (consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerNewMember(@RequestBody MemberRegistryDTO memberRegistryDTO){
        Member newMember = new Member(memberRegistryDTO.inss, memberRegistryDTO.eMail,memberRegistryDTO.firstName,memberRegistryDTO.lastName,memberRegistryDTO.streetName,memberRegistryDTO.houseNumber,memberRegistryDTO.postalCode,memberRegistryDTO.city);
        memberRepository.addMember(newMember);
        return new MemberDto(newMember);
    }
}

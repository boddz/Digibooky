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
        if (!memberRepository.isUniqueEmail(memberRegistryDTO.geteMail())) {
            throw new IllegalArgumentException("not uniqueEmail");
        }
        Member newMember = new Member(memberRegistryDTO.getInss(), memberRegistryDTO.geteMail(),memberRegistryDTO.getFirstName(),memberRegistryDTO.getLastName(),memberRegistryDTO.getStreetName(),memberRegistryDTO.getHouseNumber(),memberRegistryDTO.getPostalCode(),memberRegistryDTO.getCity());
        memberRepository.addMember(newMember);
        return new MemberDto(newMember);
    }

 /*   public boolean checkBodyRequierements(MemberRegistryDTO memberRegistryDTO){
        checkIfMailIsUnique(memberRegistryDTO);
    }

    private boolean checkIfMailIsUnique(MemberRegistryDTO memberRegistryDTO) {
        if (!memberRepository.isUniqueEmail(memberRegistryDTO.geteMail())) {
            throw new IllegalArgumentException("not uniqueEmail");
        }return true;
    }*/
}

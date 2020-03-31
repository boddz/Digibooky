package com.teamawsome.domain.service;

import com.teamawsome.domain.dto.MemberDto;
import com.teamawsome.domain.dto.MemberRegistryDTO;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.teamawsome.infrastructure.authentication.external.FakeAuthenticationService;
import com.teamawsome.infrastructure.authentication.feature.BookstoreRole;

import java.util.List;
import java.util.stream.Collectors;

import static com.teamawsome.domain.member.Member.MemberBuilder.buildMember;

@Service
public class MemberManagement {
    private final FakeAuthenticationService fakeAuthenticationService;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;


    public MemberManagement(FakeAuthenticationService fakeAuthenticationService, MemberRepository memberRepository, MemberMapper memberMapper){
        this.fakeAuthenticationService = fakeAuthenticationService;
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public MemberDto registerMember(MemberRegistryDTO memberRegistryDTO){
        Member newMember = verifyAndAddNewMember(memberRegistryDTO);
        addUserToAuthenticationService(memberRegistryDTO, BookstoreRole.MEMBER);
        return memberMapper.toMemberDto(newMember);
    }

    public MemberDto registerLibrarian(MemberRegistryDTO memberRegistryDTO){
        Member newMember = verifyAndAddNewMember(memberRegistryDTO);
        addUserToAuthenticationService(memberRegistryDTO, BookstoreRole.LIBRARIAN);
        return memberMapper.toMemberDto(newMember);
    }

    public MemberDto registerAdministrator(MemberRegistryDTO memberRegistryDTO){
        Member newMember = verifyAndAddNewMember(memberRegistryDTO);
        addUserToAuthenticationService(memberRegistryDTO,BookstoreRole.ADMIN);
        return memberMapper.toMemberDto(newMember);
    }


    private Member verifyAndAddNewMember(@RequestBody MemberRegistryDTO memberRegistryDTO) {
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
        return newMember;
    }

    void checkIfValidInput(MemberRegistryDTO memberRegistryDTO) {
        memberRepository.isUniqueEmail(memberRegistryDTO.geteMail());
        memberRepository.isUniqueInss(memberRegistryDTO.getInss());
        if (memberRegistryDTO.getLastName().isEmpty() || memberRegistryDTO.getCity().isEmpty()) {
            throw new IllegalArgumentException("city and lastname must be inserted");
        }
    }

    private void addUserToAuthenticationService(MemberRegistryDTO memberRegistryDTO, BookstoreRole bookstoreRoles) {
        fakeAuthenticationService.addMember(memberRegistryDTO.getFirstName(), memberRegistryDTO.getPassword(), List.of(bookstoreRoles));
    }

    public List<MemberDto> getListOfMembers(){
        return memberRepository.getAllMembers().stream()
                .map(memberMapper::toMemberDto)
                .map(MemberDto::setInssToEmpty)
                .collect(Collectors.toList());
    }
}

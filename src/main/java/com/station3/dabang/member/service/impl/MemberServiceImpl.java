package com.station3.dabang.member.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.member.service.MemberService;
import com.station3.dabang.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

	@Override
	@Transactional
	public MemberCreateResponse create(MemberCreateRequest memberCreateRequest) {
		if(memberRepository.existsByEmail(new Email(memberCreateRequest.getEmail()))) {
			throw new IllegalArgumentException("이메일이 중복되었습니다.");
		}
		
		Member saveMember = memberRepository.save(memberCreateRequest.toMemeber());
		return MemberCreateResponse.from(saveMember, jwtTokenProvider.createToken(saveMember.getEmail().getValue()));
	}
	
}

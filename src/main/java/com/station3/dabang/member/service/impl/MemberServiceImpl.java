package com.station3.dabang.member.service.impl;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.station3.dabang.common.exception.CustomException;
import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.request.MemberLoginRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.controller.dto.response.MemberLoginResponse;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public MemberCreateResponse create(MemberCreateRequest memberCreateRequest) {
		if(memberRepository.existsByEmail(new Email(memberCreateRequest.getEmail()))) {
			throw new IllegalArgumentException("이메일이 중복되었습니다.");
		}
		
		memberCreateRequest.passwordEncryption(passwordEncoder.encode(memberCreateRequest.getPassword()));
		Member saveMember = memberRepository.save(memberCreateRequest.toMemeber());
		return MemberCreateResponse.from(saveMember, jwtTokenProvider.createToken(saveMember.getEmail().getValue()));
	}

	@Override
	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
	    try {
	    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword()));
	    	return MemberLoginResponse.from(memberRepository.findByEmail(new Email(memberLoginRequest.getEmail())), jwtTokenProvider.createToken(memberLoginRequest.getEmail()));
	      } catch (AuthenticationException e) {
    	  	e.printStackTrace();
    	  	throw new CustomException("아이디와 암호를 확인해주세요.", HttpStatus.UNPROCESSABLE_ENTITY);
	      }
	}
	
}

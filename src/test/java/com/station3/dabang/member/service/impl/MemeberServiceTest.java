package com.station3.dabang.member.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.member.domain.Password;
import com.station3.dabang.member.service.MemberService;
import com.station3.dabang.security.JwtTokenProvider;

public class MemeberServiceTest {

	private final Email email = new Email("admin@station3.co.kr");
	private final Password password = new Password("Station3#");
	private final String jwtToken = "jwtToken";
	
	private final MemberRepository memberRepository = mock(MemberRepository.class);
	private final JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
    private final MemberService memberService = new MemberServiceImpl(memberRepository, jwtTokenProvider);

	@Test
	@DisplayName("회원을 등록한다")
	public void createMemeber() {
		//given
		MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email.getValue(), password.getValue());
		Member member = new Member(1L, email.getValue(), password.getValue());
		doReturn(member).when(memberRepository).save(any());
		doReturn(jwtToken).when(jwtTokenProvider).createToken(any());
		
		//when
		MemberCreateResponse memberCreateResponse = memberService.create(memberCreateRequest);
		
		//then
		assertAll(
			()->assertEquals(memberCreateResponse.getEmail(), member.getEmail()),
			()->assertEquals(memberCreateResponse.getId(), member.getId()),
			()->assertEquals(memberCreateResponse.getJwtToken(), jwtToken)
		);
	}
	
	@Test
	@DisplayName("이미 등록된 이메일로는 등록 할 수 없다.")
	public void emailduplicateCheck() {
		MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email.getValue(), password.getValue());
		doReturn(true).when(memberRepository).existsByEmail(any());
			
		ThrowingCallable callable = () -> memberService.create(memberCreateRequest);
			
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(callable)
			.withMessageMatching("이메일이 중복되었습니다.");
	}

}

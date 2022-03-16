package com.station3.dabang.member.controller.dto.response;

import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberCreateResponse {
	private final Long id;
	private final Email email;
	private final String jwtToken;

	public static MemberCreateResponse from(Member member, String jwtToken) {
        return new MemberCreateResponse(member.getId(), member.getEmail(), jwtToken);
	}
}

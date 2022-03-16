package com.station3.dabang.member.controller.dto.response;

import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@ApiModel
public class MemberLoginResponse {
	@ApiModelProperty(value = "id")
	private final Long id;

	@ApiModelProperty(value = "email")
	private final Email email;
	
	@ApiModelProperty(value = "token")
	private final String jwtToken;

	public static MemberLoginResponse from(Member member, String jwtToken) {
        return new MemberLoginResponse(member.getId(), member.getEmail(), jwtToken);
	}
}

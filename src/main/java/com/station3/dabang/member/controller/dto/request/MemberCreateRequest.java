package com.station3.dabang.member.controller.dto.request;

import com.station3.dabang.member.domain.Member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ApiModel
public class MemberCreateRequest {
	
    public MemberCreateRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	@ApiModelProperty(value = "email", example = "admin@station3.co.kr")
	private final String email;	

    @ApiModelProperty(value = "password", example = "test")
	private final String password;
    
    public Member toMemeber() {
    	return new Member(email, password);
    }
}

package com.station3.dabang.member.controller.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.station3.dabang.member.domain.Member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@ApiModel
@AllArgsConstructor
public class MemberLoginRequest {
	@ApiModelProperty(value = "email", example = "admin@station3.co.kr")
	@Email(message="이메일 형태를 확인해주세요")
	private final String email;	

    @ApiModelProperty(value = "password", example = "Station3#")
    @NotEmpty(message="암호는 필수 값 입니다.")
	private final String password;
    
    public Member toMemeber() {
    	return new Member(email, password);
    }
}

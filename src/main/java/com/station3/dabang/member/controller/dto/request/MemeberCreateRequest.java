package com.station3.dabang.member.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel
public class MemeberCreateRequest {
    @ApiModelProperty(value = "email", example = "admin@station3.co.kr")
	private String email;	

    @ApiModelProperty(value = "password", example = "test")
	private String password;
}

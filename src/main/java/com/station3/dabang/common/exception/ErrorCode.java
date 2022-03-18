package com.station3.dabang.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode  {

	INTERNAL_SERVER("E001", "내부 서비스 문제가 발생했습니다. 관리자에게 문의해주세요."),
	AUTH_INVALID("E002", "로그인정보가 만료되었거나, 아이디암호가 일치하지 않습니다."),
	DUPLICATE_EMAIL("E003", "이미 등록되어있는 이메일입니다."),
	NOT_READABLE("E003", "데이터 형식을 확인해주세요.");

	private String code;
	private String message;

	ErrorCode(String code, String message) {
		this.message = message;
		this.code = code;
	}
}
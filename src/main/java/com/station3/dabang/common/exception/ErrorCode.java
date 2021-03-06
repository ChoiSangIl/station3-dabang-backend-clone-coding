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
	NOT_READABLE("E004", "데이터 형식을 확인해주세요."),
	DEAL_NOT_VALID_01("E005", "방 유형이 전세이면 월세가 들어갈 수 없습니다."),
	DEAL_NOT_VALID_02("E006", "방 유형이 월세이면 월세금액이 0이상이여야 합니다."),
	DEAL_NOT_VALID_03("E007", "보증/전세, 월세금액이 0입니다."),
	DEAL_NOT_VALID_04("E008", "전세는 1개만 들어갈 수 있습니다."),
	DEAL_NOT_VALID_05("E009", "월세/보증금 금액이 중복되었습니다."),
	LOGIN_INFO_INVALID("E010", "잘못된 요청입니다(자신의 정보만 가져올 수 있습니다)"),
	ROOM_NOT_FOUND("E011", "방 상세정보가 존재하지 않습니다."),
	NOT_AUTH_DELETE_ROOM("E012", "방이 존재하지 않거나, 방을 삭제할 권한이 없습니다."),
	PASSWORD_INVALID("E013", "비밀번호 형식을 확인해주세요(최소 숫자 1자리 이상, 영어 소문자 1자리 이상, 영어 대문자 1자리 이상, 특수문자 1자리 이상, 공백문자 포함하지 않고, 8자리 이상)");

	private String code;
	private String message;

	ErrorCode(String code, String message) {
		this.message = message;
		this.code = code;
	}
}

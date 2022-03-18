package com.station3.dabang.common.exception;

public class BusinessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;
	private ErrorCode errorCode;

	public BusinessRuntimeException(ErrorCode errorCode) {
		this.message = errorCode.getMessage();
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

}

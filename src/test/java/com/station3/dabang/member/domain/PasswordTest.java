package com.station3.dabang.member.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordTest {

	private Password password;
	private String passwordString = "Station3#";

	@Test
	@DisplayName("비밀번호를 생성할 수 있다.")
	public void create() {
		password = new Password(passwordString);
		assertEquals(password.getValue(), passwordString);
	}

}

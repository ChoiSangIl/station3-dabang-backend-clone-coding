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
	
	@DisplayName("값이 없으면 IllegalArgumentException이 발생한다")
	@ParameterizedTest
	@NullAndEmptySource
	public void nullAndEmptyCheck(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			password = new Password(input);
		});
	}
	
	@DisplayName("비밀번호 형식이 다르면 IllegalArgumentException이 발생한다")
	@ParameterizedTest
	@ValueSource(strings= {"s$#@$%222", "1234", "test", "T@asdfesdf"})
	public void verifyPassword(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			password = new Password(input);
		});
	}

}

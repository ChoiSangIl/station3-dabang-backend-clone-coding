package com.station3.dabang.member.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class EmailTest {

	private Email email;
	private String emailAddress = "admin@station3.co.kr";
	
	@Test
	@DisplayName("이메일을 생성할 수 있다.")
	public void create() {
		email = new Email(emailAddress);
		assertEquals(email.getValue(), emailAddress);
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("값이 없으면 IllegalArgumentException이 발생한다")
	public void nullAndEmptyCheck(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			email = new Email(input);
		});
	}
	
	@DisplayName("메일 형식이 다르면 IllegalArgumentException이 발생한다")
	@ParameterizedTest
	@ValueSource(strings= {"abcdef", "bbb@", "12456"})
	public void verifyPassword(String input) {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			email = new Email(input);
		});
	}

}

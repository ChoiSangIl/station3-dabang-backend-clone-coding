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
	private String emailAddress = "dabang@station3.co.kr";
	
	@Test
	@DisplayName("이메일을 생성할 수 있다.")
	public void create() {
		email = new Email(emailAddress);
		assertEquals(email.getValue(), emailAddress);
	}

}

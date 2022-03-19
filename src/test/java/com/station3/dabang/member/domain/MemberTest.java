package com.station3.dabang.member.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {
	
	private Member member;
	private static final String email = "dabang@station3.co.kr";
	private static final String password = "Station3#";
	
	@Test
	@DisplayName("회원을 생성할 수 있어야한다.")
	public void create() {
		//when
		member = new Member(email, password);
		
		//then
		assertEquals(member.getEmail().getValue(), email);
		assertEquals(member.getPassword().getValue(), password);
	}
}

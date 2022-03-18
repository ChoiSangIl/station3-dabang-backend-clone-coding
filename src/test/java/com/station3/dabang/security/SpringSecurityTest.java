package com.station3.dabang.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class SpringSecurityTest {

	@Test
	@DisplayName("유저 정보를 가져올 수 있다.")
	@WithMockUser(username="testUser")
	public void springLoginInfoTest() {
		//when
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		//then
		assertEquals(user.getUsername(), "testUser");
	}

}

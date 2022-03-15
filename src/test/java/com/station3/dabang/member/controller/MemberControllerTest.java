package com.station3.dabang.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.service.MemberService;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
    private MemberService memberService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String email = "admin@station3.co.kr";
	private static final String password = "test";
	
	@Test
	@DisplayName("회원가입")
	public void registerMemeber() throws Exception {
		//given
		MemberCreateRequest memeberCreateRequest = new MemberCreateRequest(email, password);
		MemberCreateResponse memberCreateResponse = new MemberCreateResponse(1L, email, password);
		doReturn(memberCreateResponse).when(memberService).create(any());
		
		//when
		mockMvc.perform(
				post("/members")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memeberCreateRequest))
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("email").value(email))
		.andExpect(jsonPath("password").value(password));
	}

}

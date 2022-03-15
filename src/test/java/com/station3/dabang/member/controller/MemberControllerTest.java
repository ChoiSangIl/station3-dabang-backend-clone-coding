package com.station3.dabang.member.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
public class MemberControllerTest {

	private MockMvc mockMvc;
	private MemberController memberController;
	
	@BeforeEach
	private void init() {
		memberController = new MemberController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
	}
	
	@Test
	public void 회원가입_API호출_테스트() throws Exception {
		//given
		Map<String, String> memeberCreateForm = new HashMap<String, String>();
		memeberCreateForm.put("email", "admin@station3.co.kr");
		memeberCreateForm.put("password", "test");
		 
		//when
		MvcResult mvcResult = mockMvc.perform(
					post("/members")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(memeberCreateForm))
				)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		
		//then
		assertEquals("ok", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void 회원가입시_필수값이없으면_오류() {
		
	}

}

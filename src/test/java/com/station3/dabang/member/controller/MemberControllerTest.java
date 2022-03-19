package com.station3.dabang.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.request.MemberLoginRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.controller.dto.response.MemberLoginResponse;
import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Password;
import com.station3.dabang.member.service.MemberService;

@WebMvcTest(controllers = MemberController.class, 
	excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurerAdapter.class}) 
})
public class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
    private MemberService memberService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Email email = new Email("dabang@station3.co.kr");
	private static final Password password = new Password("Station3#");
	private static final String jwtToken = "jwtTokenTestValue";
	
	@Test
	@DisplayName("회원가입")
	@WithMockUser
	public void registerMemeber() throws Exception {
		//given
		MemberCreateRequest memeberCreateRequest = new MemberCreateRequest(email.getValue(), password.getValue());
		MemberCreateResponse memberCreateResponse = new MemberCreateResponse(1L, email, jwtToken);
		
		doReturn(memberCreateResponse).when(memberService).create(any());
		
		//when
		mockMvc.perform(
				post("/members")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memeberCreateRequest))
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(1L))
		.andExpect(jsonPath("email.value").value(email.getValue()))
		.andExpect(jsonPath("jwtToken").value(jwtToken));
	}


	@Test
	@DisplayName("아이디 암호로 로그인 할 수 있어야한다.")
	@WithMockUser
	public void testLogin() throws JsonProcessingException, Exception {
		//given
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(email.getValue(), password.getValue());
		MemberLoginResponse memberLoginResponse = new MemberLoginResponse(1L, email, jwtToken);

		doReturn(memberLoginResponse).when(memberService).login(any());
		
		//when
		mockMvc.perform(
				post("/members/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memberLoginRequest))
				.with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(1L))
		.andExpect(jsonPath("email.value").value(email.getValue()))
		.andExpect(jsonPath("jwtToken").value(jwtToken));
	}

}

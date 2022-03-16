package com.station3.dabang.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.request.MemberLoginRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.controller.dto.response.MemberLoginResponse;
import com.station3.dabang.member.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

/**
* @Project : station3.dabang.project
* @FileName : MemberController.java
* @Date : 2022. 3. 15.
* @author : Choi Sang Il
* @description : 회원 관련 api
*/
@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
@Api( tags = "회원관련 API")
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping
    @Operation(summary = "회원가입", description = "회원가입 api")
	private MemberCreateResponse create(@RequestBody MemberCreateRequest memeberCreateRequest) {
		return memberService.create(memeberCreateRequest);
	}
	
	@PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 api")
	private MemberLoginResponse login(@RequestBody MemberLoginRequest memeberLoginRequest) {
		return memberService.login(memeberLoginRequest);
	}
}

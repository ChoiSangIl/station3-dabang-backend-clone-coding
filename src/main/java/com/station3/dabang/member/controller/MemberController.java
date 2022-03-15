package com.station3.dabang.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station3.dabang.member.controller.dto.request.MemeberCreateRequest;

import io.swagger.v3.oas.annotations.Operation;

/**
* @Project : station3.dabang.project
* @FileName : MemberController.java
* @Date : 2022. 3. 15.
* @author : Choi Sang Il
* @description : 회원 관련 api
*/
@RequestMapping("/members")
@RestController
public class MemberController {
	
	@PostMapping
    @Operation(summary = "회원가입", description = "회원가입 api")
	private String create(@RequestBody MemeberCreateRequest memeberCreateRequest) {
		return "ok";
	}
}

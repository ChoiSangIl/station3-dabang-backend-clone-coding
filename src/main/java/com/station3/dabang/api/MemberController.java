package com.station3.dabang.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Project : station3.dabang.project
* @FileName : MemberController.java
* @Date : 2022. 3. 15.
* @author : Choi Sang Il
* @description : 회원 관련 api
*/

@RequestMapping("/members")
public class MemberController {
	
	@PostMapping
	private String joinMember() {
		return "success";
	}
	
	@GetMapping
	private String test() {
		return "success";
	}
}

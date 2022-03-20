package com.station3.dabang;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner{
	
	private final MemberService memberService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MemberCreateRequest memberCreateRequest = new MemberCreateRequest("dabang@station3.co.kr", "Station3#");
		memberService.create(memberCreateRequest);
	}
}

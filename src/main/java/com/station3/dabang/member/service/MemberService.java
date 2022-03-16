package com.station3.dabang.member.service;

import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.request.MemberLoginRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;
import com.station3.dabang.member.controller.dto.response.MemberLoginResponse;

public interface MemberService {	
	/**
	 * 회원 가입
	 * @param memberCreateRequest
	 * @return
	 */
	public MemberCreateResponse create(MemberCreateRequest memberCreateRequest);
	
	/**
	 * 로그인
	 * @param memberLoginRequest
	 * @return
	 */
	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest);
}

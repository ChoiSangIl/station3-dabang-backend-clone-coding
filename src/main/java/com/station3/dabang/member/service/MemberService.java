package com.station3.dabang.member.service;

import com.station3.dabang.member.controller.dto.request.MemberCreateRequest;
import com.station3.dabang.member.controller.dto.response.MemberCreateResponse;

public interface MemberService {	
	/**
	 * 회원 가입
	 * @param memberCreateRequest
	 * @return
	 */
	public MemberCreateResponse create(MemberCreateRequest memberCreateRequest);
}

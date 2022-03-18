package com.station3.dabang.room.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.domain.Deal;
import com.station3.dabang.room.domain.DealRepository;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomRepository;
import com.station3.dabang.room.service.RoomService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DealRepository dealRepository;

	@Override
	@Transactional
	public RoomCreateResponse registerRoom(RoomCreateRequest roomCreateRequest) {
		Room room = roomCreateRequest.toRoom();
		Member member = memberRepository.findByEmail(new Email(getUserEmail()));

		//roomCreateRequest.toDeals().forEach(obj -> room.addDeal(obj));
		//람다식으로 표현하면 room = roomRepository.save(room); 해당 함수를 쓸수가 없어서 테스트코드를 만들수가 없음.. 더좋은 방법을 생각해보자. 
		for(Deal deal:roomCreateRequest.toDeals()) {
			room.addDeal(deal);
		}

		room.setMember(member);
		room = roomRepository.save(room);
		dealRepository.saveAll(room.getDeals());
		return RoomCreateResponse.from(room);
	}

	public String getUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return user.getUsername();
	}
}

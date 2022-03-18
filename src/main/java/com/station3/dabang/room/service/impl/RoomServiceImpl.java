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
import com.station3.dabang.room.domain.DealRepository;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomRepository;
import com.station3.dabang.room.service.RoomService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService{

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	DealRepository dealRepository;
	
	@Override
	@Transactional
	public RoomCreateResponse registerRoom(RoomCreateRequest roomCreateRequest) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User)authentication.getPrincipal();
			
			Room room = roomCreateRequest.toRoom();
			Member member = memberRepository.findByEmail(new Email(user.getUsername()));

			roomCreateRequest.toDeals().forEach(obj->room.addDeal(obj));
			
			room.setMember(member);
			roomRepository.save(room);
			dealRepository.saveAll(room.getDeals());
			return RoomCreateResponse.from(room);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

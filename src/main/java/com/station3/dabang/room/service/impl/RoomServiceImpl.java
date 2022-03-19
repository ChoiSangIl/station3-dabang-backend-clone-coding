package com.station3.dabang.room.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.station3.dabang.common.exception.BizRuntimeException;
import com.station3.dabang.common.exception.ErrorCode;
import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.controller.dto.response.RoomDetailResponse;
import com.station3.dabang.room.controller.dto.response.RoomListResponse;
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
	
	/**
	 * 정책에 따라서 체크 안해도 될듯..!?
	 * @param memberId
	 */
	public void memberValidation(Long memberId) {
		Member member = memberRepository.getById(memberId);
		if(!member.getEmail().getValue().equals(getUserEmail())) {
    	  	throw new BizRuntimeException(ErrorCode.LOGIN_INFO_INVALID);
		}
	}

	@Override
	public RoomListResponse getRoomList(Long memberId) {
		memberValidation(memberId);
		return RoomListResponse.from(roomRepository.findByMemberId(memberId));
	}

	@Override
	public RoomDetailResponse getRoomDetail(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		if(!room.isPresent()) throw new BizRuntimeException(ErrorCode.ROOM_NOT_FOUND);
		return RoomDetailResponse.from(room.get());
	}

	@Override
	public HttpStatus deleteRoom(Long roomId) {
		Room room = roomRepository.findByRoomIdAndEmail(roomId, new Email(getUserEmail()));
		if(room == null) throw new BizRuntimeException(ErrorCode.NOT_AUTH_DELETE_ROOM); 
		else roomRepository.deleteById(roomId);
		return HttpStatus.OK;
	}
}

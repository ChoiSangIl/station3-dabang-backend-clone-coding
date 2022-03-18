package com.station3.dabang.room.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.room.controller.dto.request.RoomCreateDealDto;
import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.domain.DealRepository;
import com.station3.dabang.room.domain.DealType;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomRepository;
import com.station3.dabang.room.domain.RoomType;

@SpringBootTest
public class RoomServiceImplTest {

	RoomRepository roomRepository = mock(RoomRepository.class);
	DealRepository dealRepository = mock(DealRepository.class);
	MemberRepository memberRepository = mock(MemberRepository.class);
	RoomServiceImpl roomService = new RoomServiceImpl(roomRepository, memberRepository, dealRepository);
	
	private static final String email = "dabang@station3.co.kr";
	private static final String password = "Station3$";
	
	@Test
	@DisplayName("내방을 등록 할 수 있다.")
	@WithMockUser
	public void testRegisterRoom() {
		Member member = new Member(1L, email, password);
		List<RoomCreateDealDto> deals = new ArrayList<RoomCreateDealDto>();
		RoomCreateDealDto dealDto1 = new RoomCreateDealDto(DealType.MONTHLY, 5000, 50);
		RoomCreateDealDto dealDto2 = new RoomCreateDealDto(DealType.MONTHLY, 4000, 60);
		RoomCreateDealDto dealDto3 = new RoomCreateDealDto(DealType.YEARLY, 10000, 0);
		deals.add(dealDto1);
		deals.add(dealDto2);
		deals.add(dealDto3);
		
		RoomCreateRequest roomCreateRequest = new RoomCreateRequest(RoomType.ONE_ROOM, deals);
		doReturn(member).when(memberRepository).findByEmail(any());
		doReturn(new Room(1L, member, RoomType.ONE_ROOM)).when(roomRepository).save(any());
		
		//when
		RoomCreateResponse roomCreateResponse = roomService.registerRoom(roomCreateRequest);
		
		//then
		assertAll(
			()->assertEquals(roomCreateResponse.getRoomId(), 1L)
		);
	}
}

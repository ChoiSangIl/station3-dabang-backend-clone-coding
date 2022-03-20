package com.station3.dabang.room.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.room.controller.dto.common.RoomDealDto;
import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.request.RoomUpdateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.controller.dto.response.RoomDetailResponse;
import com.station3.dabang.room.controller.dto.response.RoomListResponse;
import com.station3.dabang.room.domain.Deal;
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
	EntityManager em = mock(EntityManager.class);
	RoomServiceImpl roomService = new RoomServiceImpl(roomRepository, memberRepository, dealRepository, em);

	private static final Long memberId = 1L;
	private static final String email = "dabang@station3.co.kr";
	private static final String password = "Station3$";
	private static final Member member = new Member(memberId, email, password);
	
	private Deal deal1 = new Deal(DealType.MONTHLY, 1000, 50);
	private Deal deal2 = new Deal(DealType.YEARLY, 5000, 0);
	private Deal deal3 = new Deal(DealType.MONTHLY, 1500, 40);
	
	private RoomDealDto dealDto1 = new RoomDealDto(DealType.MONTHLY, 5000, 50);
	private RoomDealDto dealDto2 = new RoomDealDto(DealType.MONTHLY, 4000, 60);
	private RoomDealDto dealDto3 = new RoomDealDto(DealType.YEARLY, 10000, 0);
	
	@Test
	@DisplayName("내방을 등록 할 수 있다.")
	@WithMockUser
	public void testRegisterRoom() {
		//given
		List<RoomDealDto> deals = new ArrayList<RoomDealDto>();
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

	@Test
	@DisplayName("내방 리스트를 가져온다.")
	@WithMockUser(username = email)
	public void testGetMemberRoomList() {
		//given
		Room room = new Room(1L, member, RoomType.ONE_ROOM);
		Optional<Member> optionalMember = Optional.ofNullable(member);
		List<Room> rooms = new ArrayList<Room>();
		room.setMember(member);
		room.addDeal(deal1);
		room.addDeal(deal2);
		room.addDeal(deal3);
		rooms.add(room);
		doReturn(optionalMember).when(memberRepository).findById(anyLong());
		doReturn(rooms).when(roomRepository).findByMemberId(anyLong());
		
		//when
		RoomListResponse roomListResponse = roomService.getMemberRoomList(memberId);
		
		//then
		assertAll(
			()->assertEquals(roomListResponse.getRooms().size(), 1)
		);
	}

	@Test
	@DisplayName("내방 하나를 조회한다.")
	public void testGetRoomDetail() {
		//GIVEN
		Room room = new Room(1L, member, RoomType.ONE_ROOM);
		room.setMember(member);
		room.addDeal(deal1);
		room.addDeal(deal2);
		room.addDeal(deal3);
		Optional<Room> optionalRoom = Optional.ofNullable(room);
		
		doReturn(optionalRoom).when(roomRepository).findById(anyLong());
		
		//when
		RoomDetailResponse roomDetailResponse = roomService.getRoomDetail(optionalRoom.get().getId());
		
		//then
		assertAll(
			()->assertEquals(roomDetailResponse.getRoom().getRoomType(), optionalRoom.get().getType())
		);
	}
	
	@Test
	@DisplayName("내방을 삭제한다")
	@WithMockUser(username = email)
	public void testDeleteRoom() {
		//GIVEN
		Room room = new Room(1L, member, RoomType.ONE_ROOM);
		room.setMember(member);
		room.addDeal(deal1);
		room.addDeal(deal2);
		room.addDeal(deal3);
		
		doReturn(room).when(roomRepository).findByRoomIdAndEmail(anyLong(), any());
		
		//when
		HttpStatus httpStatus = roomService.deleteRoom(room.getId());
		
		//then
		assertAll(
			()->assertEquals(httpStatus, HttpStatus.OK)
		);
	}

	@Test
	@DisplayName("내방을 수정한다")
	@WithMockUser(username = email)
	public void testUpdateRoom() {
		List<RoomDealDto> roomUpdateDeals = new ArrayList<RoomDealDto>();
		roomUpdateDeals.add(new RoomDealDto (DealType.MONTHLY, 3000, 30));
		roomUpdateDeals.add(new RoomDealDto (DealType.MONTHLY, 5000, 20));
		roomUpdateDeals.add(new RoomDealDto (DealType.YEARLY, 10000, 0));
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest(1L, RoomType.ONE_ROOM, roomUpdateDeals);
		doReturn(roomUpdateRequest.toRoom()).when(roomRepository).findByRoomIdAndEmail(anyLong(), any());
		
		HttpStatus httpStatus = roomService.updateRoom(roomUpdateRequest);
		
		//then
		assertAll(
			()->assertEquals(httpStatus, HttpStatus.OK)
		);
	}

	@Test
	public void testGetRoomList() {
	}
}

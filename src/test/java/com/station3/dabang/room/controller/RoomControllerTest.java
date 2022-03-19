package com.station3.dabang.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.room.controller.dto.common.RoomDealDto;
import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.request.RoomUpdateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.controller.dto.response.RoomDetailResponse;
import com.station3.dabang.room.controller.dto.response.RoomListResponse;
import com.station3.dabang.room.domain.Deal;
import com.station3.dabang.room.domain.DealType;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomType;
import com.station3.dabang.room.service.RoomService;


@WebMvcTest(controllers = RoomController.class, 
	excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurerAdapter.class}) 
})
public class RoomControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RoomService roomService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private static final Long memberId = 1L;
	private static final String email = "dabang@station3.co.kr";
	private static final String password = "Station3$";
	private static final Member member = new Member(memberId, email, password);
	
	@Test
	@DisplayName("내방 등록 api 호출 테스트")
	@WithMockUser
	public void testRegisterRoom() throws JsonProcessingException, Exception {
		//given
		List<RoomDealDto> dealList = new ArrayList<RoomDealDto>();
		dealList.add(new RoomDealDto (DealType.MONTHLY, 3000, 30));
		dealList.add(new RoomDealDto (DealType.MONTHLY, 3000, 30));
		dealList.add(new RoomDealDto (DealType.YEARLY, 10000, 0));
		RoomCreateRequest roomCreateRequest = new RoomCreateRequest(RoomType.ONE_ROOM, dealList);
		RoomCreateResponse roomCreateResponse = new RoomCreateResponse(1L);
		roomCreateResponse.setRoomId(1);
		doReturn(roomCreateResponse).when(roomService).registerRoom(any());
		
		//when
		mockMvc.perform(
				post("/rooms")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(roomCreateRequest))
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("roomId").value(1));
	}
	
	@Test
	@DisplayName("내방 전체 리스트를 가져올 수 있다.")
	@WithMockUser
	public void testGetMyRooms() throws JsonProcessingException, Exception {
		//given
		List<Room> rooms = new ArrayList<Room>();
		Room room = new Room(RoomType.ONE_ROOM);
		room.addDeal(new Deal (DealType.MONTHLY, 3000, 30));
		room.addDeal(new Deal (DealType.MONTHLY, 5000, 20));
		room.addDeal(new Deal(DealType.YEARLY, 10000, 0));
		room.setMember(member);
		rooms.add(room);
		RoomListResponse roomListResponse = RoomListResponse.from(rooms);
		
		doReturn(roomListResponse).when(roomService).getRoomList(anyLong());
		
		//when
		mockMvc.perform(
				get("/rooms/me/1")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		)
		
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("rooms[0].roomType").value(RoomType.ONE_ROOM.toString()));
	}

	@Test
	@DisplayName("내방 하나를 조회한다")
	@WithMockUser
	public void testGetRoom() throws Exception {
		//given
		List<Room> rooms = new ArrayList<Room>();
		Room room = new Room(RoomType.ONE_ROOM);
		room.addDeal(new Deal (DealType.MONTHLY, 3000, 30));
		room.addDeal(new Deal (DealType.MONTHLY, 5000, 20));
		room.addDeal(new Deal(DealType.YEARLY, 10000, 0));
		room.setMember(member);
		rooms.add(room);
		RoomDetailResponse roomDeatilResponse = RoomDetailResponse.from(room);
		doReturn(roomDeatilResponse).when(roomService).getRoomDetail(any());
		
		//when
		mockMvc.perform(
				get("/rooms/1")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		)
		//then
		.andExpect(status().isOk())
		.andExpect(jsonPath("room.roomType").value(RoomType.ONE_ROOM.toString()));
	}
	
	@Test
	@DisplayName("내방 하나를 삭제한다.")
	@WithMockUser
	public void testDeleteRoom() throws Exception {
		//given
		doReturn(HttpStatus.OK).when(roomService).deleteRoom(anyLong());
		
		//when
		mockMvc.perform(
				delete("/rooms/1")
				.contentType(MediaType.APPLICATION_JSON)
		        .with(csrf())
		)
		//then
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("내방을 수정한다.")
	@WithMockUser
	public void testUpdateRoom() throws Exception {
		//given
		List<RoomDealDto> roomUpdateDeals = new ArrayList<RoomDealDto>();
		roomUpdateDeals.add(new RoomDealDto (DealType.MONTHLY, 3000, 30));
		roomUpdateDeals.add(new RoomDealDto (DealType.MONTHLY, 5000, 20));
		roomUpdateDeals.add(new RoomDealDto (DealType.YEARLY, 10000, 0));
		
		doReturn(HttpStatus.OK).when(roomService).updateRoom(any());
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest(1L, RoomType.ONE_ROOM, roomUpdateDeals);
		
		//when
		mockMvc.perform(
				put("/rooms/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(roomUpdateRequest))
		        .with(csrf())
		)
		//then
		.andExpect(status().isOk());
	}
}

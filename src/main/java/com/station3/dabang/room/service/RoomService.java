package com.station3.dabang.room.service;

import org.springframework.http.HttpStatus;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.request.RoomSearchRequest;
import com.station3.dabang.room.controller.dto.request.RoomUpdateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.controller.dto.response.RoomDetailResponse;
import com.station3.dabang.room.controller.dto.response.RoomListResponse;

public interface RoomService {
	/**
	 * 방 등록
	 * @param request
	 * @return
	 */
	RoomCreateResponse registerRoom(RoomCreateRequest request);
	
	/**
	 * 회원 방 리스트 가져오기
	 * @param memberId
	 * @return
	 */
	RoomListResponse getMemberRoomList(Long memberId);
	

	/**
	 * 방 검색
	 * @param roomSearchRequest
	 * @return
	 */
	RoomListResponse getRoomList(RoomSearchRequest roomSearchRequest);
	
	/**
	 * 방 상세 정보 가져오기
	 * @param roomId
	 * @return
	 */
	RoomDetailResponse getRoomDetail(Long roomId);
	
	/**
	 * 내방 삭제하기
	 * @param roomId
	 * @return
	 */
	HttpStatus deleteRoom(Long roomId);
	
	/**
	 * 내방 수정하기
	 * @param roomUpdateRequest
	 * @return
	 */
	HttpStatus updateRoom(RoomUpdateRequest roomUpdateRequest);
}

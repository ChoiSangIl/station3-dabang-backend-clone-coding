package com.station3.dabang.room.service;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
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
	RoomListResponse getRoomList(Long memberId);
	
	/**
	 * 방 상세 정보 가져오기
	 * @param roomId
	 * @return
	 */
	RoomDetailResponse getRoomDetail(Long roomId);
}

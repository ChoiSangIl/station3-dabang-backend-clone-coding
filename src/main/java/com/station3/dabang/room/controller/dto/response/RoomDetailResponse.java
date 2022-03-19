package com.station3.dabang.room.controller.dto.response;

import com.station3.dabang.room.controller.dto.common.RoomDto;
import com.station3.dabang.room.domain.Room;

import lombok.Getter;

@Getter
public class RoomDetailResponse {
	private RoomDto room;
	
	public RoomDetailResponse(RoomDto room) {
		this.room = room;
	}
	
	public static RoomDetailResponse from(Room room) {
		return new RoomDetailResponse(RoomDto.from(room));
	}
}

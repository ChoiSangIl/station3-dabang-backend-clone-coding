package com.station3.dabang.room.controller.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.station3.dabang.room.controller.dto.common.RoomDto;
import com.station3.dabang.room.domain.Room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomListResponse {
	private List<RoomDto> rooms;

	public RoomListResponse(List<RoomDto> rooms) {
		this.rooms = rooms;
	}

	public static RoomListResponse from(List<Room> rooms) {
		List<RoomDto> roomList = new ArrayList<RoomDto>();
		rooms.forEach(room -> {
			roomList.add(RoomDto.from(room));
		});
		return new RoomListResponse(roomList);
	}
}

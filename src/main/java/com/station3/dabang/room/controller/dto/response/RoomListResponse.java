package com.station3.dabang.room.controller.dto.response;

import java.util.List;

import com.station3.dabang.room.controller.dto.common.RoomDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomListResponse {
	private List<RoomDto> rooms;
}

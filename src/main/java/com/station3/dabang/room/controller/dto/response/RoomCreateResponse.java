package com.station3.dabang.room.controller.dto.response;

import com.station3.dabang.room.domain.Room;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class RoomCreateResponse {
	long roomId;
	
	public RoomCreateResponse(long roomId){
		this.roomId = roomId;
	}
	
	public static RoomCreateResponse from (Room room) {
		return new RoomCreateResponse(room.getId());
	}
}

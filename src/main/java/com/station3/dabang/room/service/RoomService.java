package com.station3.dabang.room.service;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;

public interface RoomService {
	RoomCreateResponse registerRoom(RoomCreateRequest request);
}

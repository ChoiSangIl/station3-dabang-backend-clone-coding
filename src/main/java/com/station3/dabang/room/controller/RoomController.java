package com.station3.dabang.room.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;


@RequestMapping("/rooms")
@RestController
@RequiredArgsConstructor
@Api( tags = "내방관련 API")
public class RoomController {

	private final RoomService roomService;
	
	@PostMapping()
    @Operation(summary = "내방등록", description = "내방등록 api")
	private RoomCreateResponse registerRoom(@RequestBody @Valid RoomCreateRequest roomCreateRequest) {
		return roomService.registerRoom(roomCreateRequest);
	}
	
	@GetMapping()
    @Operation(summary = "내방가져오기", description = "내방가져오 api")
	private String getRooms(@RequestBody @Valid RoomCreateRequest request) {
		return "abc";
	}
}

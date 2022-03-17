package com.station3.dabang.room.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;


@RequestMapping("/rooms")
@RestController
@Api( tags = "내방관련 API")
public class RoomController {

	@PostMapping()
    @Operation(summary = "내방등록", description = "내방등록 api")
	private String registerRoom(@RequestBody RoomCreateRequest request) {
		request.getDealList().forEach(x->System.out.println(x.toString()));
		return "abc";
	}
	
	@GetMapping()
    @Operation(summary = "내방가져오기", description = "내방가져오 api")
	private String getRooms(@RequestBody RoomCreateRequest request) {
		return "abc";
	}
}

package com.station3.dabang.room.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.service.RoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	
	@GetMapping("/me/{memberId}")
    @Operation(summary = "내방가져오기", description = "내방가져오 api (id->고유아이디 email X)")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "memberId", value = "회원 고유id", required = true, dataType = "int", paramType = "path", defaultValue = "1")
	})
	private String getRooms(@PathVariable("memberId") int memberId) {
		return "abc";
	}
	
	@PatchMapping("/{roomId}")
    @Operation(summary = "내방수정하기", description = "내방수정 api")
	private String modifyRoom(@PathVariable("roomId") int roomId) {
		return "abc";
	}
	
	@DeleteMapping("/{roomId}")
    @Operation(summary = "내방삭제하기", description = "내방삭제 api")
	private String deleteRoom(@PathVariable("roomId") int roomId) {
		return "abc";
	}
}

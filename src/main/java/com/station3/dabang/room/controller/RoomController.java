package com.station3.dabang.room.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.request.RoomSearchRequest;
import com.station3.dabang.room.controller.dto.request.RoomUpdateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.controller.dto.response.RoomDetailResponse;
import com.station3.dabang.room.controller.dto.response.RoomListResponse;
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
	
	@GetMapping()
    @Operation(summary = "전체방 가져오기", description = "내방등록 api swagger position 이슈로인한 파라미터 정렬안됨...(참고:https://github.com/springfox/springfox/issues/3391)")
	private RoomListResponse getRooms(@Valid RoomSearchRequest roomCreateRequest) {
		return roomService.getRoomList(roomCreateRequest);
	}
	
	@PostMapping()
    @Operation(summary = "내방 등록하기", description = "내방등록 api")
	private RoomCreateResponse registerRoom(@RequestBody @Valid RoomCreateRequest roomCreateRequest) {
		return roomService.registerRoom(roomCreateRequest);
	}
	
	@GetMapping("/me/{memberId}")
    @Operation(summary = "내방전체 리스트 가져오기", description = "내방가져오 api (id->고유아이디 email X)")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "memberId", value = "회원 고유id example-1", required = true, dataType = "long", paramType = "path", defaultValue = "1")
	})
	private RoomListResponse getRooms(@PathVariable("memberId") Long memberId) {
		return roomService.getMemberRoomList(memberId);
	}
	
	@GetMapping("/{roomId}")
    @Operation(summary = "내방 가져오기", description = "내방 가져오기")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "roomId", value = "내방id", required = true, dataType = "long", paramType = "path", defaultValue = "1")
	})
	private RoomDetailResponse getRoom(@PathVariable("roomId") Long roomId) {
		return roomService.getRoomDetail(roomId);
	}
	
	@PutMapping("/{roomId}")
    @Operation(summary = "내방수정하기", description = "내방수정 api")
	private HttpStatus updateRoom(@PathVariable("roomId") Long roomId, @Valid @RequestBody RoomUpdateRequest roomUpdateRequest) {
		roomUpdateRequest.setRoomId(roomId);
		return roomService.updateRoom(roomUpdateRequest);
	}
	
	@DeleteMapping("/{roomId}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "roomId", value = "내방id", required = true, dataType = "long", paramType = "path", defaultValue = "1")
	})
    @Operation(summary = "내방삭제하기", description = "내방삭제 api")
	private HttpStatus deleteRoom(@PathVariable("roomId") Long roomId) {
		return roomService.deleteRoom(roomId);
	}
}

package com.station3.dabang.room.controller.dto.request;

import java.util.List;

import com.station3.dabang.room.domain.RoomType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RoomCreateRequest {
	
	@ApiModelProperty(value = "방유형", example = "ONE_ROOM", allowableValues = "ONE_ROOM, TWO_ROOM, THREE_ROOM", required = true)
	private RoomType roomType;
	
	@ApiModelProperty(value = "거래정보", required = true)	
	private List<RoomCreateDealDto> dealList;
	
}

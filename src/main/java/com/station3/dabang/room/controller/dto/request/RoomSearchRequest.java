package com.station3.dabang.room.controller.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomSearchRequest {
	@ApiModelProperty(name = "offset", value="페이징 시작 index example-0", dataType = "int", required = true)
	private int offset;	

	@ApiModelProperty(name="limit", value="페이징 가져올 건수 example-10", dataType = "int", required = true)
	private int limit;

	@ApiModelProperty(value = "방 유형", example = "ONE_ROOM", allowableValues = "ONE_ROOM, TWO_ROOM, THREE_ROOM")
	private String roomType = "";
	
	@ApiModelProperty(value = "거래유형(MONTHLY-월세/YEARLY-전세)", example = "MONTHLY", allowableValues = "MONTHLY, YEARLY")
	private String dealType = "";
	
	@ApiModelProperty(value = "보증금/전세가 시작 (단위 만원)", dataType = "int")
	private Integer depositStart = null;
	
	@ApiModelProperty(value = "보증금/전세가 종료 (단위 만원)", dataType = "int")
	private Integer depositEnd = null;
	
	@ApiModelProperty(value = "월세 시작 (단위 만원)", dataType = "int")
	private Integer priceStart = null;
	
	@ApiModelProperty(value = "월세 종료 (단위 만원)", dataType = "int")
	private Integer priceEnd = null;
}

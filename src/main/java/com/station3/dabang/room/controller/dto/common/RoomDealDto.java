package com.station3.dabang.room.controller.dto.common;

import com.station3.dabang.room.domain.Deal;
import com.station3.dabang.room.domain.DealType;
import com.station3.dabang.room.domain.Room;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class RoomDealDto {
	
	@ApiModelProperty(value = "거래유형", example = "MONTHLY", allowableValues = "MONTHLY, YEARLY", required = true)
	private DealType dealType;
	
	@ApiModelProperty(value = "보증금 or 전세금액", example="1000")
	private int deposit;
	
	@ApiModelProperty(value = "월세", example="50")
	private int price;
	
	public Deal toDeal(Room room) {
		return new Deal(room, this.dealType, this.deposit, this.price);
	}
	
}

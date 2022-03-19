package com.station3.dabang.room.controller.dto.common;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.station3.dabang.room.domain.Deal;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomDto {
	
	@ApiModelProperty(value = "방유형")
	private RoomType roomType;
	
	@ApiModelProperty(value = "거래정보", required = true)
	@NotNull
	private List<RoomDealDto> dealList = new ArrayList<RoomDealDto>();
	
	public Room toRoom() {
		return new Room(roomType);
	}
	
	public List<Deal> toDeals(){
		List<Deal> deals = new ArrayList<Deal>();
		dealList.forEach(obj-> deals.add(new Deal(obj.getDealType(), obj.getDeposit(), obj.getPrice())));
		return deals;
	}
}

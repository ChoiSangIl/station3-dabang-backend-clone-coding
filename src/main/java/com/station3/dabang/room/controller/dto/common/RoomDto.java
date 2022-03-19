package com.station3.dabang.room.controller.dto.common;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.station3.dabang.room.domain.Deal;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RoomDto {
	
	@ApiModelProperty(value = "방유형")
	private RoomType roomType;
	
	@ApiModelProperty(value = "거래정보", required = true)
	@NotNull
	private List<RoomDealDto> dealList = new ArrayList<RoomDealDto>();
	
	public RoomDto(RoomType roomType) {
		this.roomType = roomType;
	}
	
	private RoomDto(RoomType roomType, List<RoomDealDto> dealList) {
		this.roomType = roomType;
		this.dealList = dealList;	
	}
	
	public Room toRoom() {
		return new Room(roomType);
	}
	
	public static RoomDto from(RoomType roomType, List<Deal> deals) {
		return new RoomDto(roomType, fromDeals(deals));
	}
	
	public static List<RoomDealDto> fromDeals(List<Deal> deals){
		List<RoomDealDto> DealsDto = new ArrayList<RoomDealDto>();
		deals.forEach(obj-> DealsDto.add(new RoomDealDto(obj.getType(), obj.getDeposit(), obj.getPrice())));
		return DealsDto;
	}
	
	public List<Deal> toDeals(){
		List<Deal> deals = new ArrayList<Deal>();
		dealList.forEach(obj-> deals.add(new Deal(obj.getDealType(), obj.getDeposit(), obj.getPrice())));
		return deals;
	}
}

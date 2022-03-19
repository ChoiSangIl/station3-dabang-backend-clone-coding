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
	@ApiModelProperty(value = "room id")
	private Long roomId;	
	
	@ApiModelProperty(value = "방유형")
	private RoomType roomType;

	@ApiModelProperty(value = "멤버id")
	private Long memberId;	

	@ApiModelProperty(value = "멤버 email")
	private String email;
	
	@ApiModelProperty(value = "거래정보", required = true)
	@NotNull
	private List<RoomDealDto> dealList = new ArrayList<RoomDealDto>();
	
	public RoomDto(RoomType roomType) {
		this.roomType = roomType;
	}
	
	private RoomDto(RoomType roomType, List<RoomDealDto> dealList, Long roomId, Long memberId, String email) {
		this.roomType = roomType;
		this.dealList = dealList;	
		this.roomId = roomId;
		this.memberId = memberId;
		this.email = email;
	}
	
	public Room toRoom() {
		return new Room(roomType);
	}
	
	public static RoomDto from(Room room) {
		return new RoomDto(room.getType(), fromDeals(room.getDeals()), room.getId(), room.getMember().getId(), room.getMember().getEmail().getValue());
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

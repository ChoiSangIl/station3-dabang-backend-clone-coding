package com.station3.dabang.room.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.station3.dabang.common.domain.BaseEntity;
import com.station3.dabang.common.exception.BizRuntimeException;
import com.station3.dabang.common.exception.ErrorCode;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Deal extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ROOM_NUMBER")
	private Room room;
	
	@Column(name="DEAL_TYPE")
	@Enumerated(EnumType.STRING)
	private DealType type;
	
	private int deposit;
	
	private int price;
	
	public Deal() {
	}
	
	public Deal(DealType type, int deposit, int price) {
		verifyPrice(type, deposit, price);
		this.type = type;
		this.deposit = deposit;
		this.price = price;
	}
	
	public Deal(Room room, DealType type, int deposit, int price) {
		verifyPrice(type, deposit, price);
		this.room = room;
		this.type = type;
		this.deposit = deposit;
		this.price = price;
	}
	
	public Deal(Long dealId, DealType type, int deposit, int price) {
		verifyPrice(type, deposit, price);
		this.id = dealId;
		this.type = type;
		this.deposit = deposit;
		this.price = price;
	}
	
	public void verifyPrice(DealType type, int deposit, int price){
		if(type.equals(DealType.YEARLY) && price > 0) {
			throw new BizRuntimeException(ErrorCode.DEAL_NOT_VALID_01);
		}else if(type.equals(DealType.MONTHLY) && price == 0) {
			throw new BizRuntimeException(ErrorCode.DEAL_NOT_VALID_02);
		}else if(deposit==0 && price==0) {
			throw new BizRuntimeException(ErrorCode.DEAL_NOT_VALID_03);
		}
	}
	
	public void setRoom(Room room) {
		this.room = room;
		
		if(!room.getDeals().contains(this)) {
			room.getDeals().add(this);
		}
	}
}

package com.station3.dabang.room.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.station3.dabang.common.domain.BaseEntity;
import com.station3.dabang.member.domain.Member;

import lombok.Getter;

@Entity
@Getter
public class Room extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ROOM_NUMBER")
	private Long id;
	
	@Version
	private Long version;
	
	@OneToMany(mappedBy="room")
	List<Deal> deals = new ArrayList<Deal>();
	
	@OneToOne
	@JoinColumn(name="MEMBER_ID")
	Member member = new Member();
	
	@Column(name="ROOM_TYPE")
	@Enumerated(EnumType.STRING)
	private RoomType type;
	
	public void changeType(RoomType type) {
		this.type = type;
	}
	
	public Room() {
	}
	
	
	public Room(RoomType type) {
		this.type=type;
	}
	
	public Room(Long id, Member member, RoomType type) {
		this.id = id;
		this.member = member;
		this.type = type;
	}
	
	public Room(Member member, RoomType type) {
		this.member = member;
		this.type = type;
	}
	
	public Room(RoomType type, List<Deal> deal) {
		this.type = type;
		this.deals = deal;
	}
	
	public void addDeal(Deal deal) {
		//verify..
		
		this.deals.add(deal);
		if(deal.getRoom() != this) {
			deal.setRoom(this);
		}
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
}

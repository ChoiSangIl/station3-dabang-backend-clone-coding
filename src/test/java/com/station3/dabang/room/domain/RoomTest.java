package com.station3.dabang.room.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.station3.dabang.member.domain.Member;

public class RoomTest {
	private Room room;
	private final String email = "admin@station3.co.kr";
	private final String password = "Station3$";
	private final Member member = new Member(email, password);
	
	@Test
	@DisplayName("내방을 등록할 수 있다.")
	public void create() {
		//when
		room = new Room(member, RoomType.ONE_ROOM);
		
		//then
		assertAll(
			()->assertEquals(room.getMember().getEmail().getValue(), email),
			()->assertEquals(room.getMember().getPassword().getValue(), password),
			()->assertEquals(room.getType(), RoomType.ONE_ROOM)
		);
	}
	
	@Test
	@DisplayName("내방을 수정할 수 있다.")
	public void update() {
		//when
		room = new Room(member, RoomType.ONE_ROOM);
		room.changeType(RoomType.TWO_ROOM);
		
		//then
		assertAll(
			()->assertEquals(room.getType(), RoomType.TWO_ROOM)
		);
	}
	
	@Test
	@DisplayName("내방을 삭제할수 있다.")
	public void delete() {
		//when
		room = new Room(member, RoomType.ONE_ROOM);
		room = null;
		
		//then
		assertAll(
			()->assertNull(room)
		);
	}
}

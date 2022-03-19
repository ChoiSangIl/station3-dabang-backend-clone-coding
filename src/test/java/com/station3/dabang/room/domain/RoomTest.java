package com.station3.dabang.room.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.station3.dabang.common.exception.BizRuntimeException;
import com.station3.dabang.common.exception.ErrorCode;
import com.station3.dabang.member.domain.Member;

public class RoomTest {
	private Room room;
	private final String email = "dabang@station3.co.kr";
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
	
	@Test
	@DisplayName("거래유형을 추가할 수 있다.")
	public void addDeals() {
		//given
		room = new Room(member, RoomType.ONE_ROOM);
		Deal deal = new Deal(room, DealType.YEARLY, 1000, 0);
		
		//when
		room.addDeal(deal);
		
		//then
		assertAll(
			()->assertNotNull(room.getDeals())
		);
	}
	
	@Test
	@DisplayName("전세는 여러개 들어갈수 없다.")
	public void addDealsExceptionTest() {
		//given
		room = new Room(member, RoomType.ONE_ROOM);
		
		Deal deal1 = new Deal(room, DealType.YEARLY, 1000, 0);
		Deal deal2 = new Deal(room, DealType.YEARLY, 1100, 0);

		//when
		room.addDeal(deal1);
		ThrowingCallable callable = () -> room.addDeal(deal2);
		
		assertAll(
			()->assertNotNull(room.getDeals())
		);
		
		assertThatExceptionOfType(BizRuntimeException.class)
		.isThrownBy(callable)
		.withMessageMatching("전세는 1개만 들어갈 수 있습니다.");
	}
	
	@Test
	@DisplayName("월세인데 보증금과 월세가 중복되면 안된다.")
	public void monthlyPriceCheck() {
		//given
		room = new Room(member, RoomType.ONE_ROOM);
		
		Deal deal1 = new Deal(room, DealType.MONTHLY, 1000, 50);
		Deal deal2 = new Deal(room, DealType.MONTHLY, 5000, 30);
		Deal deal3 = new Deal(room, DealType.MONTHLY, 1000, 50);

		//when
		room.addDeal(deal1);
		room.addDeal(deal2);
		
		
		ThrowingCallable callable = () -> room.addDeal(deal3);
		
		assertAll(
			()->assertNotNull(room.getDeals())
		);
		
		assertThatExceptionOfType(BizRuntimeException.class)
		.isThrownBy(callable)
		.withMessageMatching(ErrorCode.DEAL_NOT_VALID_05.getMessage());
	}
}

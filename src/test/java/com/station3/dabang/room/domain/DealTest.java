package com.station3.dabang.room.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.station3.dabang.common.exception.BizRuntimeException;
import com.station3.dabang.common.exception.ErrorCode;

public class DealTest {
	
	private Deal deal;

	@Test
	@DisplayName("거래유형을 설정 할 수 있다.")
	public void create() {
		//when
		deal = new Deal(DealType.MONTHLY, 1000, 50);
		
		//then
		assertAll(
			()->assertEquals(deal.getType(), DealType.MONTHLY),
			()->assertEquals(deal.getDeposit(), 1000),
			()->assertEquals(deal.getPrice(), 50)
		);
		
	}
	
	@Test
	@DisplayName("거래유형이 전세일 경우 월세에 값이 입력되면 안된다.")
	public void priceExceptionTest1() {
		//when
		ThrowingCallable callable = () -> deal = new Deal(DealType.YEARLY, 10000, 50);;
		
		//then
		assertThatExceptionOfType(BizRuntimeException.class)
		.isThrownBy(callable)
		.withMessageMatching(ErrorCode.DEAL_NOT_VALID_01.getMessage());
	}
	
	@Test
	@DisplayName("월세인데 보증감에만 값이 들어가면 안된다.")
	public void priceExceptionTest3() {
		//when
		ThrowingCallable callable = () -> deal = new Deal(DealType.MONTHLY, 1000, 0);;
		
		//then
		assertThatExceptionOfType(BizRuntimeException.class)
		.isThrownBy(callable)
		.withMessageMatching(ErrorCode.DEAL_NOT_VALID_02.getMessage());
	}
	
	@Test
	@DisplayName("보증금/전세와 월세 금액이 0으로 들어가면 안된다")
	public void priceExceptionTest2() {
		//when
		ThrowingCallable callable = () -> deal = new Deal(DealType.YEARLY, 0, 0);;
		
		//then
		assertThatExceptionOfType(BizRuntimeException.class)
		.isThrownBy(callable)
		.withMessageMatching(ErrorCode.DEAL_NOT_VALID_03.getMessage());
	}
}

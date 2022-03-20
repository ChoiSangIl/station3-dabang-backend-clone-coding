package com.station3.dabang.room.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.station3.dabang.common.util.ObjectUtil;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.member.domain.QMember;
import com.station3.dabang.room.controller.dto.request.RoomSearchRequest;

@DataJpaTest
public class RoomRepositoryTest {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private DealRepository dealRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	public EntityManager em;

	private static final String email = "dabang@station3.co.kr";
	private static final String password = "Station3$";
	private static final Member member = new Member(email, password);

	private Room room;
	private Deal deal1;
	private Deal deal2;
	private Deal deal3;

	@BeforeEach
	public void dataInit() {
		// given
		room = new Room(RoomType.ONE_ROOM);
		deal1 = new Deal(DealType.MONTHLY, 1000, 50);
		deal2 = new Deal(DealType.YEARLY, 5000, 0);
		deal3 = new Deal(DealType.MONTHLY, 1500, 40);
	}

	@Test
	@DisplayName("방 조회, 삭제, 업데이트, 등록 테스트(CRUD)")
	@Transactional
	public void registerRoom() {
		// given
		room.member = memberRepository.save(member);
		room.addDeal(deal1);
		room.addDeal(deal2);
		room.addDeal(deal3);

		// when
		roomRepository.save(room);
		dealRepository.saveAll(room.getDeals());

		// then
		assertAll(() -> assertEquals(room.getType(), RoomType.ONE_ROOM),
				() -> assertEquals(room.getDeals().get(0).getType(), DealType.MONTHLY),
				() -> assertEquals(room.getDeals().get(0).getDeposit(), 1000),
				() -> assertEquals(room.getDeals().get(0).getPrice(), 50));

		em.clear(); // 실제 db에서 select 해오기 위해

		// when
		Room searchRoom = roomRepository.getById(room.getId());
		List<Deal> searchDeals = searchRoom.getDeals();

		// then
		assertAll(() -> assertNotNull(searchRoom), () -> assertEquals(searchDeals.size(), room.getDeals().size()));

		em.clear(); // 실제 db에서 select 해오기 위해

		// when
		List<Room> searchMyRoom = roomRepository.findByMemberId(member.getId());

		// then
		assertAll(() -> assertEquals(searchMyRoom.get(0).getType(), RoomType.ONE_ROOM),
				() -> assertEquals(searchMyRoom.get(0).getDeals().get(0).getType(), DealType.MONTHLY),
				() -> assertEquals(searchMyRoom.get(0).getDeals().get(0).getDeposit(), 1000),
				() -> assertEquals(searchMyRoom.get(0).getDeals().get(0).getPrice(), 50));

		Room searchDeleteTarget = roomRepository.findByRoomIdAndEmail(searchMyRoom.get(0).getId(), member.getEmail());
		roomRepository.deleteById(searchDeleteTarget.getId());
	}

	@Test
	@DisplayName("queryDslPagingTest")
	@Transactional
	public void queryDslTest() {

		JPAQueryFactory query = new JPAQueryFactory(em);
		QRoom room = QRoom.room;
		QDeal deal = QDeal.deal;
		QMember member = QMember.member;
		
		RoomSearchRequest roomSearchRequest = new RoomSearchRequest(0, 10, "", DealType.MONTHLY.toString(), null, 2000, 0, 40);
		
		BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtil.isEmpty(roomSearchRequest.getRoomType().toString())) {
			builder.and(room.type.eq(RoomType.valueOf(roomSearchRequest.getRoomType())));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getDealType().toString())) {
			builder.and(deal.type.eq(DealType.valueOf(roomSearchRequest.getDealType())));	
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getDepositStart())) {
			builder.and(deal.deposit.goe(roomSearchRequest.getDepositStart()));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getDepositEnd())) {
			builder.and(deal.deposit.loe(roomSearchRequest.getDepositEnd()));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getPriceStart())) {
			builder.and(deal.price.goe(roomSearchRequest.getPriceStart()));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getPriceEnd())) {
			builder.and(deal.price.loe(roomSearchRequest.getPriceEnd()));
		}

		List<Room> memberList = query
				.selectFrom(room)
				.join(room.deals, deal)
				.join(room.member, member).fetchJoin()
				.where(builder)
				.groupBy(room.id, room.type, member.email)
				.orderBy(room.id.desc())
				.offset(0)
				.limit(5)
				.fetch();
				

		memberList.forEach(obj -> System.out.println(obj.getId() + "_" + obj.getType() + "_"+ obj.getDeals().toString()));

	}
}

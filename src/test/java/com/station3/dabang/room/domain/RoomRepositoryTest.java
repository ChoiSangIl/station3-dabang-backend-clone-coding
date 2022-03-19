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

import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;

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
		//given
		room = new Room(RoomType.ONE_ROOM);
		deal1 = new Deal(DealType.MONTHLY, 1000, 50);
		deal2 = new Deal(DealType.YEARLY, 5000, 0);
		deal3 = new Deal(DealType.MONTHLY, 1500, 40);
	}
	
	@Test
	@DisplayName("방 조회, 삭제, 업데이트, 등록 테스트(CRUD)")
	@Transactional
	public void registerRoom() {
		//given
		room.member = memberRepository.save(member);
		room.addDeal(deal1);
		room.addDeal(deal2);
		room.addDeal(deal3);
		
		//when
		roomRepository.save(room);
		dealRepository.saveAll(room.getDeals());
		
		//then
		assertAll(
			()->assertEquals(room.getType(), RoomType.ONE_ROOM),
			()->assertEquals(room.getDeals().get(0).getType(), DealType.MONTHLY),
			()->assertEquals(room.getDeals().get(0).getDeposit(), 1000),
			()->assertEquals(room.getDeals().get(0).getPrice(), 50)
		);
		
		em.clear();	//실제 db에서 select 해오기 위해
		
		//when
		Room searchRoom = roomRepository.getById(room.getId());
		List<Deal> searchDeals = searchRoom.getDeals();
		
		//then
		assertAll(
			()->assertNotNull(searchRoom),
			()->assertEquals(searchDeals.size(), room.getDeals().size())
		);
		
		em.clear();	//실제 db에서 select 해오기 위해
		
		//when
		List<Room> searchMyRoom = roomRepository.findByMemberId(member.getId());
		
		//then
		assertAll(
			()->assertEquals(searchMyRoom.get(0).getType(), RoomType.ONE_ROOM),
			()->assertEquals(searchMyRoom.get(0).getDeals().get(0).getType(), DealType.MONTHLY),
			()->assertEquals(searchMyRoom.get(0).getDeals().get(0).getDeposit(), 1000),
			()->assertEquals(searchMyRoom.get(0).getDeals().get(0).getPrice(), 50)
		);

		Room searchDeleteTarget = roomRepository.findByRoomIdAndEmail(searchMyRoom.get(0).getId(), member.getEmail());
		roomRepository.deleteById(searchDeleteTarget.getId());
		
	}
}

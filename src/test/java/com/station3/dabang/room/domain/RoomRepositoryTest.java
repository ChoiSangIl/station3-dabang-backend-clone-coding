package com.station3.dabang.room.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.MemberRepository;

@SpringBootTest
public class RoomRepositoryTest {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private DealRepository dealRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	private static final String email = "admin@station3.co.kr";
	private static final String password = "Station3$";

	private Room room;
	private Deal deal1;
	private Deal deal2;
	private Deal deal3;
	
	@BeforeEach
	public void dataInit() {
		//given
		room = new Room(RoomType.ONE_ROOM);
		deal1 = new Deal(room, DealType.MONTHLY, 1000, 50);
		deal2 = new Deal(room, DealType.YEARLY, 5000, 0);
		deal3 = new Deal(room, DealType.MONTHLY, 1500, 40);
	}

	@Test
	@DisplayName("내방과 거래정보를 저장할 수 있어야한다.")
	@Transactional
	@Rollback(value = false)
	public void createRoom() {
		//when
		room.member = memberRepository.findByEmail(new Email(email));
		roomRepository.save(room);
		dealRepository.save(deal1);
		dealRepository.save(deal2);
		dealRepository.save(deal3);
	}
	
	@Test
	@DisplayName("전체방을 조회한다.")
	@Transactional
	@Rollback(value = false)
	public void searchAll() {
		Room room = roomRepository.getById(3L);
		
		List<Deal> deal = room.getDeal();
		deal.forEach(s->System.out.println(s.toString()));
	}

}

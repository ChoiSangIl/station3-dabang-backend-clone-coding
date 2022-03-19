package com.station3.dabang.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.station3.dabang.member.domain.Email;

public interface RoomRepository extends JpaRepository<Room, Long>{
	List<Room> findByMemberId(Long memberId);
	
	/**
	 * id와 이메일로 조회
	 * @param roomId
	 * @param email
	 * @return
	 */
	@Query(
			"select r from Room r "
			+ "inner join r.member m "
			+ "left outer join r.deals d "
			+ "where r.id = :roomId and m.email = :email" 
	)
	Room findByRoomIdAndEmail(Long roomId, Email email);
}

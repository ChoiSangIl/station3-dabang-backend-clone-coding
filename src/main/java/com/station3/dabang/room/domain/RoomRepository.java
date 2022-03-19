package com.station3.dabang.room.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>{
	List<Room> findByMemberId(Long memberId);
}

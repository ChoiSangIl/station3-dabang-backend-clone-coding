package com.station3.dabang.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByEmail(Email email);
	Member findByEmail(Email email);
	Member findByEmailAndPassword(Email email, Password password);
}

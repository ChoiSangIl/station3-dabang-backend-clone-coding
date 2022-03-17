package com.station3.dabang.member.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.station3.dabang.common.domain.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class Member extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MEMBER_ID")
	private Long id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;
	
	public Member() {
		
	}
	
	public Member(String email, String password) {
		this.email = new Email(email);
		this.password = new Password(password);
	}
	
	public Member(Long id, String email, String password) {
		this.id = id;
		this.email = new Email(email);
		this.password = new Password(password);
	}
}

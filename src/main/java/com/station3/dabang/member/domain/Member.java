package com.station3.dabang.member.domain;

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
	private Long id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;
	
	public Member(String email, String password) {
		this.email = new Email(email);
		this.password = new Password(password);
	}
}

package com.station3.dabang.member.domain;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;

@Getter
@Embeddable
public class Email {
	
	private static final Pattern pattern = Pattern.compile("^(.+)@(.+)$");
	
	@Column(name="email", unique = true)
	private String value;
	
	protected Email() {
	}
	
	public Email(String email){
		this.value = email;
	}
}

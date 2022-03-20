package com.station3.dabang.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;

@Getter
@Embeddable
public class Password {

    @Column(name="password")
	private String value;
	
	protected Password(){
	}
	
	public Password(String password){
		this.value = password;
	}
	
}

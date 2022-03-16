package com.station3.dabang.member.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;

import lombok.Getter;

@Getter
@Embeddable
public class Email {
	
	private static final Pattern pattern = Pattern.compile("^(.+)@(.+)$");
	
	@Column(name="email")
	private String value;
	
	protected Email() {
	}
	
	public Email(String email){
		verify(email);
		this.value = email;
	}
	
	private void verify(String email) {
		if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("이메일은 필수값입니다.");
        }
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일을 확인해주세요.");
        }
	}
}

package com.station3.dabang.member.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;

import lombok.Getter;

@Getter
@Embeddable
public class Password {

    // 최소 숫자 1자리 이상, 영어 소문자 1자리 이상, 영어 대문자 1자리 이상, 특수문자 1자리 이상, 공백문자 포함하지 않고, 8자리 이상
    private static final Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,}$");

    @Column(name="password")
	private String value;
	
	protected Password(){
	}
	
	public Password(String password){
		verify(password);
		this.value = password;
	}
	
	private void verify(String password) {
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호 형식을 확인해주세요(최소 숫자 1자리 이상, 영어 소문자 1자리 이상, 영어 대문자 1자리 이상, 특수문자 1자리 이상, 공백문자 포함하지 않고, 8자리 이상)");
        }
    }
}

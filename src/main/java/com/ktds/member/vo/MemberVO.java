package com.ktds.member.vo;

import javax.validation.constraints.NotEmpty;

public class MemberVO {

	private int id;
	@NotEmpty(message = "Nickname은 필수입력 값 입니다.")
	private String nickname;
	@NotEmpty(message = "PassWord 필수입력 값 입니다.")
	private String password;
	private String email;
	private String registDate;
	private String salt;
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public int setId(int id) {
		return this.id=id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
}
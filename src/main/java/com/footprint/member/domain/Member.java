package com.footprint.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Builder
	public Member(String username, String password, String nickname) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}


}
package com.footprint.follow.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Id;

import com.footprint.member.domain.Member;

@Getter
@Table(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Follow {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_id")
	private Member follower;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "followee_id")
	private Member followee;
}

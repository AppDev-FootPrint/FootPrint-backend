package com.footprint.heart.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.domain.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "heart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Heart {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "heart_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_travel_id")
	private MainTravel mainTravel;

}

package com.footprint.scrap.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.footprint.common.BaseTimeEntity;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.domain.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "scrap")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "scraped_date"))
public class Scrap extends BaseTimeEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)//TODO 삽입 시 성능 최적화를 위해서는 GenerationType이 IDENTITY이면 안됨
	@Column(name = "scrap_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_travel_id")
	private MainTravel mainTravel;
}

package com.footprint.price.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.footprint.detailtravel.domain.DetailTravel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Price {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)//TODO 삽입 시 성능 최적화를 위해서는 GenerationType이 IDENTITY이면 안됨
	@Column(name = "price_id")
	private Long id;

	@Column(name = "item")
	private String item;

	@Column(name = "price_info")
	private int priceInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detail_travel_id")
	private DetailTravel detailTravel;

}

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

import com.footprint.detailedtravel.domain.DetailTravel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Price {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

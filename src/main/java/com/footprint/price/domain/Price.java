package com.footprint.price.domain;

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

import com.footprint.detailedtravel.domain.DetailTravel;

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

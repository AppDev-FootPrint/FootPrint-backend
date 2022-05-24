package com.footprint.detailtravel.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.footprint.common.BaseTimeEntity;
import com.footprint.image.domain.Image;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.price.domain.Price;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "detail_travel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DetailTravel extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_travel_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_travel_id")
	private MainTravel mainTravel;

	@Column(name = "title")
	private String title;

	@Lob
	@Column(name = "review")
	private String review;

	@Column(name = "tip")
	private String tip;

	@Column(name = "visited_date")
	private LocalDate visitedDate;

	@Embedded
	private Address address;


	@OneToMany(mappedBy = "detailTravel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Price> prices = new ArrayList<>();

	@OneToMany(mappedBy = "detailTravel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Image> images = new ArrayList<>();


	@Builder
	public DetailTravel(String title, String review, String tip, LocalDate visitedDate, Address address) {
		this.title = title;
		this.review = review;
		this.tip = tip;
		this.visitedDate = visitedDate;
		this.address = address;
	}

	public void setMainTravel(MainTravel mainTravel) {
		this.mainTravel = mainTravel;
	}



	//TODO price 랑 dto 추가
	public void update(String title, String review, String tip, LocalDate visitedDate, Address address) {
		this.title = title;
		this.review = review;
		this.tip = tip;
		this.visitedDate = visitedDate;
		this.address = address;
	}
}

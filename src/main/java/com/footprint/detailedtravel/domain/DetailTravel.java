package com.footprint.detailedtravel.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;

import com.footprint.comment.domain.Comment;
import com.footprint.image.domain.Image;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.price.domain.Price;

@Getter
@Table(name = "detail_travel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DetailTravel {
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


}

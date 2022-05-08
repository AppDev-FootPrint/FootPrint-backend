package com.footprint.image.domain;

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
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long id;

	@Column(name = "path", nullable = false, unique = true)
	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detail_travel_id")
	private DetailTravel detailTravel;

}

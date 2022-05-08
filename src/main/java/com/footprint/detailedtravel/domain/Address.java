package com.footprint.detailedtravel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(name = "address")
	private String address;

	@Column(name = "road_address")
	private String roadAddress;

	@Column(name = "map_x")
	private int mapX;

	@Column(name = "map_y")
	private int mapY;
}

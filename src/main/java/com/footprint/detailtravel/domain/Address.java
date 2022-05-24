package com.footprint.detailtravel.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
	@Column(name = "address")
	private String address;

	@Column(name = "road_address")
	private String roadAddress;

	@Column(name = "map_x")
	private int mapX;

	@Column(name = "map_y")
	private int mapY;

	@Builder
	public Address(String address, String roadAddress, int mapX, int mapY) {
		this.address = address;
		this.roadAddress = roadAddress;
		this.mapX = mapX;
		this.mapY = mapY;
	}
}

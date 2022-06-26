package com.footprint.detailtravel.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Address address1 = (Address)o;
		return mapX == address1.mapX && mapY == address1.mapY && Objects.equals(address, address1.address)
			&& Objects.equals(roadAddress, address1.roadAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, roadAddress, mapX, mapY);
	}
}

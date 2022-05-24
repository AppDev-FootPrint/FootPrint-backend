package com.footprint.detailtravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/05/24.
 */
public interface DetailTravelRepository extends JpaRepository<DetailTravel, Long> {

	List<DetailTravel> findAllByMainTravelId(Long mainTravelId);
}

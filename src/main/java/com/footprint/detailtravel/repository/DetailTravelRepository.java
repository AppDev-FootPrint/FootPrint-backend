package com.footprint.detailtravel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.footprint.detailtravel.domain.DetailTravel;

/**
 * Created by ShinD on 2022/05/24.
 */
public interface DetailTravelRepository extends JpaRepository<DetailTravel, Long> {

	List<DetailTravel> findAllByMainTravelId(Long mainTravelId);

	@Query("select dt from DetailTravel dt join fetch dt.mainTravel mt join fetch mt.writer where dt.id =: id")
	Optional<DetailTravel> findWithMainTravelAndWriterById(@Param("id") Long id);
}

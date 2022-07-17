package com.footprint.maintravel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.domain.Member;

public interface MainTravelRepository extends JpaRepository<MainTravel, Long> {

	@EntityGraph(attributePaths = {"writer", "image"})
	Optional<MainTravel> findWithWriterAndImageById(Long travelId);

	List<MainTravel> findAllByWriter(Member writer);
}

package com.footprint.scrap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.scrap.domain.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
	boolean existsScrapByMemberIdAndMainTravelId(Long memberId, Long mainTravelId);

	@EntityGraph(attributePaths = {"mainTravel"})
	List<Scrap> findAllByMemberId(Long memberId);
}

package com.footprint.scrap.service;

import java.util.List;

import com.footprint.scrap.service.dto.ScrapDto;

public interface ScrapService {

	Long saveScrap(Long memberId, Long travelId);

	void deleteScrap(Long scrapId);

	List<ScrapDto> getScraps(Long memberId);
}

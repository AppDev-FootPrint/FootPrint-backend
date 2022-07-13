package com.footprint.scrap.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.footprint.scrap.controller.dto.ScrapResponse;
import com.footprint.scrap.service.ScrapService;
import com.footprint.scrap.service.dto.ScrapDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScrapController {
	private final ScrapService scrapService;

	@PostMapping("/members/{memberId}/scraps")
	public ResponseEntity<Long> saveScrap(@PathVariable Long memberId, @RequestParam Long travelId) {
		Long scrapId = scrapService.saveScrap(memberId, travelId);

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath().path("/scraps/")
			.buildAndExpand(scrapId).toUri();

		return ResponseEntity.created(location).body(scrapId);
	}

	@GetMapping("/members/{memberId}/scraps")
	public ResponseEntity<List<ScrapResponse>> saveScrap(@PathVariable Long memberId) {
		List<ScrapDto> scraps = scrapService.getScraps(memberId);
		return ResponseEntity.ok(scraps.stream().map(ScrapResponse::from).toList());
	}

	@DeleteMapping("/scraps/{scrapId}")
	public ResponseEntity<Void> deleteScrap(@PathVariable Long scrapId) {
		scrapService.deleteScrap(scrapId);
		return ResponseEntity.ok().build();
	}
}

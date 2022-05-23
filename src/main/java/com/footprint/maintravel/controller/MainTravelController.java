package com.footprint.maintravel.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.footprint.maintravel.controller.dto.MainTravelRequest;
import com.footprint.maintravel.controller.dto.MainTravelResponse;
import com.footprint.maintravel.service.MainTravelService;
import com.footprint.maintravel.service.dto.MainTravelDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainTravelController {
	private final MainTravelService mainTravelService;

	@GetMapping("/main-travels/{id}")
	public ResponseEntity<MainTravelResponse> getMainTravel(@PathVariable Long id) {
		MainTravelDto mainTravelDto = mainTravelService.getMainTravel(id);
		return ResponseEntity.ok(MainTravelResponse.from(mainTravelDto));
	}

	@PutMapping("/main-travels/{id}")
	public ResponseEntity<Long> updateMainTravel(@PathVariable Long id,
		@RequestBody MainTravelRequest updateRequest) {
		Long updateMainTravelId = mainTravelService.updateMainTravel(updateRequest.toUpdateDto(id));
		return ResponseEntity.ok(updateMainTravelId);
	}

	@PostMapping("/main-travels")
	public ResponseEntity<Long> saveMainTravel(@RequestBody MainTravelRequest saveRequest) {
		Long saveMainTravelId = mainTravelService.saveMainTravel(saveRequest.toSaveDto());

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath().path("/main-travels/")
			.buildAndExpand(saveMainTravelId).toUri();

		return ResponseEntity.created(location).body(saveMainTravelId);
	}

	@DeleteMapping("/main-travels/{id}")
	public ResponseEntity<Void> deleteMainTravel(@PathVariable Long id) {
		mainTravelService.deleteMainTravel(id);
		return ResponseEntity.ok().build();
	}
}

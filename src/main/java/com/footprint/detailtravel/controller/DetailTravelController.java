package com.footprint.detailtravel.controller;

import static com.footprint.auth.service.SecurityUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footprint.detailtravel.controller.dto.response.DetailTravelInfoResponse;
import com.footprint.detailtravel.service.DetailTravelService;
import com.footprint.detailtravel.service.dto.info.DetailTravelDto;

import lombok.RequiredArgsConstructor;

/**
 * Created by ShinD on 2022/05/25.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DetailTravelController {

	private final DetailTravelService detailTravelService;


	@GetMapping("/detail-travels/{id}")
	public ResponseEntity<DetailTravelInfoResponse> getDetailTravel(@PathVariable Long id) {
		DetailTravelDto detailTravelDto = detailTravelService.getById(getLoginMemberId(), id);
		return ResponseEntity.ok(DetailTravelInfoResponse.from(detailTravelDto));
	}

}

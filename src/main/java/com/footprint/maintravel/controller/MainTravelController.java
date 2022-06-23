// package com.footprint.maintravel.controller;
//
// import static com.footprint.auth.service.SecurityUtils.*;
//
// import java.net.URI;
//
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
// import com.footprint.maintravel.controller.dto.MainTravelResponse;
// import com.footprint.maintravel.controller.dto.UpdateMainTravelRequest;
// import com.footprint.maintravel.controller.dto.request.create.CreateMainTravelRequest;
// import com.footprint.maintravel.service.MainTravelService;
// import com.footprint.maintravel.service.dto.info.MainTravelDto;
//
// import lombok.RequiredArgsConstructor;
//
// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api")
// public class MainTravelController {
// 	private final MainTravelService mainTravelService;
//
//
//
//
// 	@GetMapping("/main-travels/{id}")
// 	public ResponseEntity<MainTravelResponse> getMainTravel(@PathVariable Long id) {
// 		MainTravelDto mainTravelDto = mainTravelService.getMainTravel(id);
// 		return ResponseEntity.ok(MainTravelResponse.from(mainTravelDto));
// 	}
//
//
//
//
//
//
// 	@PostMapping("/main-travels")
// 	public ResponseEntity<Long> saveMainTravel(@RequestBody CreateMainTravelRequest saveRequest) {
// 		Long saveMainTravelId = mainTravelService.saveMainTravel(getLoginMemberId(), saveRequest.toServiceDto());
//
// 		URI location = ServletUriComponentsBuilder
// 			.fromCurrentContextPath().path("/main-travels/")
// 			.buildAndExpand(saveMainTravelId).toUri();
//
// 		return ResponseEntity.created(location).body(saveMainTravelId);
// 	}
//
//
//
//
// 	@PutMapping("/main-travels/{id}")
// 	public ResponseEntity<Long> updateMainTravel(@PathVariable Long id,
// 		@RequestBody UpdateMainTravelRequest updateRequest) {
// 		Long updateMainTravelId = mainTravelService.updateMainTravel(getLoginMemberId(), updateRequest.toServiceDto(id));
// 		return ResponseEntity.ok(updateMainTravelId);
// 	}
//
//
//
//
//
//
//
// 	@DeleteMapping("/main-travels/{id}")
// 	public ResponseEntity<Void> deleteMainTravel(@PathVariable Long id) {
// 		mainTravelService.deleteMainTravel(getLoginMemberId(), id);
// 		return ResponseEntity.ok().build();
// 	}
// }

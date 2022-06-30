package com.footprint.scrap.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.footprint.maintravel.domain.MainTravel;
import com.footprint.maintravel.exception.MainTravelException;
import com.footprint.maintravel.exception.MainTravelExceptionType;
import com.footprint.maintravel.repository.MainTravelRepository;
import com.footprint.member.domain.Member;
import com.footprint.member.exception.MemberException;
import com.footprint.member.exception.MemberExceptionType;
import com.footprint.member.repository.MemberRepository;
import com.footprint.scrap.domain.Scrap;
import com.footprint.scrap.exception.ScrapException;
import com.footprint.scrap.exception.ScrapExceptionType;
import com.footprint.scrap.repository.ScrapRepository;
import com.footprint.scrap.service.dto.ScrapDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {
	private final ScrapRepository scrapRepository;
	private final MemberRepository memberRepository;
	private final MainTravelRepository mainTravelRepository;

	@Override
	public Long saveScrap(Long memberId, Long travelId) {
		if (existsScrap(memberId, travelId)) {
			throw new ScrapException(ScrapExceptionType.CONFLICT);
		}
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND));
		MainTravel mainTravel = mainTravelRepository.findById(travelId)
			.orElseThrow(() -> new MainTravelException(MainTravelExceptionType.NOT_FOUND));
		Scrap scrap = scrapRepository.save(Scrap.builder().member(member).mainTravel(mainTravel).build());
		return scrap.getId();
	}

	private boolean existsScrap(Long memberId, Long travelId) {
		return scrapRepository.existsScrapByMemberIdAndMainTravelId(memberId, travelId);
	}

	@Override
	public void deleteScrap(Long scrapId) {
		Scrap scrap = scrapRepository.findById(scrapId)
			.orElseThrow(() -> new ScrapException(ScrapExceptionType.NOT_FOUND));
		scrapRepository.delete(scrap);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ScrapDto> getScraps(Long memberId) {
		List<Scrap> scraps = scrapRepository.findAllByMemberId(memberId);
		return scraps.stream().map(ScrapDto::from).toList();
	}
}
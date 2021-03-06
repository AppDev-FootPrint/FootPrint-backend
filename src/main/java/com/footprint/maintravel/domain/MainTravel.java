package com.footprint.maintravel.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.footprint.comment.domain.Comment;
import com.footprint.common.BaseTimeEntity;
import com.footprint.detailtravel.domain.DetailTravel;
import com.footprint.heart.domain.Heart;
import com.footprint.image.domain.Image;
import com.footprint.member.domain.Member;
import com.footprint.scrap.domain.Scrap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "main_travel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MainTravel extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "main_travel_id")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "is_visible")
	private boolean isVisible;

	@Column(name = "like_num")
	private Integer likeNum;

	@Column(name = "is_completed", nullable = false)
	private boolean isCompleted;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;

	//TODO 배치쿼리 적용하려면 수정이 필요할 것 같음
	@OneToMany(mappedBy = "mainTravel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "mainTravel", orphanRemoval = false, cascade = CascadeType.PERSIST)
	private List<DetailTravel> detailTravels = new ArrayList<>();

	//TODO 배치쿼리 적용하려면 수정이 필요할 것 같음
	@OneToMany(mappedBy = "mainTravel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Scrap> scraps = new ArrayList<>();

	//TODO 배치쿼리 적용하려면 수정이 필요할 것 같음
	@OneToMany(mappedBy = "mainTravel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Heart> hearts = new ArrayList<>();

	@Builder
	public MainTravel(String title, LocalDate startDate, LocalDate endDate, boolean isVisible, boolean isCompleted) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isVisible = isVisible;
		this.isCompleted = isCompleted;
		this.likeNum = 0;
	}

	public void update(String title, LocalDate startDate, LocalDate endDate, boolean isVisible, boolean isCompleted, List<DetailTravel> detailTravelList) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isVisible = isVisible;
		this.isCompleted = isCompleted;

		setDetailTravels(detailTravelList);
	}





	public void setWriter(Member writer) {
		this.writer = writer;
	}



	public void setDetailTravels(List<DetailTravel> detailTravels) {
		this.detailTravels.clear();
		detailTravels.forEach(dt -> dt.setMainTravel(this));
		this.detailTravels.addAll(detailTravels);
	}

	public String getImagePath() {
		return (this.image == null)
			? null
			: this.image.getPath();
	}

	public void setImage(String mainImagePath) {
		Image image = Image.from(mainImagePath);
		this.image = image;
	}
}

package com.footprint.comment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.footprint.common.BaseTimeEntity;
import com.footprint.maintravel.domain.MainTravel;
import com.footprint.member.domain.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Column(name = "content", nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_id")
	private MainTravel mainTravel;

	//TODO 일급 컬렉션으로 수
	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	List<Comment> children = new ArrayList<>();

	@Builder
	public Comment(String content, Member writer, MainTravel mainTravel, Comment parent) {
		this.content = content;
		this.writer = writer;
		setMainTravel(mainTravel);
		if (parent != null) {
			setParent(parent);
		}
	}

	private void setMainTravel(MainTravel mainTravel) {
		this.mainTravel = mainTravel;
		mainTravel.getComments().add(this);
	}

	private void setParent(Comment parent) {
		this.parent = parent;
		parent.getChildren().add(this);
	}

	public void updateComment(String content) {
		this.content = content;
	}
}

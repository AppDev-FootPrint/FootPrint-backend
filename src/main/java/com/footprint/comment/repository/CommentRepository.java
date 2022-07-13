package com.footprint.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@EntityGraph(attributePaths = {"writer", "children"})
	List<Comment> findAllByMainTravelIdAndParentId(Long mainTravelId, Long parentId);

	@EntityGraph(attributePaths = {"writer", "parent"})
	List<Comment> findAllWithWriterAndParentByMainTravelId(Long mainTravelId);
}

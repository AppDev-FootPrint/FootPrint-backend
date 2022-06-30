package com.footprint.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.image.domain.Image;

/**
 * Created by ShinD on 2022/06/30.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}

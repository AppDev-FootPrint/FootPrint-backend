package com.footprint.price.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.footprint.price.domain.Price;

/**
 * Created by ShinD on 2022/06/30.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {
}

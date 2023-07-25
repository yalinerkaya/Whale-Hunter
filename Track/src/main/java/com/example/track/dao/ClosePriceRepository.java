package com.example.track.dao;

import com.example.track.domain.ClosePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.example.track.dao
 * fileName       : ClosePriceRepository
 * author         : 정재윤
 * date           : 2023-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-23        정재윤       최초 생성
 */
public interface ClosePriceRepository extends JpaRepository<ClosePrice, Long> {
    List<ClosePrice> findAllByOrderByClosedAtDesc();
}

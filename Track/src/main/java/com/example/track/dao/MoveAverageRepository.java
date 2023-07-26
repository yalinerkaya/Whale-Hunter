package com.example.track.dao;

import com.example.track.domain.MoveAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.example.track.dao
 * fileName       : MoveAverageRepository
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
@Repository
public interface MoveAverageRepository extends JpaRepository<MoveAverage, Long> {
    MoveAverage findTopByOrderByCreatedAtDesc();
}

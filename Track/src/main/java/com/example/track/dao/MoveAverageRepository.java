package com.example.track.dao;

import com.example.track.domain.MoveAverage;
import com.example.track.dto.MoveAverageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

/**
 * packageName    : com.example.track.dao
 * fileName       : MoveAverageRepository
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Repository
public interface MoveAverageRepository extends JpaRepository<MoveAverage, Long> {
    MoveAverageResponse findTopByOrderByCreatedAtDesc();
}

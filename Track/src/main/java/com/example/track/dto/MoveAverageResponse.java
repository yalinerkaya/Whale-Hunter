package com.example.track.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * packageName    : com.example.track.dto
 * fileName       : MoveAverageResponse
 * author         : 정재윤
 * date           : 2023-08-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-21        정재윤       최초 생성
 */
@Getter
@AllArgsConstructor
public class MoveAverageResponse {
    private BigDecimal moveAverage;
}

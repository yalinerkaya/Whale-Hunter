package com.example.display.application;

import com.example.display.dto.DisplayDataResponse;

import java.util.Optional;

/**
 * packageName    : com.example.display.application
 * fileName       : DisplayService
 * author         : 정재윤
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        정재윤       최초 생성
 */
public interface DisplayService {
    Optional<DisplayDataResponse> buildDataset();
}

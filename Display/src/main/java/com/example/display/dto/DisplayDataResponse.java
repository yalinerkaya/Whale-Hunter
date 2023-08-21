package com.example.display.dto;

import com.example.display.domain.Data;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : com.example.display.dto
 * fileName       : DisplayDataResponse
 * author         : Jay
 * date           : 2023-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        Jay       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class DisplayDataResponse {
    private List<Data> sell;
    private List<Data> buy;
}

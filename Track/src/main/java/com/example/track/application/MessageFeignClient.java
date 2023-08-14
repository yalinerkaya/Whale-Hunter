package com.example.track.application;

import com.example.global.common.CommonResponse;
import com.example.track.dto.CoinStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * packageName    : com.example.track.application
 * fileName       : MessageFeignClient
 * author         : 정재윤
 * date           : 2023-08-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-08-02        정재윤       최초 생성
 */

@FeignClient(value ="message-service")
public interface MessageFeignClient {
    @PostMapping("/up")
    public CommonResponse<Void> priceBreakout();

    @PostMapping("/down")
    public CommonResponse<Void> priceBreakdown();

    @GetMapping("/status")
    public CommonResponse<CoinStatusResponse> checkBTCStatus();
}

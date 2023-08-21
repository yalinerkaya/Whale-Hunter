package com.example.message.api;

import com.example.global.common.CommonResponse;
import com.example.message.application.MessageService;
import com.example.message.dto.CoinStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageApi {
    private final MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/up")
    public CommonResponse<Void> priceBreakout() throws Exception {
        messageService.priceBreakout();
        return new CommonResponse<>();
    }

    @PostMapping("/down")
    public CommonResponse<Void> priceBreakdown() throws Exception {
        messageService.priceBreakdown();
        return new CommonResponse<>();
    }

    @PostMapping("/btc")
    public CommonResponse<Void> likeBTC() throws Exception {
        messageService.likeBTC();
        return new CommonResponse<>();
    }

    @GetMapping("/status")
    public CommonResponse<CoinStatusResponse> checkBTCStatus() throws Exception {
        CoinStatusResponse coinStatusResponse = messageService.selectBTCStatus();
        return new CommonResponse<>(coinStatusResponse);
    }
}

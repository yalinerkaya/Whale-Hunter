package com.example.message.api;

import com.example.global.common.CommonResponse;
import com.example.message.application.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageApi {
    private final MessageService messageService;

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
}

package com.example.message.api;

import com.example.global.common.CommonResponse;
import com.example.message.application.MessageService;
import com.example.message.dto.MessageEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageApi {
    private final MessageService messageService;

    @PostMapping("/up")
    public CommonResponse<Void> priceBreakout(@Valid @RequestBody MessageEventRequest messageEventRequest) throws Exception {
        messageService.priceBreakout(messageEventRequest);
        return new CommonResponse<>();
    }

    @PostMapping("/down")
    public CommonResponse<Void> priceBreakdown(@Valid @RequestBody MessageEventRequest messageEventRequest) throws Exception {
        messageService.priceBreakdown(messageEventRequest);
        return new CommonResponse<>();
    }
}

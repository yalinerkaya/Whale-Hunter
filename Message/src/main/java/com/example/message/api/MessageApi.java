package com.example.message.api;

import com.example.global.common.CommonResponse;
import com.example.message.application.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageApi {
    private MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public CommonResponse<Void> sendMessage() throws Exception {
        messageService.sendMessage();
        return new CommonResponse<>();
    }

    @PostMapping("/btc")
    public CommonResponse<Void> likeBTC() throws Exception {
        messageService.likeBTC();
        return new CommonResponse<>();
    }
}

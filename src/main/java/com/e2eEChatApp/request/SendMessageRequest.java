package com.e2eEChatApp.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMessageRequest {
    private Integer userId;
    private Integer chatId;
    private String content;
}

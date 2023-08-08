package com.e2eEChatApp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequest {
    private List<Integer> userIds;
    private String chatName;
    private String chatImage;
}

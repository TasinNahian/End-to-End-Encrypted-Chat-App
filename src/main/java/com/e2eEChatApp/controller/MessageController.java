package com.e2eEChatApp.controller;

import com.e2eEChatApp.exception.ChatException;
import com.e2eEChatApp.exception.MessageException;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.Message;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.request.SendMessageRequest;
import com.e2eEChatApp.response.ApiResponse;
import com.e2eEChatApp.service.MessageService;
import com.e2eEChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);

        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        List<Message> message = messageService.getChatMessages(chatId, user);

        return new ResponseEntity<List<Message>>(message, HttpStatus.OK);
    }
    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessagesHandler(@PathVariable("messageId") Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, MessageException {
        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId, user);
        ApiResponse res = new ApiResponse("Message deleted successfully", false);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

}

package com.e2eEChatApp.controller;

import com.e2eEChatApp.exception.ChatException;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.Chat;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.request.GroupChatRequest;
import com.e2eEChatApp.request.SingleChatRequest;
import com.e2eEChatApp.response.ApiResponse;
import com.e2eEChatApp.service.ChatService;
import com.e2eEChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    @PostMapping(value = "/single", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader(name = "Authorization", required = true) String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @GetMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest req, @RequestHeader(name = "Authorization", required = true) String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        Chat groupChat = chatService.createGroup(req, reqUser);
        return new ResponseEntity<Chat>(groupChat, HttpStatus.OK);
    }

    @GetMapping(value = "/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader(name = "Authorization", required = true) String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader(name = "Authorization", required = true) String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }
    @PostMapping(value = "/{chatId}/add/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable("chatId") Integer chatId, @PathVariable("userId") Integer userId, @RequestHeader(name = "Authorization", required = true) String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @PutMapping(value = "/{chatId}/add/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable("chatId") Integer chatId, @PathVariable("userId") Integer userId, @RequestHeader(name = "Authorization", required = true) String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.removeFromGroup(userId, chatId, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }
    @PutMapping(value = "/delete/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable("chatId") Integer chatId, @RequestHeader(name = "Authorization", required = true) String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);
        chatService.deleteChat(chatId, reqUser.getId());
        ApiResponse res = new ApiResponse("Chat is deleted successfully", true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }
}

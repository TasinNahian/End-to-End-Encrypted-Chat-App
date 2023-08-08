package com.e2eEChatApp.service;

import com.e2eEChatApp.exception.ChatException;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.Chat;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, Integer userId2) throws UserException;
    Chat findChatById(Integer chatId) throws ChatException;
    List<Chat> findAllChatByUserId(Integer userId) throws UserException;
    Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;
    Chat addUserToGroup (Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;
    Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException;
    Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;
    void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;


}

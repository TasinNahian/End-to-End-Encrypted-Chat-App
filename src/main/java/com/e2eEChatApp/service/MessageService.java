package com.e2eEChatApp.service;

import com.e2eEChatApp.exception.ChatException;
import com.e2eEChatApp.exception.MessageException;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.Message;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
    List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException;
    Message findMessageById(Integer messageId) throws MessageException;
    void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}

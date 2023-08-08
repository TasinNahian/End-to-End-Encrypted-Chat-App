package com.e2eEChatApp.service.impl;

import com.e2eEChatApp.exception.ChatException;
import com.e2eEChatApp.exception.MessageException;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.Chat;
import com.e2eEChatApp.model.Message;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.repository.MessageRepository;
import com.e2eEChatApp.request.SendMessageRequest;
import com.e2eEChatApp.service.ChatService;
import com.e2eEChatApp.service.MessageService;
import com.e2eEChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImplementation implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(req.getUserId());
        Chat chat =chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());

        return message;
    }

    @Override
    public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        if(!chat.getUsers().contains(chatId)){
            throw new UserException("You are not related to this chat "+chat.getId());
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new MessageException("Message not found with id: "+messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
        Message message = findMessageById(messageId);
        if(message.getUser().getId().equals(reqUser.getId())){
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can't delete another another user's message");
    }
}

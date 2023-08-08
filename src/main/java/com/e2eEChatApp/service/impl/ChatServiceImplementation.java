package com.e2eEChatApp.service.impl;

import com.e2eEChatApp.exception.ChatException;
import com.e2eEChatApp.exception.UserException;
import com.e2eEChatApp.model.Chat;
import com.e2eEChatApp.model.User;
import com.e2eEChatApp.repository.ChatRepository;
import com.e2eEChatApp.request.GroupChatRequest;
import com.e2eEChatApp.service.ChatService;
import com.e2eEChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImplementation implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);
        Chat isChatExist  =chatRepository.findSingleChatByUsers(user, reqUser);
        if(isChatExist != null){
            return isChatExist;
        }
        Chat chat  = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found with id "+chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(userId);
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChatImage(req.getChatImage());
        group.setChatName(req.getChatName());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);
        for(Integer userId : req.getUserIds()){
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }
        return group;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(opt.isPresent()){
            Chat chat = opt.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().add(user);
                return chat;
            }else{
                throw new UserException("You don't have access as admin");
            }
        }
        throw new ChatException("Chat not found with id: "+chatId);

    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if(opt.isPresent()){
            Chat chat = opt.get();
            if(chat.getUsers().contains(reqUser)){
                chat.setChatName(groupName);
                return chatRepository.save(chat);
            }
            throw new UserException("You are not a member of this group");
        }
        throw new ChatException("Chat not found with id:" +chatId);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(opt.isPresent()){
            Chat chat = opt.get();
            if(chat.getAdmins().contains(reqUser) || (chat.getUsers().contains(reqUser) && user.getId().equals((reqUser.getId())))){
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            }
            // remove  || (chat.getUsers().contains(reqUser) && user.getId().equals((reqUser.getId()) and uncomment below code
//            else if(chat.getUsers().contains(reqUser)){
//                if(user.getId().equals((reqUser.getId()))){
//                    chat.getUsers().remove(user);
//                    return chatRepository.save(chat);
//                }
            }
            else{
                throw new UserException("You don't have access as admin or you cannot remove another user");
            }
        throw new ChatException("Chat not found with id: "+chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if(opt.isPresent()){
            Chat chat = opt.get();
            chatRepository.deleteById(chat.getId());
        }
    }
}

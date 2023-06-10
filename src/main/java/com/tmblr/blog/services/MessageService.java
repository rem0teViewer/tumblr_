package com.tmblr.blog.services;

import com.tmblr.blog.models.Message;
import com.tmblr.blog.models.User;
import com.tmblr.blog.models.dto.MessageDto;
import com.tmblr.blog.repositories.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<MessageDto> messageList(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(filter, pageable, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepository.findByUser(pageable, author, currentUser);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }
}

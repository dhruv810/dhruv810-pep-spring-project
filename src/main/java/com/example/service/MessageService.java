package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.entity.Message;
import com.example.exception.CustomException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createNewPost(Message message) throws CustomException {
        if (message == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            throw new CustomException("Invalid message");
        }
        return this.messageRepository.save(message);
    }

    public List<Message> getAllMessage() {
        return this.messageRepository.findAll();
    }

    public Message getMessageById(Integer message_id) throws CustomException {
        Message message = this.messageRepository.findById(message_id).get();
        if (message != null)
            return message;
        
        throw new CustomException("Message not found");
    }

    public int deleteMessageById(Integer message_id) throws CustomException {
        this.getMessageById(message_id);
        this.messageRepository.deleteById(message_id);
        return 1;
    }
    
    public int updateMessage(Integer message_id, Message message) throws CustomException {
        if (message == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            throw new CustomException("Invalid message");
        }
        Message message2 = this.getMessageById(message_id);
        message2.setMessage_text(message.getMessage_text());
        this.messageRepository.save(message2);
        return 1;
        
    }

    public List<Message> getAllMessagesByAccountId(Integer account_id) {
        return this.messageRepository.findAllByAccountId(account_id);
    }
    
}

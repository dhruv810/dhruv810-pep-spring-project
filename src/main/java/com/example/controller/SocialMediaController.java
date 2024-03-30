package com.example.controller;


import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.h2.util.json.JSONObject;
import org.h2.util.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.CustomException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account2 = this.accountService.createAccount(account);
            return ResponseEntity.status(200).body(mapper.writeValueAsString(account2));
        }
        catch (CustomException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Account account) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account account2 = this.accountService.login(account);
            return ResponseEntity.status(200).body(mapper.writeValueAsString(account2));
        }
        catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<String> createNewPost(@RequestBody Message message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message2 = this.messageService.createNewPost(message);
            return ResponseEntity.status(200).body(mapper.writeValueAsString(message2));
        }
        catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessage() {
        List<Message> msgs = this.messageService.getAllMessage();
        return ResponseEntity.status(200).body(msgs);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<String> getMessageById(@PathVariable Integer message_id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = this.messageService.getMessageById(message_id);
            return ResponseEntity.status(200).body(mapper.writeValueAsString(message));
        }
        catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer message_id) {
        try {
            int numRowsEffected = this.messageService.deleteMessageById(message_id);
            return ResponseEntity.status(200).body(String.valueOf(numRowsEffected));
        }
        catch (Exception e) {
            return ResponseEntity.status(200).body("");
        }   
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessage(@PathVariable Integer message_id, @RequestBody Message message) {
        try {
            int numRowsEffected = this.messageService.updateMessage(message_id, message);
            return ResponseEntity.status(200).body(String.valueOf(numRowsEffected));
        }
        catch (Exception e) {
            return ResponseEntity.status(400).body("");
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer account_id) {
        List<Message> msgs = this.messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.status(200).body(msgs);
    }


}

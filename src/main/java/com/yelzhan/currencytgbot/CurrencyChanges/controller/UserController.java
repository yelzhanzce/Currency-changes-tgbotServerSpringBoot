package com.yelzhan.currencytgbot.CurrencyChanges.controller;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.User;
import com.yelzhan.currencytgbot.CurrencyChanges.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping(value = "/addUser", consumes = "application/json", produces = "application/json") // http://localhost:8080/api/user/addUser
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping(value = "/")
    public ResponseEntity<User> getUserByChatId(@RequestBody User user){
        return ResponseEntity.ok(userService.findByChatId(user.getChatId()));
    }
}

package com.yelzhan.currencytgbot.CurrencyChanges.service;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.User;
import com.yelzhan.currencytgbot.CurrencyChanges.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findByChatId(String chatId){
        return userRepository.findByChatId(chatId);
    }
}

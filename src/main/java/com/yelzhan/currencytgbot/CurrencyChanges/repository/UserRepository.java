package com.yelzhan.currencytgbot.CurrencyChanges.repository;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(String chatId);
}

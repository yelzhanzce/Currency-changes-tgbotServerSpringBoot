package com.yelzhan.currencytgbot.CurrencyChanges.repository;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findByName(String name);
    List<Currency> findByNameOrderByDateDesc(String name);
    List<Currency> findCurrenciesByOrderByDateDesc();
}

package com.yelzhan.currencytgbot.CurrencyChanges.service;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.Currency;
import com.yelzhan.currencytgbot.CurrencyChanges.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }

    public List<Currency> findByName(String name){
        return currencyRepository.findByName(name);
    }

    public Currency save(Currency currency){
        return currencyRepository.save(currency);
    }
    public List<Currency> findByNameOrderByDateDesc(String name){
        return currencyRepository.findByNameOrderByDateDesc(name);
    }

    public List<Currency> findCurrenciesByOrderByDateDesc(){
        return currencyRepository.findCurrenciesByOrderByDateDesc();
    }
}

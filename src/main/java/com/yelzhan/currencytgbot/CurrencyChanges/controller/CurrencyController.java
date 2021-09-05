package com.yelzhan.currencytgbot.CurrencyChanges.controller;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.Currency;
import com.yelzhan.currencytgbot.CurrencyChanges.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/")
    public ResponseEntity<Currency> home(){
        return ResponseEntity.ok(new Currency(1L, "USD", 426.17, LocalDate.now(),0.24));
    }

    @GetMapping("/{currency}") // http://localhost:8080/api/currency/usd
    public ResponseEntity<List<Currency>> getByCurrency(@PathVariable("currency") String currency){
        return ResponseEntity.ok(currencyService.findByNameOrderByDateDesc(currency));
    }





}

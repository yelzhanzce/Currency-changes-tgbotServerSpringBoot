package com.yelzhan.currencytgbot.CurrencyChanges.service;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.Currency;
import com.yelzhan.currencytgbot.CurrencyChanges.entity.User;
import com.yelzhan.currencytgbot.CurrencyChanges.parser.ParserCurrency;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private UserService userService;


    public void notificationOn(List<User> users, Currency currency){
        String tgURL = "https://api.telegram.org/bot1935914387:AAFLiQScWBy6yda6r1OfBEoSWuNI9cOF8w0/sendMessage";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String notificationText;
        for (User user : users) {
            notificationText = "Hello " + user.getUsername() + "!" + "\nI want to notify you that today's exchange rate (" + currency.getName() + ") has been changed by more than 10%!" +
                    "\n\nCurrent exchange rate:\n1 " + currency.getName() + " = " + currency.getKzt_value() + " KZT rate for " + currency.getDate();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chat_id", user.getChatId());
            jsonObject.put("text", notificationText);
            HttpEntity<String> httpEntity = new HttpEntity<String>(jsonObject.toString(), httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(tgURL, httpEntity, String.class);
            String json = responseEntity.getBody();
            System.out.println(responseEntity);
            System.out.println(json);
        }

    }

    @Scheduled(fixedDelay = 1000*60*60)
    public void parser(){
        ParserCurrency parserCurrency = new ParserCurrency("USD", "EUR", "RUB");

        Currency currencyUSD = parserCurrency.getUSD();
        Currency currencyEUR = parserCurrency.getEUR();
        Currency currencyRUB = parserCurrency.getRUB();


        List<Currency> currencyList = currencyService.findCurrenciesByOrderByDateDesc();

        if(currencyList.size() == 0){
            ArrayList<Currency> currencies = parserCurrency.getAllCurrencyLastTenDays();
            for (int i = 0; i < currencies.size(); i++){
                currencyService.save(currencies.get(i));
            }
        }else {
            Currency currencyUSDInDB = currencyList.get(0);
            Currency currencyEURInDB = currencyList.get(1);
            Currency currencyRUBInDB = currencyList.get(2);

            boolean checkerUSD = checkForDates(currencyUSDInDB, currencyUSD);
            boolean checkerEUR = checkForDates(currencyEURInDB, currencyEUR);
            boolean checkerRUB = checkForDates(currencyRUBInDB, currencyRUB);

            if (!checkerUSD && !checkerEUR && !checkerRUB){
                currencyService.save(currencyUSD);
                currencyService.save(currencyEUR);
                currencyService.save(currencyRUB);
                double tenPercent = 0.0;
                List<User> users = null;
                if(currencyUSD.getChanges() >= tenPercent || currencyUSD.getChanges() <= -tenPercent || currencyEUR.getChanges() >= tenPercent || currencyEUR.getChanges() <= -tenPercent || currencyRUB.getChanges() >= tenPercent || currencyRUB.getChanges() <= -tenPercent){
                    users = userService.findAll();
                }
                if(currencyUSD.getChanges() >= tenPercent || currencyUSD.getChanges() <= -tenPercent){
                    notificationOn(users, currencyUSD);
                }if(currencyEUR.getChanges() >= tenPercent || currencyEUR.getChanges() <= -tenPercent){
                    notificationOn(users, currencyEUR);
                }
                if(currencyRUB.getChanges() >= tenPercent || currencyRUB.getChanges() <= -tenPercent){
                    notificationOn(users, currencyRUB);
                }
            }else{
                System.out.println("Already exists same date in the DB");
            }
        }

    }

    public static boolean checkForDates(Currency currencyInDB, Currency newCurrency){
        boolean check = false;
        String dateDb = currencyInDB.getDate().toString().split("\\s+")[0];
        String dateNew = newCurrency.getDate().toString().split("\\s+")[0];
        if (dateDb.equals(dateNew)){
            check = true;
        }
        return  check;
    }
}

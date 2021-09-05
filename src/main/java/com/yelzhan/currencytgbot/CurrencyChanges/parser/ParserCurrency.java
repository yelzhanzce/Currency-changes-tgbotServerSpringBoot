package com.yelzhan.currencytgbot.CurrencyChanges.parser;

import com.yelzhan.currencytgbot.CurrencyChanges.entity.Currency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ParserCurrency {

    private Document documentUSD;
    private Document documentEUR;
    private Document documentRUB;

    public ParserCurrency(){
    }

    public ParserCurrency(String usd, String eur, String rub){
        run(usd, eur, rub);
    }

    private void run(String usd, String eur, String rub) {
        try {
            documentUSD = Jsoup.connect("https://www.exchangerates.org.uk/" + usd + "-KZT-exchange-rate-history.html").get();
            documentEUR = Jsoup.connect("https://www.exchangerates.org.uk/" + eur + "-KZT-exchange-rate-history.html").get();
            documentRUB = Jsoup.connect("https://www.exchangerates.org.uk/" + rub + "-KZT-exchange-rate-history.html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getChanges(double preValue, double currentValue) {
        double res = (currentValue * 100 / preValue) - 100.0;
        return res;
    }

    public Currency getUSD() {

        Currency currency = new Currency();

        LocalDate date = null;
        double kzt_value;

        Elements elements = documentUSD.getElementsByTag("tr");
        Elements td = elements.get(2).getElementsByTag("td"); //current day
        Elements td3 = elements.get(3).getElementsByTag("td"); //yesterday

        String[] splitedPreviousDayKZTValue = (td3.get(1).text() + "").split("\\s+");
        double preValue = Double.parseDouble(splitedPreviousDayKZTValue[3]);
        String[] splitedKztValue = (td.get(1).text() + "").split("\\s+"); // ["1", "USD", "=", "425.44", "KZT"]
        String[] splitedDate = (td.get(2).text() + "").split("\\s+");     // ["USD", "KZT", "rate", "for", "02/09/2021"]
        date = convertStringToLocalDate(splitedDate[4]);
        kzt_value = Double.parseDouble(splitedKztValue[3]);
        double changes = getChanges(preValue, kzt_value);
        currency.setName("USD");
        currency.setDate(date);
        currency.setKzt_value(kzt_value);
        currency.setChanges(changes);

        return currency;
    }

    public Currency getEUR() {

        Currency currency = new Currency();

        LocalDate date = null;
        double kzt_value;

        Elements elements = documentEUR.getElementsByTag("tr");
        Elements td = elements.get(2).getElementsByTag("td"); //current day
        Elements td3 = elements.get(3).getElementsByTag("td"); //yesterday

        String[] splitedPreviousDayKZTValue = (td3.get(1).text() + "").split("\\s+");
        double preValue = Double.parseDouble(splitedPreviousDayKZTValue[3]);
        String[] splitedKztValue = (td.get(1).text() + "").split("\\s+"); // ["1", "USD", "=", "425.44", "KZT"]
        String[] splitedDate = (td.get(2).text() + "").split("\\s+");     // ["USD", "KZT", "rate", "for", "02/09/2021"]
        date = convertStringToLocalDate(splitedDate[4]);
        kzt_value = Double.parseDouble(splitedKztValue[3]);
        double changes = getChanges(preValue, kzt_value);
        currency.setName("EUR");
        currency.setDate(date);
        currency.setKzt_value(kzt_value);
        currency.setChanges(changes);

        return currency;
    }


    public Currency getRUB() {

        Currency currency = new Currency();

        LocalDate date = null;
        double kzt_value;

        Elements elements = documentRUB.getElementsByTag("tr");
        Elements td = elements.get(2).getElementsByTag("td"); //current day
        Elements td3 = elements.get(3).getElementsByTag("td"); //yesterday

        String[] splitedPreviousDayKZTValue = (td3.get(1).text() + "").split("\\s+");
        double preValue = Double.parseDouble(splitedPreviousDayKZTValue[3]);
        String[] splitedKztValue = (td.get(1).text() + "").split("\\s+"); // ["1", "USD", "=", "425.44", "KZT"]
        String[] splitedDate = (td.get(2).text() + "").split("\\s+");     // ["USD", "KZT", "rate", "for", "02/09/2021"]
        date = convertStringToLocalDate(splitedDate[4]);
        kzt_value = Double.parseDouble(splitedKztValue[3]);
        double changes = getChanges(preValue, kzt_value);
        currency.setName("RUB");
        currency.setDate(date);
        currency.setKzt_value(kzt_value);
        currency.setChanges(changes);

        return currency;
    }

    public ArrayList<Currency> getAllCurrencyLastTenDays(){
        ArrayList<Currency> currencies = new ArrayList<>();

        Elements elementsUSD = documentUSD.getElementsByTag("tr");
        Elements elementsEUR = documentEUR.getElementsByTag("tr");
        Elements elementsRUB = documentRUB.getElementsByTag("tr");


        for (int i = 2; i < 13; i++) {
            Currency currencyUSD = new Currency();
            Elements tdUSD = elementsUSD.get(i).getElementsByTag("td");
            Elements tdYesterdayUSD = elementsUSD.get(i+1).getElementsByTag("td");

            String[] splitedPreviousDayKZTValueUSD = (tdYesterdayUSD.get(1).text() + "").split("\\s+");
            String[] splitedKztValueUSD = (tdUSD.get(1).text() + "").split("\\s+"); // ["1", "USD", "=", "425.44", "KZT"]
            String[] splitedDateUSD = (tdUSD.get(2).text() + "").split("\\s+");     // ["USD", "KZT", "rate", "for", "02/09/2021"]
            LocalDate date = convertStringToLocalDate(splitedDateUSD[4]);
            System.out.println(date.toString());

            double preValue = Double.parseDouble(splitedPreviousDayKZTValueUSD[3]);
            double kzt_value = Double.parseDouble(splitedKztValueUSD[3]);
            double changes = getChanges(preValue, kzt_value);

            currencyUSD.setName("USD");
            currencyUSD.setDate(date);
            currencyUSD.setKzt_value(kzt_value);
            currencyUSD.setChanges(changes);
///////////////////////////////////////////////////////////////////////////////////
            Currency currencyEUR = new Currency();
            Elements tdEUR = elementsEUR.get(i).getElementsByTag("td");
            Elements tdYesterdayEUR = elementsEUR.get(i+1).getElementsByTag("td");

            String[] splitedPreviousDayKZTValueEUR = (tdYesterdayEUR.get(1).text() + "").split("\\s+");
            String[] splitedKztValueEUR = (tdEUR.get(1).text() + "").split("\\s+"); // ["1", "USD", "=", "425.44", "KZT"]
            String[] splitedDateEUR = (tdEUR.get(2).text() + "").split("\\s+");     // ["USD", "KZT", "rate", "for", "02/09/2021"]
            LocalDate dateEUR = convertStringToLocalDate(splitedDateEUR[4]);

            double preValueEUR = Double.parseDouble(splitedPreviousDayKZTValueEUR[3]);
            double kzt_valueEUR = Double.parseDouble(splitedKztValueEUR[3]);
            double changesEUR = getChanges(preValueEUR, kzt_valueEUR);

            currencyEUR.setName("EUR");
            currencyEUR.setDate(dateEUR);
            currencyEUR.setKzt_value(kzt_valueEUR);
            currencyEUR.setChanges(changesEUR);
//////////////////////////////////////////////////////////////////////////////////

            Currency currencyRUB = new Currency();
            Elements tdRUB = elementsRUB.get(i).getElementsByTag("td");
            Elements tdYesterdayRUB = elementsRUB.get(i+1).getElementsByTag("td");

            String[] splitedPreviousDayKZTValueRUB = (tdYesterdayRUB.get(1).text() + "").split("\\s+");
            String[] splitedKztValueRUB = (tdRUB.get(1).text() + "").split("\\s+"); // ["1", "USD", "=", "425.44", "KZT"]
            String[] splitedDateRUB = (tdRUB.get(2).text() + "").split("\\s+");     // ["USD", "KZT", "rate", "for", "02/09/2021"]
            LocalDate dateRUB = convertStringToLocalDate(splitedDateRUB[4]);

            double preValueRUB = Double.parseDouble(splitedPreviousDayKZTValueRUB[3]);
            double kzt_valueRUB = Double.parseDouble(splitedKztValueRUB[3]);
            double changesRUB = getChanges(preValueRUB, kzt_valueRUB);

            currencyRUB.setName("RUB");
            currencyRUB.setDate(dateRUB);
            currencyRUB.setKzt_value(kzt_valueRUB);
            currencyRUB.setChanges(changesRUB);


            currencies.add(currencyUSD);
            currencies.add(currencyEUR);
            currencies.add(currencyRUB);

        }
        return currencies;
    }




    public static LocalDate convertStringToLocalDate(String date){
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld = LocalDate.parse(date, dateformatter);

        return ld;
    }


}

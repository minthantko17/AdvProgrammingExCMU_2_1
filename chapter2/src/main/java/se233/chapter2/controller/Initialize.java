package se233.chapter2.controller;

import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

public class Initialize {
    public static List<Currency> initializeApp(){
        Currency baseC=new Currency("THB");
        Currency c=new Currency("USD");
        List<CurrencyEntity> cList=FetchData.fetchRange(baseC.getShortCode(), c.getShortCode(), 30);
        c.setHistorical(cList);
        c.setCurrent(cList.get(cList.size()-1));
        List<Currency> currencyList=new ArrayList<Currency>();
        currencyList.add(c);
        return currencyList;
    }
}

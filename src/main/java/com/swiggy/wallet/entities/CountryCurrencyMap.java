package com.swiggy.wallet.entities;

import java.util.HashMap;
import java.util.Map;

public class CountryCurrencyMap {
    private static final Map<Country, Currency> countryCurrencyMap = new HashMap<>();
    static {
        countryCurrencyMap.put(Country.USA, Currency.USD);
        countryCurrencyMap.put(Country.INDIA, Currency.INR);
        countryCurrencyMap.put(Country.UK, Currency.GBP);
        countryCurrencyMap.put(Country.JAPAN, Currency.JPY);
    }

    public static Currency getCurrencyByCountry(Country country) {
        return countryCurrencyMap.get(country);
    }
}

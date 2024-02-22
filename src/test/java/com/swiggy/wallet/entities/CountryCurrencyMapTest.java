package com.swiggy.wallet.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryCurrencyMapTest {
    @Test
    void TestIfCurrectCurrencyIsMappedToCorrectCountry() {
        assertEquals(Currency.INR, CountryCurrencyMap.getCurrencyByCountry(Country.INDIA));
        assertEquals(Currency.USD, CountryCurrencyMap.getCurrencyByCountry(Country.USA));
        assertEquals(Currency.JPY, CountryCurrencyMap.getCurrencyByCountry(Country.JAPAN));
        assertEquals(Currency.GBP, CountryCurrencyMap.getCurrencyByCountry(Country.UK));
    }
}
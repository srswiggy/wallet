package com.swiggy.wallet.entities;

import com.swiggy.wallet.exceptions.InsufficientBalanceException;
import com.swiggy.wallet.exceptions.InvalidAmountException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;

    private Money money;

    public Wallet() {
        this.money = new Money(0.0, Currency.INR);
    }

    public Wallet(Country country) {
        this.money = new Money(0.0, CountryCurrencyMap.getCurrencyByCountry(country));
    }

    public void deposit(Money money) throws InvalidAmountException {
        this.money.add(money);
    }

    public void withdraw(Money money) throws InsufficientBalanceException, InvalidAmountException {
        this.money.subtract(money);
    }
}

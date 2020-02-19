package com.mycompany.app.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

import java.time.LocalDate;

@SpaceClass
public class StockEarningDate {

    private String symbol;
    private LocalDate nextEarningsDate;

    @SpaceId
    @SpaceIndex(unique = true)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getNextEarningsDate() {
        return nextEarningsDate;
    }

    public void setNextEarningsDate(LocalDate nextEarningsDate) {
        this.nextEarningsDate = nextEarningsDate;
    }
}

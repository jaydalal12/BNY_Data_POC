package com.mycompany.app.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

@SpaceClass
public class StockPrice {
    private String symbol;
    private Double lastClose;
    private Double todaysOpen;

    @SpaceId
    @SpaceIndex(unique = true)
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getLastClose() {
        return lastClose;
    }

    public void setLastClose(Double lastClose) {
        this.lastClose = lastClose;
    }

    public Double getTodaysOpen() {
        return todaysOpen;
    }

    public void setTodaysOpen(Double todaysOpen) {
        this.todaysOpen = todaysOpen;
    }
}

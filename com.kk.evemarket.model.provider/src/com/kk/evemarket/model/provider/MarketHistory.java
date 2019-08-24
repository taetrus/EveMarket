package com.kk.evemarket.model.provider;

import java.time.LocalDate;

public class MarketHistory {

    private LocalDate date;
    private Integer order_count;
    private Integer volume;
    private Float highest;
    private Float average;
    private Float lowest;

    public MarketHistory() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getOrder_count() {
        return order_count;
    }

    public void setOrder_count(Integer order_count) {
        this.order_count = order_count;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Float getHighest() {
        return highest;
    }

    public void setHighest(Float highest) {
        this.highest = highest;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    public Float getLowest() {
        return lowest;
    }

    public void setLowest(Float lowest) {
        this.lowest = lowest;
    }
}

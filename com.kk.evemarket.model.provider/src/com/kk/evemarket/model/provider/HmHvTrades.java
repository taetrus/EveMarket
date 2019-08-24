package com.kk.evemarket.model.provider;

public class HmHvTrades {
    private String name;
    private Double volume;
    private Double margin;
    private Double profit;
    private Double buyPrice;
    private Double sellPrice;

    public HmHvTrades(String name, Double volume, Double margin, Double profit, Double buyPrice, Double sellPrice) {
        this.name = name;
        this.volume = volume;
        this.margin = margin;
        this.setProfit(profit);
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

}

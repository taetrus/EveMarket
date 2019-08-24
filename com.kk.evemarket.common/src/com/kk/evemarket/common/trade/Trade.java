package com.kk.evemarket.common.trade;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trade {
	private SimpleStringProperty name;
	private SimpleDoubleProperty volume;
	private SimpleDoubleProperty margin;
	private SimpleDoubleProperty profit;
	private SimpleDoubleProperty buyPrice;
	private SimpleDoubleProperty sellPrice;

	public Trade(String name, Double volume, Double margin, Double profit, Double buyPrice, Double sellPrice) {
		this.name = new SimpleStringProperty(name);
		this.volume = new SimpleDoubleProperty(volume);
		this.margin = new SimpleDoubleProperty(margin);
		this.profit = new SimpleDoubleProperty(profit);
		this.buyPrice = new SimpleDoubleProperty(buyPrice);
		this.sellPrice = new SimpleDoubleProperty(sellPrice);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Double getVolume() {
		return volume.get();
	}

	public void setVolume(Double volume) {
		this.volume.set(volume);
	}

	public Double getMargin() {
		return margin.get();
	}

	public void setMargin(Double margin) {
		this.margin.set(margin);
	}

	public Double getProfit() {
		return profit.get();
	}

	public void setProfit(Double profit) {
		this.profit.set(profit);
	}

	public Double getBuyPrice() {
		return buyPrice.get();
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice.set(buyPrice);
	}

	public Double getSellPrice() {
		return sellPrice.get();
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice.set(sellPrice);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Trade customer = (Trade) o;

		return getName() != null ? getName().equals(customer.getName()) : customer.getName() == null;
	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}

}

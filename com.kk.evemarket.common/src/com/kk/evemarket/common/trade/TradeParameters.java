package com.kk.evemarket.common.trade;

public class TradeParameters {
	public TradeParameters(double volume, double margin, double tax, int region) {
		minVolume = volume;
		minMargin = margin;
		feeTax = tax;
		tradeRegion = region;
	}

	private double minVolume;
	private double minMargin;
	private int tradeRegion;
	private double feeTax;

	// private String region;
	public double getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(double minVolume) {
		this.minVolume = minVolume;
	}

	public double getMinMargin() {
		return minMargin;
	}

	public void setMinMargin(double minMargin) {
		this.minMargin = minMargin;
	}

	public Integer getTradeRegion() {
		return tradeRegion;
	}

	public void setTradeRegion(int tradeRegion) {
		this.tradeRegion = tradeRegion;
	}

	public double getFeeTax() {
		return feeTax;
	}

	public void setFeeTax(double feeTax) {
		this.feeTax = feeTax;
	}
}

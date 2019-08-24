package com.kk.evemarket.common.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by kerem on 30.05.2017.
 */
public class Settings {

	private Settings() {
		properties = new Properties();
		File file = new File("properties.properties");

		if (file.exists()) {
			try (InputStream stream = new FileInputStream(file)) {
				properties.load(stream);

				setBuyFeeAndTax(Double.valueOf(properties.getProperty("BuyFeeandTax")));
				setMinMargin(Double.valueOf(properties.getProperty("MinimumMargin")));
				setMinVolume(Integer.valueOf(properties.getProperty("MinimumVolume")));
				setRegionId(Integer.valueOf(properties.getProperty("Region")));
				setMinValue(Double.valueOf(properties.getProperty("MinimumValue")));

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			new File("settings").mkdir();
			try (OutputStream stream = new FileOutputStream("properties.properties")) {
				properties.setProperty("MinimumVolume", "150");
				properties.setProperty("MinimumMargin", "7");
				properties.setProperty("BuyFeeandTax", "3.3");
				properties.setProperty("Region", "10000002");
				properties.setProperty("MinimumValue", "1000000");

				setBuyFeeAndTax(Double.valueOf(properties.getProperty("BuyFeeandTax")));
				setMinMargin(Double.valueOf(properties.getProperty("MinimumMargin")));
				setMinVolume(Integer.valueOf(properties.getProperty("MinimumVolume")));
				setRegionId(Integer.valueOf(properties.getProperty("Region")));
				setMinValue(Double.valueOf(properties.getProperty("MinimumValue")));

				properties.store(stream, null);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Settings settings;
	private Properties properties;
	private double minimumValue;
	private int minVolume;
	private double minMargin;
	private int regionId;
	private double buyFeeAndTax;

	public static Settings get() {
		if (settings == null) {
			settings = new Settings();
		}
		return settings;
	}

	public void save() {
		try (OutputStream stream = new FileOutputStream("properties.properties")) {
			properties.setProperty("MinimumVolume", String.valueOf(getMinVolume()));
			properties.setProperty("MinimumMargin", String.valueOf(getMinMargin()));
			properties.setProperty("BuyFeeandTax", String.valueOf(getBuyFeeAndTax()));
			properties.setProperty("Region", String.valueOf(getRegionId()));
			properties.setProperty("MinimumValue", String.valueOf(getMinValue()));

			properties.store(stream, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}

	public double getMinMargin() {
		return minMargin;
	}

	public void setMinMargin(double minMargin) {
		this.minMargin = minMargin;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public double getBuyFeeAndTax() {
		return buyFeeAndTax;
	}

	public void setBuyFeeAndTax(double buyFeeAndTax) {
		this.buyFeeAndTax = buyFeeAndTax;
	}

	public double getMinValue() {
		return minimumValue;
	}

	private void setMinValue(double value) {
		minimumValue = value;
	}
}
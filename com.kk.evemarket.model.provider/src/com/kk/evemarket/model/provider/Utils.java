package com.kk.evemarket.model.provider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kk.evemarket.common.settings.Settings;

import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.MarketApi;
import net.troja.eve.esi.model.MarketPricesResponse;

//import net.troja.eve.esi.ApiException;
//import net.troja.eve.esi.api.MarketApi;
//import net.troja.eve.esi.model.MarketPricesResponse;

public class Utils {

	public static List<Integer> marketableTypeIds;
	private static Map<Integer, String> typeIdNameMap;
	private static ConcurrentHashMap<Integer, List<MarketHistory>> typeIDMarketHistoryMap;
	private static Map<Integer, InvTypes> typeIdInvItemMap;

	private static Utils utils;

	private Utils() {

		try {
			initializeData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Utils get() {
		if (utils == null) {
			utils = new Utils();
		}
		return utils;
	}

	public void initializeData() throws IOException {

		// downloadFile("https://www.fuzzwork.co.uk/dump/latest/invTypes.csv.bz2");

		URL url = getClass().getResource("invTypes.csv");
		InputStream inputStream = url.openConnection().getInputStream();

		List<InvTypes> itemsList = readObjectsFromCsv(inputStream, InvTypes.class);

		typeIdInvItemMap = itemsList.stream().filter(i -> i.getMarketGroupID() != null)
				.collect(Collectors.toMap(InvTypes::getTypeID, Function.identity()));

		typeIdNameMap = itemsList.stream().filter(i -> i.getMarketGroupID() != null)
				.collect(Collectors.toMap(InvTypes::getTypeID, InvTypes::getTypeName));

		MarketApi marketApi = new MarketApi();
		marketApi.getApiClient().setBasePath("https://esi.tech.ccp.is");

		try {
			List<MarketPricesResponse> marketsPrices = marketApi.getMarketsPrices("tranquility", "");
			marketableTypeIds = marketsPrices.stream()
					.filter(t -> (t.getAveragePrice() != null)
							&& (t.getAveragePrice() > ((float) Settings.get().getMinValue())))// 2000000))
					.map(m -> m.getTypeId()).collect(Collectors.toList());

			// deneme();

		} catch (ApiException e) {
			e.printStackTrace();
		}

	}

	public static <T> List<T> readObjectsFromCsv(File file, Class<?> typeRef) throws IOException {

		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(typeRef).withNullValue("None").withHeader();
		MappingIterator<T> iter = mapper.readerFor(typeRef).with(schema).readValues(file);
		List<T> allItems = iter.readAll();

		return allItems;
	}

	public static <T> List<T> readObjectsFromCsv(InputStream stream, Class<?> typeRef) throws IOException {

		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(typeRef).withNullValue("None").withHeader();
		MappingIterator<T> iter = mapper.readerFor(typeRef).with(schema).readValues(stream);
		List<T> allItems = iter.readAll();

		return allItems;
	}

	public static String getItemNameFromNo(Integer itemNo) {
		String itemName = null;

		if (itemNo != null) {
			itemName = typeIdNameMap.get(itemNo);
		}

		return itemName;
	}

	public static Integer getItemNoFromName(String itemName) {
		Integer itemNo = null;

		if (itemName != null) {
			try {
				itemNo = typeIdNameMap.entrySet().stream().filter(e -> e.getValue().equals(itemName))
						.map(e -> e.getKey()).collect(Collectors.toList()).get(0);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return itemNo;
	}

}

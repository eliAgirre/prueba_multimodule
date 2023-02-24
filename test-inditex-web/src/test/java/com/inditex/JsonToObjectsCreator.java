package com.inditex;

import com.inditex.model.request.PriceRqTest;

import java.io.IOException;
import java.util.List;

public class JsonToObjectsCreator extends BaseJsonToObjectsCreator {

	public PriceRqTest test1PriceRqAt10Date14() throws IOException {
		return getObjectFromFile("/json/test1-priceRq-at10-date14.json", PriceRqTest.class);
	}
	public List<com.inditex.models.Prices> test1PriceRsAt10Date14() throws IOException {
		return getObjectListFromFile("/json/test1-priceRs-at10-date14.json", com.inditex.models.Prices.class);
	}
	public PriceRqTest test2PriceRqAt16Date14() throws IOException {
		return getObjectFromFile("/json/test2-priceRq-at16-date14.json", PriceRqTest.class);
	}
	public List<com.inditex.models.Prices> test2PriceRsAt16Date14() throws IOException {
		return getObjectListFromFile("/json/test2-priceRs-at16-date14.json", com.inditex.models.Prices.class);
	}
	public PriceRqTest test3PriceRqAt21Date14() throws IOException {
		return getObjectFromFile("/json/test3-priceRq-at21-date14.json", PriceRqTest.class);
	}
	public List<com.inditex.models.Prices> test3PriceRsAt21Date14() throws IOException {
		return getObjectListFromFile("/json/test3-priceRs-at21-date14.json", com.inditex.models.Prices.class);
	}
	public PriceRqTest test4PriceRqAt10Date15() throws IOException {
		return getObjectFromFile("/json/test4-priceRq-at10-date15.json", PriceRqTest.class);
	}
	public List<com.inditex.models.Prices> test4PriceRsAt10Date15() throws IOException {
		return getObjectListFromFile("/json/test4-priceRs-at10-date15.json", com.inditex.models.Prices.class);
	}
	public PriceRqTest test5PriceRqAt21Date16() throws IOException {
		return getObjectFromFile("/json/test5-priceRq-at21-date16.json", PriceRqTest.class);
	}

	public List<com.inditex.models.Prices> test5PriceRsAt21Date16() throws IOException {
		return getObjectListFromFile("/json/test5-priceRs-at21-date16.json", com.inditex.models.Prices.class);
	}
}

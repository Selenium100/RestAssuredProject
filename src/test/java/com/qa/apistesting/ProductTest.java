package com.qa.apistesting;

import org.apache.juneau.html.HtmlSerializer;
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.apache.juneau.xml.XmlSerializer;

import com.qa.pojo.Product;

public class ProductTest {

	public static void main(String[] args) throws SerializeException, ParseException {

		// pojo to json:
		JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;

		String[] sellerNames = { "Nitya", "Abhay", "Chiku", "Viswa" };
		Product product = new Product("MacbookPro", 1000, "White", sellerNames);

		String json = jsonSerializer.serialize(product);
		System.out.println(json);

		// pojo to xml:
		XmlSerializer xmlSerializer = XmlSerializer.DEFAULT_NS_SQ_READABLE;
		String xml = xmlSerializer.serialize(product);
		System.out.println(xml);

		// pojo to html:
		HtmlSerializer htmlSerializer = HtmlSerializer.DEFAULT_SQ_READABLE;
		String html = htmlSerializer.serialize(product);
		System.out.println(html);

		// desrialization:
		// json to pojo

		JsonParser jsonParser = JsonParser.DEFAULT;
		String jsonObj = "{\r\n" + "	\"colour\": \"White\",\r\n" + "	\"name\": \"MacbookPro\",\r\n"
				+ "	\"price\": 1000,\r\n" + "	\"sellerNames\": [\r\n" + "		\"Nitya\",\r\n" + "		\"Abhay\",\r\n"
				+ "		\"Chiku\",\r\n" + "		\"Viswa\"\r\n" + "	]\r\n" + "}";

		Product pro = jsonParser.parse(jsonObj, Product.class);
		for(String each: pro.getSellerNames()) {
			System.out.println(each);
		}

	}

}

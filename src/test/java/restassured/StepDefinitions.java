package restassured;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import io.cucumber.java.en.Given;

public class StepDefinitions {

	private Response res = null; // Response
	private JsonPath jp = null; // JsonPath
	private JsonPath viewjp = null; // JsonPath
	private ArrayList customerList = null;

	HelperTestMethods htm = new HelperTestMethods();

	@Given("I listed out the {string} ids from api")
	public void i_listed_out_customerIDS(String customers) {

		Utils.setBaseURI();
		Utils.setBasePath("");
		Utils.setContentType(ContentType.JSON); // Setup Content Type
		Utils.createSearchQueryPath(customers);
		res = Utils.getResponse(); // Get response
		jp = Utils.getJsonPath(res); // Set JsonPath

		customerList = htm.getListOfIds(jp);

	}

	@Given("I GET {string} data and validate the status code {int}")
	public void getCustomerViewandValidate(String customerView, int statusCode) {

		for (int counter = 0; counter < customerList.size(); counter++) {

			Utils.setBasePath(customerList.get(counter) + "/");
			Utils.setContentType(ContentType.JSON); // Setup Content Type
			Utils.createSearchQueryPath(customerView);
			res = Utils.getResponse(); // Get response
			viewjp = Utils.getJsonPath(res);
			res = Utils.getResponse();
			htm.checkStatusIs200(res, statusCode);
			viewjp = Utils.getJsonPath(res);

			Map<String, String> customerIDS = htm.getRelatedCustomerIdList(viewjp);

			for (Map.Entry<String, String> entry : customerIDS.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("CustomerID")) {
					int convertedCustomerID = Integer.parseInt(entry.getValue());
					assertEquals(convertedCustomerID, customerList.get(counter));
				}
			}

		}

	}

}

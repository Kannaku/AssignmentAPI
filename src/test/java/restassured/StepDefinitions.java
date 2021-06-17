package restassured;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import io.cucumber.java.en.Given;
import junit.framework.Assert;

public class StepDefinitions {

	private Response res = null; // Response
	private JsonPath jp = null; // JsonPath
	private JsonPath viewjp = null; // JsonPath
	private ArrayList customerIDList = null;
	private ArrayList customerEmailList = null;
	private ArrayList customerfnList = null;
	private ArrayList customerlnList = null;

	private List<Map<String, String>> listofMapData = null;

	HelperTestMethods htm = new HelperTestMethods();

	@Given("I listed out the {string} ids from api")
	public void i_listed_out_customerIDS(String customers) {

		Utils.setBaseURI();
		Utils.setBasePath("");
		Utils.setContentType(ContentType.JSON); // Setup Content Type
		Utils.createSearchQueryPath(customers);
		res = Utils.getResponse(); // Get response
		jp = Utils.getJsonPath(res); // Set JsonPath

		customerIDList = htm.getListOfIds(jp, "id");
		customerEmailList = htm.getListOfIds(jp, "email");
		customerfnList = htm.getListOfIds(jp, "first_name");
		customerlnList = htm.getListOfIds(jp, "last_name");

		listofMapData = htm.getListOfIds(jp);

	}

	@Given("I GET {string} data for {string} and validate the status code {int} and index as {string}")
	public void getCustomerViewandValidate(String customerView, String customerID, int statusCode, String beforeindex) {

		Utils.setBasePath(customerID + "/");
		Utils.setContentType(ContentType.JSON); // Setup Content Type
		Utils.createSearchQueryPath(customerView);
		res = Utils.getResponse(); // Get response
		viewjp = Utils.getJsonPath(res);
		res = Utils.getResponse();
		htm.checkStatusIs200(res, statusCode);
		viewjp = Utils.getJsonPath(res);

		Map<String, String> customerIDS = htm.getRelatedCustomerIdList(viewjp);

		int index = Integer.parseInt(beforeindex);

		// Map<String, String> customerIDListData = listofMapData.get(index);

		for (Map.Entry<String, String> entry : customerIDS.entrySet()) {

			if (entry.getKey().equalsIgnoreCase("CustomerID")) {
				String customerViewID = entry.getValue();

				String customerIDfromCustomers = customerIDList.get(index).toString();

				Assert.assertEquals("cusstomer ID mismatched for " + customerID, customerViewID,
						customerIDfromCustomers);

			}

			if (entry.getKey().equalsIgnoreCase("first_name")) {
				String customerfname = entry.getValue();

				String customerViewFname = customerfnList.get(index).toString();

				Assert.assertEquals("first_name mismatched for " + customerID, customerfname, customerViewFname);

			}

			if (entry.getKey().equalsIgnoreCase("last_name")) {
				String lastname = entry.getValue();

				String customerViewLname = customerlnList.get(index).toString();

				Assert.assertEquals("last_name mismatched for " + customerID, customerViewLname, lastname);

			}

			if (entry.getKey().equalsIgnoreCase("email")) {
				String emailAddress = entry.getValue();

				String customerViewEmail = customerEmailList.get(index).toString();

				Assert.assertEquals("email mismatched for " + customerID, customerViewEmail, emailAddress);

			}

		}

	}

	@Given("I verify {string} the wrong customer ID as {string} and status code should be {int}")
	public void getCustomerViewandValidate(String customerView, String customerID, int statusCode) {

		Utils.setBasePath(customerID + "/");
		Utils.setContentType(ContentType.JSON); // Setup Content Type
		Utils.createSearchQueryPath(customerView);
		res = Utils.getResponse(); // Get response
		viewjp = Utils.getJsonPath(res);
		res = Utils.getResponse();
		htm.checkStatusIs200(res, statusCode);
	}

}

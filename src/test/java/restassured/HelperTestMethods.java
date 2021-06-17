package restassured;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class HelperTestMethods {
// Verify the http response status returned. Check Status Code is 200?
	public void checkStatusIs200(Response res,int responseCode) {
		assertEquals("Status Check Failed!", responseCode, res.getStatusCode());
		System.out.println("Status code:::"+res.getStatusCode());
	}

// Get Ids (For use case-1)
	public ArrayList getListOfIds(JsonPath jp,String value) {
		ArrayList listOfIds = jp.get("data."+value);
		return listOfIds;
	}

// Get Related Customer Ids (For use case-2)
	public Map<String, String> getRelatedCustomerIdList(JsonPath jpath) {
		
		Map<String, String> mapObject = jpath.getMap("data");
		
		return mapObject;
	}
	
	// Get Ids (For use case-1)
		public List<Map<String,String>> getListOfIds(JsonPath jp) {
			List<Map<String,String>> listOfMapDataa= jp.getList("data");
			return listOfMapDataa;
		}

}

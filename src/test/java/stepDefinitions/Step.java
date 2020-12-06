package stepDefinitions;

import java.util.List;
import java.util.Map;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Step {

	private static final String BASE_URL = "https://petstore.swagger.io";
	private static Response response;
	private static String jsonString;
	private static Integer petID = (int) (Math.random() * 9000) + 1000;

	@Given("I can request for available pets in the store")
	public void i_can_request_for_available_pets_in_the_store() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.get("/v2/pet/findByStatus?status=available");

		jsonString = response.asString();
		List<Map<String, Object>> pets = JsonPath.from(jsonString).get("");
		Assert.assertTrue(pets.size() > 0);
	}

	@When("I add the pet to the store")
	public void i_add_the_pet_to_the_store() {

// Creating body json object
		JSONObject AddPetObject = new JSONObject();
		AddPetObject.put("id", petID);
		AddPetObject.put("name", "Dragonnnnn");
		AddPetObject.put("status", "available");
// Creating Category Object		
		JSONObject categoryMap = new JSONObject();
		categoryMap.put("id", 4777477);
		categoryMap.put("name", "uPIxpq_mKp8xhv");
		AddPetObject.put("category", categoryMap);
// Creating Photo Array
		JSONArray photoArray = new JSONArray();
		photoArray.add("gsdhbdsjhbdjhsdb");
		AddPetObject.put("photoUrls", photoArray);
// Creating Tag Array
		JSONObject tagObject1 = new JSONObject();
		tagObject1.put("id", 676);
		tagObject1.put("name", "tSY7VlzCR");
		JSONArray tagsArray = new JSONArray();
		tagsArray.add(tagObject1);
		AddPetObject.put("tags", tagsArray);

		String AddPet = AddPetObject.toJSONString();

		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.contentType("application/json").body(AddPet).put("/v2/pet");
	}

	@Then("I expect the pet to be added to store")
	public void i_expect_the_pet_to_be_added_to_store() {
		Assert.assertEquals(200, response.getStatusCode());
	}

	@When("I change status of pet to sold")
	public void i_change_status_of_pet_to_sold() {
		RestAssured.baseURI = BASE_URL;
		System.out.println(petID);
		RequestSpecification request = RestAssured.given();
		response = request.contentType("application/x-www-form-urlencoded").formParam("status", "sold")
				.post("/v2/pet/" + petID);
	}

	@Then("I expect the status of pet to be set to sold")
	public void i_expect_the_status_of_pet_to_be_set_to_sold() {
		Assert.assertEquals(200, response.getStatusCode());
	}

	@When("I remove the pet from store")
	public void i_remove_the_pet_from_store() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		response = request.contentType("application/json").delete("/v2/pet/" + petID);
	}

	@Then("I expect the pet to be removed from store")
	public void i_expect_the_pet_to_be_removed_from_store() {
		Assert.assertEquals(200, response.getStatusCode());
	}
}

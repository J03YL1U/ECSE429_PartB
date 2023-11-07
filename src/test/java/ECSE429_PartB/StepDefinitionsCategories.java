package ECSE429_PartB;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Random.class)
public class StepDefinitionsCategories {
    static OkHttpClient client = new OkHttpClient();
    private String existingTitle;
    private JSONArray errorMessage;
    private String idNewCategory;


    @Given("a list of categories {string}")
    public void aListOfCategories(String title) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("description", "existing category");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.existingTitle = (String) responseJson.get("id");
    }

    @When("I create the category {string}")
    public void iCreateTheCategoryTask(String title) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", title);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
    }


    @Then("the expected category status code received from the system is {string}")
    public void theExpectedCategoryStatusCodeReceivedFromTheSystemIs(String statusCode) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/"+idNewCategory)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(statusCode), response.code());
    }

    @And("the number of category in the system will be {string}")
    public void theNumberOfCategoryTasksInTheSystemWillBe(String numberOfCategories) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray categories = (JSONArray) responseJson.get("categories");

        assertEquals(Integer.parseInt(numberOfCategories), categories.size());
    }

    @And("{string} category will be in the list of category")
    public void categoryWillBeInTheListOfCategoryTasks(String newCategory) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray todos = (JSONArray) responseJson.get("categories");

        boolean CategoryAdded = false;

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String title = (String) todo.get("title");
            if(title.equals(newCategory)){
                CategoryAdded = true;
            }
        }

        assertTrue(CategoryAdded);
    }
}

package ECSE429_PartB;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class StepDefinitionsTodos {

    static OkHttpClient client = new OkHttpClient();

    @BeforeAll
    public static void checkApiAvailability() {
        //OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:4567/")
                .build();
        boolean isApiAvailable;
        try {
            Response response = client.newCall(request).execute();
            isApiAvailable = (response.code() == 200);
        } catch (IOException e) {
            Assert.fail("We are not connected, please make sure we are connected to the API");
            isApiAvailable = false;
        }
    }

    /* Story 1 */

    /* Normal Flow */

    String testid;
    String testid2;
    JSONArray errorMessage;

    @Given("a todo task list {string}")
    public void a_todo_task_list(String title) throws Exception{

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", true);
        obj.put("description", "existing todo item");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testid = (String) responseJson.get("id");
    }

    @When("I create the todo task {string}")
    public void i_create_the_todo_task(String title) throws Exception {

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", true);
        obj.put("description", "new todo item");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessage = (JSONArray) responseJson.get("errorMessages");
        testid2 = (String) responseJson.get("id");
    }

    @Then("the returned todo status code of the system is {string}")
    public void the_returned_status_code_of_the_system_is(String statusCode) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(statusCode), response.code());
    }


    @Then("{string} todo will be in the list {string}")
    public void todo_will_be_in_the_list(String todo, String todoList) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+testid2)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");
        String[] parts = todoList.split(",");
        for (Object todoObject : todos) {
            JSONObject category = (JSONObject) todoObject;
            String title = (String) category.get("title");
            assertEquals(parts[1], title);
        }
    }
}

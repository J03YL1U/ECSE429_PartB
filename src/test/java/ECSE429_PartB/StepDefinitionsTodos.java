package ECSE429_PartB;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.MethodOrderer.Random;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(Random.class)
public class StepDefinitionsTodos {

    static OkHttpClient client = new OkHttpClient();

    /* Creating a new Todo task */

    /* Normal Flow */
    String idExistingTodo;
    String idNewTodo;
    JSONArray errorMessage;

    @Given("a list of todo task {string}")
    public void a_list_of_todo_task(String title) throws Exception{

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

        idExistingTodo = (String) responseJson.get("id");
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
        idNewTodo = (String) responseJson.get("id");
    }

    @Then("the expected todo status code received from the system is {string}")
    public void the_expected_todo_status_code_received_from_the_system_is(String statusCode) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(statusCode), response.code());
    }

    @Then("the number of todo tasks in the system will be {string}")
    public void the_number_of_todo_tasks_in_the_system_will_be(String numberOfTasks) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray todos = (JSONArray) responseJson.get("todos");

        assertEquals(Integer.parseInt(numberOfTasks), todos.size());
    }

    @Then("{string} todo will be in the list of todo tasks")
    public void todo_will_be_in_the_list(String added_todo_task) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray todos = (JSONArray) responseJson.get("todos");

        boolean todoTaskAdded = false;

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String title = (String) todo.get("title");
            if(title.equals(added_todo_task)){
                todoTaskAdded = true;
            }
        }

        assertTrue(todoTaskAdded);
    }
}

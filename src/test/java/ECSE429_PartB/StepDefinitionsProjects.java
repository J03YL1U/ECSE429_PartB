package ECSE429_PartB;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.MethodOrderer.Random;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@TestMethodOrder(Random.class)
public class StepDefinitionsProjects {

    static OkHttpClient client = new OkHttpClient();
    String url = "http://localhost:4567/projects";

    // Used to keep track of test progress
    JSONArray errorMessages;
    int statusCode;
    String testProjectId;

    /* Create a new project */
    // Normal Flow and Error Flow
    @When("I create a project with title {string}, description {string}, completed status {string}, active status {string}")
    public void i_create_a_project_with_title_description_completed_active(
            String title, String description, String completed, String active) throws IOException, ParseException {
        // Create object
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("description", description);

        // Need to check if it's a valid boolean otherwise will be incorrect for tests
        // Boolean.valueOf() returns true or false for any string regardless
        if (completed.equalsIgnoreCase("true") || completed.equalsIgnoreCase("false"))
            obj.put("completed", Boolean.valueOf(completed));
        else obj.put("completed", completed);

        if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
            obj.put("active", Boolean.valueOf(active));
        else obj.put("active", active);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        // Make a request to create a new project
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();

        System.out.println(statusCode);

        // Save the id for testing
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testProjectId = (String) responseJson.get("id");
        errorMessages = (JSONArray) responseJson.get("errorMessages");
    }

    // Alternate Flow
    @When("I create a project with id {int}")
    public void i_create_a_project_with_id(int id) throws IOException, ParseException {
        // Create object
        JSONObject obj = new JSONObject();
        obj.put("id", id);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        // Make a request to create a new project
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();

        // Save the id for testing
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testProjectId = (String) responseJson.get("id");
        errorMessages = (JSONArray) responseJson.get("errorMessages");
    }

    @Then("the expected project status code received should be {int}")
    public void the_expected_project_status_code_received_should_be(int status) {
        assertEquals(status, statusCode);
    }

    @Then("the number of projects in the system will be {int}")
    public void the_number_of_projects_in_the_system_will_be(int number) throws IOException, ParseException {
        // Get the list of projects in the system
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray projects = (JSONArray) responseJson.get("projects");

        assertEquals(number, projects.size());

    }

    @Then("a project with title {string}, description {string}, completed status {string}, active status {string} will be in the list of projects")
    public void a_project_with_title_description_completed_status_active_status_will_be_in_projects(
            String title, String description, String completed, String active) throws IOException, ParseException {
        // Get the list of projects in the system
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray projects = (JSONArray) responseJson.get("projects");

        for (Object project : projects) {
            JSONObject newProject = (JSONObject) project;
            if (newProject.get("id").equals(testProjectId)) {
                assertEquals(title, newProject.get("title"));
                assertEquals(description, newProject.get("description"));
                assertEquals(completed, newProject.get("completed"));
                assertEquals(active, newProject.get("active"));
            }
        }
    }

    @Then("an error message {string} will be returned")
    public void an_error_message_will_be_returned(String error) {
        assertEquals(error, errorMessages.get(0));
    }

    /* Update a project's completed status */
    // Normal Flow
    @Given("a project with completed status {string} exists in the system")
    public void a_project_completed_status_exists_in_the_system(String completed) throws IOException, ParseException {
        // Create new project with desired completed status
        JSONObject obj = new JSONObject();

        // Need to check if it's a valid boolean otherwise will be incorrect for tests
        // Boolean.valueOf() returns true or false for any string regardless
        if (completed.equalsIgnoreCase("true") || completed.equalsIgnoreCase("false"))
            obj.put("completed", Boolean.valueOf(completed));
        else obj.put("completed", completed);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        // Make a request to create a new project
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testProjectId = (String) responseJson.get("id");
    }

    @When("I update the completed status of the project to {string}")
    public void i_update_the_completed_status_of_a_project_to(String new_completed) throws IOException, ParseException {
        // Create a PUT request (POST will also work)
        JSONObject obj = new JSONObject();

        // Need to check if it's a valid boolean otherwise will be incorrect for tests
        // Boolean.valueOf() returns true or false for any string regardless
        if (new_completed.equalsIgnoreCase("true") || new_completed.equalsIgnoreCase("false"))
            obj.put("completed", Boolean.valueOf(new_completed));
        else obj.put("completed", new_completed);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        // Make a request to update a project with a certain id
        Request request = new Request.Builder()
                .url(url + "/" + testProjectId)
                .put(body)
                .build();

        // Get the error messages and status code
        Response response = client.newCall(request).execute();
        statusCode = response.code();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessages = (JSONArray) responseJson.get("errorMessages");
    }

    @Then("the completed status of the project will be {string}")
    public void the_completed_status_of_the_project_with_id_will_be(String new_completed) throws IOException, ParseException {
        Request request = new Request.Builder()
                .url(url + "/" + testProjectId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray projects = (JSONArray) responseJson.get("projects");

        for (Object project : projects) {
            JSONObject newProject = (JSONObject) project;
            if (newProject.get("id").equals(testProjectId)) {
                assertEquals(new_completed, newProject.get("completed"));
            }
        }
    }

    // Alternate Flow
    @When("I update the completed status of a project with id {int} to {string}")
    public void i_update_the_completed_status_of_a_project_with_invalid_id_to(
            int invalid_id, String new_completed) throws IOException, ParseException {
        // Create a PUT request (POST will also work)
        JSONObject obj = new JSONObject();

        // Need to check if it's a valid boolean otherwise will be incorrect for tests
        // Boolean.valueOf() returns true or false for any string regardless
        if (new_completed.equalsIgnoreCase("true") || new_completed.equalsIgnoreCase("false"))
            obj.put("completed", Boolean.valueOf(new_completed));
        else obj.put("completed", new_completed);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        // Create new update request
        Request request = new Request.Builder()
                .url(url + "/" + invalid_id)
                .put(body)
                .build();

        // Receive status code and errors for testing
        Response response = client.newCall(request).execute();
        statusCode = response.code();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        errorMessages = (JSONArray) responseJson.get("errorMessages");
    }

    /* Delete a project */
    // Normal Flow
    @Given("a project with title {string} exists in the system")
    public void a_project_with_title_exists_in_the_system(String title) throws IOException, ParseException {
        // Create new project with desired title
        JSONObject obj = new JSONObject();

        obj.put("title", title);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        // Make a request to create a new project
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        testProjectId = (String) responseJson.get("id");
    }

    @When("I delete a project with id {string}")
    public void i_delete_a_project_with_id(String id) throws IOException, ParseException {
        testProjectId = id;

        Request request = new Request.Builder()
                .url(url + "/" + testProjectId)
                .delete()
                .build();

        // Check for errors
        Response response = client.newCall(request).execute();
        statusCode = response.code();

        String responseBody = response.body().string();

        // Will raise EOF error if empty so must check
        if (!responseBody.isEmpty()) {
            JSONParser parser = new JSONParser();
            JSONObject responseJson = (JSONObject) parser.parse(responseBody);

            errorMessages = (JSONArray) responseJson.get("errorMessages");
        }
    }
}

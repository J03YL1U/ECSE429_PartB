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
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(Random.class)
public class StepDefinitionsTodos {

    static OkHttpClient client = new OkHttpClient();
    int statusCode;
    String idNewTodo;
    String title = "title";
    boolean doneStatus = false;
    String desc = "";

    /*------------------------------------------ ID001_CreatingTodoTaskFeature ------------------------------------------*/
    /* Normal Flow */

    @Given("a list of todo task {string}")
    public void a_list_of_todo_task(String title) throws Exception{
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        client.newCall(request).execute();
    }

    @When("I create the todo task {string}")
    public void i_create_the_todo_task(String title) throws Exception {

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

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

    @Then("{string} will be in the list of todo tasks")
    public void will_be_in_the_list(String added_todo_task) throws Exception {
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

    /*Error Flow */
    @Then("the returned status code is {string}")
    public void the_returned_status_code_is(String string) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/123456")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(Integer.parseInt(string), response.code());
    }

    /*------------------------------------------ ID002_RemovingTodoTaskFeature ------------------------------------------*/
    /*Normal Flow*/
    @Given("a todo task with title {string}")
    public void a_task_with_title(String title) throws Exception {

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        idNewTodo = (String) responseJson.get("id");
    }

    @When("I remove the todo task with title {string}")
    public void i_remove_the_todo_task_with_title(String title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();
    }

    @Then("the remove todo status code we should receive is {string}")
    public void the_remove_status_code_we_should_receive_is(String statusCode) {
        assertEquals(statusCode, String.valueOf(this.statusCode));
    }

    @Then("the todo task with title {string} will be removed from the system")
    public void the_todo_with_title_will_be_removed_from_the_system(String string) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONArray errorMessage = (JSONArray) responseJson.get("errorMessages");

        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with todos/"+idNewTodo, errorObject.toString());
        }
    }

    /*Alternate Flow*/
    @Given("two todo tasks with the same title {string}")
    public void two_todo_tasks_with_the_same_title(String title) throws Exception {

        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        idNewTodo = (String) responseJson.get("id");

        client.newCall(request).execute();
    }

    @When("I remove the todo task with id {string}")
    public void i_remove_the_todo_task_with_id(String id) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+ id)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();
    }

    @Then("the todo task with id {string} will be removed from the system")
    public void the_todo_task_with_id_will_be_removed_from_the_system(String id) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+id)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONArray errorMessage = (JSONArray) responseJson.get("errorMessages");

        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with todos/"+id, errorObject.toString());
        }
    }
    /*Error Flow*/

    @Then("the todo task with title {string} will be not be removed")
    public void the_todo_task_with_title_will_not_be_removed() throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONArray errorMessage = (JSONArray) responseJson.get("errorMessages");

        for (Object errorObject : errorMessage) {
            assertEquals( "Could not find an instance with todos/"+idNewTodo, errorObject.toString());
        }
    }

    /*------------------------------------------ ID003_ChangeTodoTaskStatusFeature ------------------------------------------*/
    /* Normal Flow */

    @Given("a task with done status {string}")
    public void a_task_with_done_status(String doneStatus) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", Boolean.parseBoolean(doneStatus));
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        idNewTodo = (String) responseJson.get("id");
    }

    @When("I update the task done status to {string}")
    public void i_update_the_task_done_status_to(String doneStatus) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", Boolean.parseBoolean(doneStatus));
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .put(body)
                .build();

        client.newCall(request).execute();
    }

    @Then("the task has done status {string}")
    public void the_task_has_done_status(String doneStatus) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String doneStatus1 = (String) todo.get("doneStatus");
            assertEquals(doneStatus, doneStatus1);
        }
    }

    /*Error Flow*/
    @When("I update the todo task with {string} to done status {string}")
    public void i_update_the_todo_task_with_to_done_status(String invalidId, String doneStatus) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", Boolean.parseBoolean(doneStatus));
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+invalidId)
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();
    }

    @Then("the expected status code is {string}")
    public void the_expected_status_code_is(String statusCode) {
        assertEquals(this.statusCode, Integer.parseInt(statusCode));
    }

    /*------------------------------------------ ID004_ChangeTodoTaskTitleFeature ------------------------------------------*/
    /* Normal Flow */
    @When("I update the task title to {string}")
    public void i_update_the_task_title_to(String title) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .put(body)
                .build();

        client.newCall(request).execute();
    }

    @Then("the todo task will have title {string}")
    public void the_task_has_title_a(String title) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String title1 = (String) todo.get("title");
            assertEquals(title, title1);
        }
    }

    /*Error Flow*/
    @When("I update the todo task {string} with title {string}")
    public void i_update_the_todo_task_with_title (String invalidId, String title) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+invalidId)
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();
    }

    /*------------------------------------------ ID005_ChangeTodoTaskDescFeature ------------------------------------------*/
    /* Normal Flow */
    @Given("a todo task with description {string}")
    public void a_todo_task_with_description(String description) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        idNewTodo = (String) responseJson.get("id");
    }

    @When("I update the todo task description to {string}")
    public void i_update_the_todo_task_description_to(String new_description) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", new_description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .put(body)
                .build();

        client.newCall(request).execute();
    }

    @Then("the todo task will have the description {string}")
    public void the_task_has_description(String resulting_description) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+idNewTodo)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray todos = (JSONArray) jsonObject.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String description = (String) todo.get("description");
            assertEquals(resulting_description, description);
        }
    }

    /*Error Flow*/
    @When("I update the todo task with {string} with description {string}")
    public void i_update_the_todo_task_with_with_description(String invalidId, String new_description) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", doneStatus);
        obj.put("description", new_description);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/"+invalidId)
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        statusCode = response.code();
    }

    @Given("a category with id {string}")
    public void a_category_with_id(String someid) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + someid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray categories = (JSONArray) responseJson.get("categories");

        for (Object categoryObject : categories) {
            JSONObject category = (JSONObject) categoryObject;
            String id = (String) category.get("id");
            assertEquals(someid, id);
        }
    }

    @Given("a todo with id {string}")
    public void a_todo_with_id(String someid) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/" + someid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray categories = (JSONArray) responseJson.get("todos");

        for (Object categoryObject : categories) {
            JSONObject category = (JSONObject) categoryObject;
            String id = (String) category.get("id");
            assertEquals(someid, id);
        }
    }

    @Given("a project with id {string}")
    public void a_project_with_id(String someid) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + someid)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray categories = (JSONArray) responseJson.get("projects");

        for (Object categoryObject : categories) {
            JSONObject category = (JSONObject) categoryObject;
            String id = (String) category.get("id");
            assertEquals(someid, id);
        }
    }


    @When("I create project with title {string} for category with id {string}")
    public void i_create_project_title_for_category(String title, String id) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + id + "/projects")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(201, response.code());
        assertEquals("Created", response.message());
    }


    @Then("I delete category {string} and project {string} relationship")
    public void i_delete_todo_category_rel(String id1, String id2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + id1 + "/projects/" + id2)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }


    @Then("there is no project for category {string}")
    public void rel_between_todo_category_does_not_exist(String id1) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + id1 + "/projects")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray projects = (JSONArray) responseJson.get("projects");

        assertEquals(0, projects.size());
    }


    //17
    @When("I delete relationship between todo {string} and project {string}")
    public void i_delete_todo_project_rel(String id1, String id2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/" + id1 + "/tasksof/" + id2)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }
    @Then("deleted relationship for todo {string} and project does not exist")
    public void no_rel_between_todo_and_project(String id1) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/" + id1 + "/tasksof")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray projects = (JSONArray) responseJson.get("projects");

        assertEquals(0, projects.size());
    }
    //18
    @Then("verify project with title {string} exists under category {string}")
    public void no_rel_between_todo_and_project(String sometitle, String someid) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + someid + "/projects")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray categories = (JSONArray) responseJson.get("projects");

        for (Object categoryObject : categories) {
            JSONObject category = (JSONObject) categoryObject;
            String id = (String) category.get("title");
            assertEquals(sometitle, id);
        }
    }

    //20

    @When("I delete relationship between project {string} and todo {string}")
    public void i_delete_project_todo_rel(String id1, String id2) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + id1 + "/tasks/" + id2)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }
    @Then("deleted relationship for project {string} and todo does not exist")
    public void verify_delete_relationship_project_todo(String id1) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/projects/" + id1 + "/tasks")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray todos = (JSONArray) responseJson.get("todos");

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String id = (String) todo.get("id");
            assertNotEquals(id, id1);
        }

    }

}

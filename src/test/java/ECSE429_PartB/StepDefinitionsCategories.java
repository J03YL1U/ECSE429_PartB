package ECSE429_PartB;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.Random.class)
public class StepDefinitionsCategories {
    static OkHttpClient client = new OkHttpClient();
    private String existingId;
    private JSONArray errorMessage;
    private String idNewCategory;
    private String statusCode;

    private String idExistingTodo;


    //    ID006: Create a category
    @Given("a list of categories {string}")
    public void aListOfCategories(String title) throws Exception {
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

        this.existingId = (String) responseJson.get("id");
    }

    @When("I create the category {string}")
    public void iCreateTheCategoryTask(String title) throws Exception {
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
        this.statusCode = String.valueOf(response.code());
    }


    @Then("the expected category status code received from the system is {string}")
    public void theExpectedCategoryStatusCodeReceivedFromTheSystemIs(String statusCode) throws Exception {
        assertEquals(statusCode, this.statusCode);
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
    public void categoryWillBeInTheListOfCategoryTasks(String newCategory) throws Exception {
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
            if (title.equals(newCategory)) {
                CategoryAdded = true;
            }
        }

        assertTrue(CategoryAdded);
    }

    //    ID007: Update a category's description

    @Given("an existing category {string} with description {string}")
    public void anExistingCategoryWithDescription(String title, String desc) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("description", desc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.existingId = (String) responseJson.get("id");
    }

    @When("I update the existing description to {string}")
    public void iUpdateTheExistingDescriptionTo(String newDesc) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", newDesc);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());
    }

    @And("the existing category's description should be {string}")
    public void theExistingCategorySDescriptionShouldBe(String newDesc) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray categories = (JSONArray) jsonObject.get("categories");

        for (Object categoriesObj : categories) {
            JSONObject todo = (JSONObject) categoriesObj;
            String desc = (String) todo.get("description");
            assertEquals(newDesc, desc);
        }

    }

    @When("I update the description to {string} of a category that doesn't exist")
    public void iUpdateTheDescriptionToOfACategoryThatDoesnTExist(String newDesc) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("description", newDesc);
        String invalidId = "1234567890";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + invalidId)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());
    }

    //    ID008: Update a category's title
    @Given("an existing category with title {string}")
    public void anExistingCategoryWithTitle(String title) throws Exception {
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

        this.existingId = (String) responseJson.get("id");
    }

    @When("I update the existing title to {string}")
    public void iUpdateTheExistingTitleTo(String newTitle) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("title", newTitle);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());
    }

    @And("the existing category's title should be {string}")
    public void theExistingCategorySTitleShouldBe(String newTitle) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        JSONObject jsonObject = new JSONObject(responseJson);
        JSONArray categories = (JSONArray) jsonObject.get("categories");

        for (Object categoriesObj : categories) {
            JSONObject todo = (JSONObject) categoriesObj;
            String title = (String) todo.get("title");
            assertEquals(newTitle, title);
        }
    }

    @When("I try to update the title to {string} of a category that doesn't exist")
    public void iTryToUpdateTheTitleToOfACategoryThatDoesnTExist(String newTitle) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", newTitle);
        String invalidId = "1234567890";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + invalidId)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());

    }

    //    ID009: delete a category
    @When("I delete a category {string}")
    public void iDeleteACategory(String category) throws Exception {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());
    }

    @And("{string} category will no longer exist in the system")
    public void categoryWillNoLongerExistInTheSystem(String deleteCategory) throws Exception {
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

        boolean categoryRemoved = true;

        for (Object todoObject : todos) {
            JSONObject todo = (JSONObject) todoObject;
            String title = (String) todo.get("id");
            if (title.equals(this.existingId)) {
                categoryRemoved = false;
            }
        }

        assertTrue(categoryRemoved);
    }

    @Given("a list of categories {string} with two categories of the title")
    public void aListOfCategoriesWithTwoCategoriesOfTheTitle(String category) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", category);
        obj.put("description", "#1");

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.existingId = (String) responseJson.get("id");

        obj = new JSONObject();

        obj.put("title", category);
        obj.put("description", "#2");

        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        client.newCall(request).execute();
    }

    @When("I delete a category {string} that is not part of the list")
    public void iDeleteACategoryThatIsNotPartOfTheList(String category) throws Exception {
        JSONObject obj = new JSONObject();

        String invalidId = "1234567890";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + invalidId)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.idNewCategory = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());
    }
    //    ID010: add category-todo relationship

    @And("an existing todo with title {string}")
    public void anExistingTodoWithId(String title) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("title", title);
        obj.put("doneStatus", true);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.idExistingTodo = (String) responseJson.get("id");
    }

    @When("create a relationship between {string} and {string}")
    public void createARelationshipBetweenAnd(String category, String todo) throws Exception {
        JSONObject obj = new JSONObject();

        obj.put("id", this.idExistingTodo);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId + "/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        this.statusCode = String.valueOf(response.code());
    }

    @When("create a new todo {string} with category that doesn't exist")
    public void createANewTodoWithCategoryThatDoesnTExist(String title) throws Exception {
        JSONObject obj = new JSONObject();

        String invalidId = "1234567890";
        obj.put("title", title);
        obj.put("doneStatus", true);
        obj.put("category", invalidId);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.existingId = (String) responseJson.get("id");
        this.statusCode = String.valueOf(response.code());
    }


    @And("{string} will be part of {string}")
    public void willBePartOf(String todo, String category) throws Exception, ParseException {
        Request request = new Request.Builder()
                .url("http://localhost:4567/categories/" + this.existingId)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);
        JSONArray categories = (JSONArray) responseJson.get("categories");

        boolean relationshipPresent = false;

        for (Object categoryObject : categories) {
            JSONObject curr_category = (JSONObject) categoryObject;
            JSONArray todos = (JSONArray) curr_category.get("todos");
            for (Object todoObject : todos) {
                JSONObject curr_todo = (JSONObject) todoObject;
                String todo_id = (String) curr_category.get("id");
                if (todo_id.equals(this.idExistingTodo)) {
                    relationshipPresent = true;
                }
            }
        }

        assertTrue(relationshipPresent);
    }

    @When("create a new category {string} with todo {string}")
    public void createANewCategoryWithTodo(String todo, String category) throws Exception {
        JSONObject todos = new JSONObject();

        todos.put("id", this.idExistingTodo);

        JSONObject obj = new JSONObject();

        obj.put("title", category);
        obj.put("todos", todos);


        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj.toString());

        Request request = new Request.Builder()
                .url("http://localhost:4567/categories")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        JSONParser parser = new JSONParser();
        JSONObject responseJson = (JSONObject) parser.parse(responseBody);

        this.existingId = (String) responseJson.get("id");
        this.errorMessage = (JSONArray) responseJson.get("errorMessages");
        this.statusCode = String.valueOf(response.code());
    }
}

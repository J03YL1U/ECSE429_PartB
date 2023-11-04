package ECSE429_PartB;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.ConnectException;
public class StepDefinitionsCommon {
    private static final OkHttpClient client = new OkHttpClient();
    private Process apiProcess;

    @Before("@scenarioOutline")
    public void beforeScenario() {
        try {
            String filePath = "src\\test\\java\\runTodoManagerRestAPI-1.5.5.jar";
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", filePath);
            apiProcess = processBuilder.start();

            Thread.sleep(1000);

            Request request = new Request.Builder()
                    .url("http://localhost:4567/")
                    .build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("API is not running or is not responding as expected.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error starting the API: " + e.getMessage(), e);
        }
    }

    @After("@scenarioOutline")
    public void afterScenario() {
        try {
            Request request = new Request.Builder()
                    .url("http://localhost:4567/shutdown")
                    .get()
                    .build();
            client.newCall(request).execute();
            apiProcess.destroy();
        } catch (ConnectException e) {
            // Server doesn't respond anymore after shutdown; no need for further action.
        } catch (IOException e) {
            throw new RuntimeException("Error shutting down the API: " + e.getMessage(), e);
        }
    }
}

package RestAPI;

import com.example.brentonang.thebird.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 11/9/14.
 */
public class UserTask extends RequestTask {

    private String userId;
    private String username;
    private String accessToken;
    private String refreshToken;

    private Client client;

    private RESTCallback createdCB;
    private RESTCallback loginCB;
    private RESTCallback getUserCB;

    public UserTask(Client client) {
        client = client;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void refresh(RESTCallback callback) {
        login(refreshToken, callback);
    }

    // Async create a user with the given username and password
    public void create(String username, String password, RESTCallback callback) {
        HashMap<String, String> request;
        request.put("uri", "http://thebird.azurewebsites.net/user");
        request.put("method", "POST");
        request.put("username", username);
        request.put("password", password);
        createdCB = callback;

        this.execute(request);
    }

    public void login(String username, String password, RESTCallback callback) {
        HashMap<String, String> request;
        request.put("uri", "http://thebird.azurewebsites.net/auth/token");
        request.put("method", "POST");
        request.put("grant_type", "password");
        request.put("client_id", client.getId());
        request.put("client_secret", client.getSecret());
        request.put("username", username);
        request.put("password", password);
        loginCB = callback;

        this.execute(request);
    }

    public void login(String refresh_token, RESTCallback callback) {
        HashMap<String, String> request;
        request.put("uri", "http://thebird.azurewebsites.net/auth/token");
        request.put("method", "POST");
        request.put("grant_type", "refresh_token");
        request.put("client_id", client.getId());
        request.put("client_secret", client.getSecret());
        request.put("refresh_token", refresh_token);
        loginCB = callback;

        this.execute(request);
    }

    public void getUser(RESTCallback callback) {
        HashMap<String, String> request;
        request.put("uri", "http://thebird.azurewebsites.net/user");
        request.put("method", "GET");
        request.put("Authorization", "Bearer " + accessToken);
        getUserCB = callback;

        this.execute(request);
    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        String action = result.get("action");
        // Login
        if (action.isEmpty()) {
            accessToken = result.get("access_token");
            refreshToken = result.get("refresh_token");
            getUser(null);

            if (loginCB != null) {
                loginCB.call(result);
                loginCB = null;
            }
        }
        if (action == "created") {
            userId = result.get("userId");
            username = result.get("username");

            if (createdCB != null) {
                createdCB.call(result);
                createdCB = null;
            }
        }
        if (action == "getUser") {
            userId = result.get("userId");
            username = result.get("username");

            if (getUserCB != null) {
                getUserCB.call(result);
                getUserCB = null;
            }
        }
    }

}
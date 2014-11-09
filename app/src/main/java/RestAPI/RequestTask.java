package RestAPI;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;

import java.util.Map;

/**
 * Created by adam on 11/9/14.
 */
public class RequestTask extends AsyncTask<Map<String, String>, String, Map<String, String>> {
    @Override
    protected Map<String, String> doInBackground(Map<String,String>... params) {
        String uri = params[0].get("uri");
        String method = params[0].get("method");
        params[0].remove("uri");
        params[0].remove("method");

        HttpResponse response;
        if (method == "GET") {
            response = HttpRequest.getRequest(uri, params[0]);
        } else if (method == "POST") {
            response = HttpRequest.postRequest(uri, params[0]);
        }
        return HttpRequest.getResponseBody(response);
    }
}

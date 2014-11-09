package RestAPI;

import android.net.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by adam on 11/9/14.
 */
public class HttpRequest {

    public static HttpResponse postRequest(String uri, Map<String, String> json) {
        String sjson = new GsonBuilder().create().toJson(json, Map.class);
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(sjson));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse getRequest(String uri, Map<String, String> json) {
        try {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Authorization", json.get("Authorization"));
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpGet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getResponseBody(HttpResponse response) {
        String resText;
        HttpEntity entity;
        try {
            entity = response.getEntity();

            InputStream in = entity.getContent();
            if (in == null) { return "";}
            if (entity.getContentLength() > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }

            String charSet;
            if (entity.getContentType() != null) {
                HeaderElement values[] = entity.getContentType().getElements();
                if (values.length > 0) {
                    NameValuePair param = values[0].getParameterByName("charset");
                    if (param != null) {
                        charSet = param.getValue();
                    }
                }
            }
            if (charSet == null) {
                charSet = HTTP.DEFAULT_CONTENT_CHARSET;
            }

            Reader reader = new InputStreamReader(in, charSet);
            StringBuilder buffer = new StringBuilder();
            try {
                int l;
                char[] tmp = new char[1024];
                while ((l = reader.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
            } finally {
                reader.close();
            }
            resText = buffer.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (entity != null) {
                try {
                    entity.consumeContent();
                } catch (IOException e1) {

                }
            }
        }

        Gson gson = new Gson();
        return gson.fromJson(resText, Map.class);
    }

}

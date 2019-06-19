package com.armazempb.ufpb.cameraarmazempb;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

/**
 *     Implementation of the CommunicationWithAPI to acess the olho de calango api.
 * */
public class CommunicationWithOlhoDeCalangoAPI implements CommunicationWithAPI {

    private final String URL_BASE;
    private final int TIMEOUT;
    private AsyncHttpClient asyncHttpClient;
    private String route;

    public CommunicationWithOlhoDeCalangoAPI() {
        this("127.0.0.1",5000, 3000);
    }

    public CommunicationWithOlhoDeCalangoAPI(String url, int port, int TIMEOUT) {
        this.URL_BASE = url + ":" + port;
        this.TIMEOUT = TIMEOUT;
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(this.TIMEOUT);

        this.route = "/decode";
    }

    @Override
    public boolean sendFile(File file) {
        RequestParams rp = new RequestParams();

        try {
            rp.put("image", file);
            rp.setForceMultipartEntityContentType(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final BooleanWrapper test = new BooleanWrapper(false);

        asyncHttpClient.post(URL_BASE + this.route, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                test.invert();
            }
        });
        return test.value;
    }
}

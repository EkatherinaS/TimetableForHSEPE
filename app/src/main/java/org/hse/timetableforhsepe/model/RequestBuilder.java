package org.hse.timetableforhsepe.model;

import android.content.Context;
import android.util.Log;

import org.hse.timetableforhsepe.view_model.DatabaseManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestBuilder {

    private final static String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    private final static String TAG = "RequestBuilder";

    private static RequestBuilder instance;

    public static RequestBuilder getInstance() {
        if (instance == null) {
            instance = new RequestBuilder();
        }
        return instance;
    }

    public void getDateTime(Callback callback) {
        Log.i(TAG, "getDate: " + callback);
        getTime(callback);
    }

    private void getTime(Callback callback) {
        Request request = new Request.Builder().url(URL).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}

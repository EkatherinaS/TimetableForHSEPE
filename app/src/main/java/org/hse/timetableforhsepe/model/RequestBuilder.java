package org.hse.timetableforhsepe.model;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestBuilder {

    private final static String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    private final static String TAG = "RequestBuilder";

    public static void getDate(Callback callback) {
        getTime(callback);
    }

    private static void getTime(Callback callback) {
        Request request = new Request.Builder().url(URL).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Log.i("TAG", call.toString());
        call.enqueue(callback);
    }
}

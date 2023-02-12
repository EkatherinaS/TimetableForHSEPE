package org.hse.timetableforhsepe.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.hse.timetableforhsepe.view_model.TimeResponse;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestBuilder {

    private final static String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    private final static String TAG = "RequestBuilder";
    private static Date date = null;

    public static void getDate(Callback callback) {
        getTime(callback);
    }

    private static void getTime(Callback callback) {
        Request request = new Request.Builder().url(URL).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}

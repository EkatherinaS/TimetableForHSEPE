package org.hse.timetableforhsepe.view_model;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.hse.timetableforhsepe.R;
import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableWithGroupEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainViewModel extends AndroidViewModel {

    private HseRepository repository;

    private final static String TAG = "BaseActivity";
    public final static String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    private final OkHttpClient client = new OkHttpClient();
    public static Date date;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new HseRepository(application);
    }

    public LiveData<Date> getTime() {
        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);
        MutableLiveData<Date> liveData = new MutableLiveData<>();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                parseResponse(response);
                liveData.postValue(date);
            }
        });
        return liveData;
    }

    private void parseResponse(Response response) {
        Gson gson = new Gson();
        ResponseBody body = response.body();
        try {
            if (body == null) {
                return;
            }
            String string = body.string();
            Log.d(TAG, string);
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
            date = Converters.dateToFullFormat(currentTimeVal);
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return repository.getGroups();
    }

    public LiveData<List<TeacherEntity>> getTeachers() {
        return repository.getTeachers();
    }

    public LiveData<List<FullTimeTableEntity>> getTimetable() {
        return repository.getTimetable();
    }

    public LiveData<List<TimeTableWithTeacherEntity>> getTimeTableWithTeacherByDate(Date date) {
        return repository.getTimeTableWithTeacherByDate(date);
    }

    public LiveData<List<TimeTableWithGroupEntity>> getTimeTableWithGroupByDate(Date date) {
        return repository.getTimeTableWithGroupByDate(date);
    }

}

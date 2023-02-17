package org.hse.timetableforhsepe.view_model;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.view.BaseActivity;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainViewModel extends AndroidViewModel {

    private final HseRepository repository;

    private final static String TAG = "MainActivity";

    private MutableLiveData<Date> date = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new HseRepository(application);
    }

    private void callbackDateTime() {
        repository.getDateTime(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "getTime onResponse" + parseResponse(response));
                date.postValue(parseResponse(response));
            }
        });
    }

    public MutableLiveData<Date> getDateTime() {
        callbackDateTime();
        Log.i(TAG, "getDate: " + date.getValue());
        return date;
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return repository.getGroups();
    }

    public LiveData<List<TeacherEntity>> getTeachers() {
        return repository.getTeachers();
    }

    public LiveData<List<FullTimeTableEntity>> getLessonByGroupId(Integer id, Date dateTime) {
        Log.i(TAG, "getLessonByGroupId:" + dateTime);
        return repository.getLessonByGroupId(id, dateTime);
    }

    public LiveData<List<FullTimeTableEntity>> getLessonByTeacherId(Integer id, Date dateTime) {
        Log.i(TAG, "getLessonByTeacherId:" + dateTime);
        return repository.getLessonByTeacherId(id, dateTime);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByGroupId(Integer id, BaseActivity.ScheduleType type, Date dateTime) {
        Log.i(TAG, "getTimetableByGroupId:" + dateTime);
        return repository.getTimetableByGroupId(id, type, dateTime);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByTeacherId(Integer id, BaseActivity.ScheduleType type, Date dateTime) {
        Log.i(TAG, "getTimetableByTeacherId:" + dateTime);
        return repository.getTimetableByTeacherId(id, type, dateTime);
    }

    private Date parseResponse(Response response) throws IOException {
        Gson gson = new Gson();
        ResponseBody body = response.peekBody(2048);
        try {
            if (body == null) {
                return null;
            }
            String string = body.string();
            Log.d(TAG, string);
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
            Log.d(TAG, timeResponse.toString());
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
            return Converters.dateToFullFormat(currentTimeVal);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }
}

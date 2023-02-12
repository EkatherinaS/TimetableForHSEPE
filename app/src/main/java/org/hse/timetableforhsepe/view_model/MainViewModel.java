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
import org.hse.timetableforhsepe.model.RequestBuilder;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableWithGroupEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;
import org.hse.timetableforhsepe.view.BaseActivity;

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

    private MutableLiveData<Date> date = new MutableLiveData<>();

    private final static String TAG = "BaseActivity";

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new HseRepository(application);
    }

    public void getTime() {
        repository.getTime(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               Date dateVal = parseResponse(response);
               date.postValue(dateVal);
            }
        });
    }

    public MutableLiveData<Date> getDate() {
        getTime();
        return date;
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return repository.getGroups();
    }

    public LiveData<List<TeacherEntity>> getTeachers() {
        return repository.getTeachers();
    }

    public LiveData<List<FullTimeTableEntity>> getLessonByGroupId(Integer id) {
        return repository.getLessonByGroupId(id);
    }

    public LiveData<List<FullTimeTableEntity>> getLessonByTeacherId(Integer id) {
        return repository.getLessonByTeacherId(id);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByGroupId(Integer id, BaseActivity.ScheduleType type) {
        return repository.getTimetableByGroupId(id, type);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByTeacherId(Integer id, BaseActivity.ScheduleType type) {
        return repository.getTimetableByTeacherId(id, type);
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

    private Date parseResponse(Response response) {
        Gson gson = new Gson();
        ResponseBody body = response.body();
        try {
            if (body == null) {
                return null;
            }
            String string = body.string();
            Log.d(TAG, string);
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
            return Converters.dateToFullFormat(currentTimeVal);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }


}

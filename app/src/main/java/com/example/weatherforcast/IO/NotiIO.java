package com.example.weatherforcast.IO;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.work.Data;

import com.example.weatherforcast.model.Constants;
import com.example.weatherforcast.model.Noti;
import com.example.weatherforcast.model.NotiArrayList;
import com.example.weatherforcast.model.NotificationHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.UUID;

public class NotiIO {
    private static final String CHANNEL_ID = "noti";
    private static NotiIO instance;
    private final Context context;

    private NotiArrayList notiList;
    private JSONArray array;
    private SharedPreferences.Editor editor;

    public static NotiIO getInstance() {
        if (instance == null) {
            throw new Error("Instance not initialized, use getInstance(Context) instead.");
        }
        return instance;
    }

    public static NotiIO getInstance(Context context) {
        if (instance == null) {
            instance = new NotiIO(context);
        }
        return instance;
    }

    private NotiIO(Context context) {
        this.context = context;
        SharedPreferences prefs = this.context.getSharedPreferences("noti", Context.MODE_PRIVATE);
        String data = prefs.getString("notiList", "[]");
        editor = prefs.edit();
//        editor.putString("notiList", "[]");
//        editor.apply();
        notiList = new NotiArrayList();
        try {
            array = new JSONArray(data);
            for (int i = 0; i < array.length(); ++i) {
                JSONObject object = array.getJSONObject(i);
                System.out.println(object.toString());

                add(Noti.fromJson(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public NotiArrayList getNotiList() {
        return notiList;
    }
    public void add(Noti noti) {

        // notificationId is a unique int for each notification that you must define
        String tag = UUID.randomUUID().toString();

        //Get time before alarm
        String[] time = noti.getTime().split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        long alertTime = getAlertTime(hour, min) - System.currentTimeMillis();

        int random = (int )(Math.random() * 50 + 1);

        //Data
        Data data = createWorkInputData(Constants.TITLE, noti.getDescription(), random);

        NotificationHandler.scheduleReminder(alertTime, data, tag);
        notiList.add(noti);
        update();
    }

    private long getAlertTime(int hour, int min){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private Data createWorkInputData(String title, String text, int id){
        return new Data.Builder()
                .putString(Constants.EXTRA_TITLE, title)
                .putString(Constants.EXTRA_TEXT, text)
                .putInt(Constants.EXTRA_ID, id)
                .build();
    }
    public void remove(Noti noti) {
        notiList.removeIf(n -> n == noti);
        update();
    }
    public void update() {
        editor.putString("notiList", notiList.toJson().toString());
        editor.apply();
    }
}

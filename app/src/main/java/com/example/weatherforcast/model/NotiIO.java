package com.example.weatherforcast.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.weatherforcast.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

                notiList.add(Noti.fromJson(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public NotiArrayList getNotiList() {
        return notiList;
    }
    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent(context, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( context, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }
    public void add(Noti noti) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Thời tiết tổng hợp")
            .setContentText(noti.getDescription())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        scheduleNotification(mBuilder.build(), 5000);
        notiList.add(noti);
        update();
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

package com.example.weatherforcast;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.weatherforcast.model.Noti;
import com.example.weatherforcast.model.NotiIO;
import com.example.weatherforcast.ui.main.TimePickerFragment;

public class AddNotiActivity extends AppCompatActivity {

    private String pickDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noti);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView timeTV = findViewById(R.id.tv_noti_edit_time);
        TextView desc = findViewById(R.id.et_noti_edit_desc);
        findViewById(R.id.btn_time_pick).setOnClickListener(view -> {
            DialogFragment newFragment = new TimePickerFragment(timeTV);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });
        findViewById(R.id.btn_noti_add).setOnClickListener(view -> {
            NotiIO.getInstance().add(new Noti(
                    timeTV.getText().toString(),
                    desc.getText().toString(),
                    true
            ));
            onBackPressed();
        });
    }
}

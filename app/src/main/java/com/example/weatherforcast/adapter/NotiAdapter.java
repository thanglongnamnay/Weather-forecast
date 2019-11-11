package com.example.weatherforcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.weatherforcast.R;
import com.example.weatherforcast.model.Noti;
import com.example.weatherforcast.model.NotiArrayList;
import com.example.weatherforcast.IO.NotiIO;

public class NotiAdapter extends BaseAdapter {
    private NotiArrayList notiList;
    private Context context;

    public NotiAdapter(Context context, NotiArrayList notiList) {
        this.notiList = notiList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notiList.size();
    }

    @Override
    public Object getItem(int position) {
        return notiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotiViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater;
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.noti_row, null);
            holder = new NotiViewHolder(
                    convertView.findViewById(R.id.tv_noti_time),
                    convertView.findViewById(R.id.tv_noti_description),
                    convertView.findViewById(R.id.sw_noti_enable),
                    convertView.findViewById(R.id.btn_noti_remove)
            );

            convertView.setTag(holder);
        } else {
            holder = (NotiViewHolder) convertView.getTag();
        }
        Noti instance = (Noti) getItem(position);
        if (instance != null) {
            holder.time.setText(instance.getTime());
            holder.description.setText(instance.getDescription());
            holder.enable.setChecked(instance.isEnable());
            holder.enable.setOnCheckedChangeListener((buttonView, isChecked) -> {
                instance.setEnable(isChecked);
                NotiIO.getInstance().update();
                notifyDataSetChanged();
            });
            holder.remove.setOnClickListener(view -> {
                NotiIO.getInstance().remove(instance);
                notifyDataSetChanged();
            });
        }

        return convertView;
    }
}

class NotiViewHolder {
    TextView time;
    TextView description;
    Switch enable;
    ImageButton remove;

    public NotiViewHolder(TextView time, TextView description, Switch enable, ImageButton remove) {
        this.time = time;
        this.description = description;
        this.enable = enable;
        this.remove = remove;
    }
}
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class statusAdapter extends BaseAdapter {

    Context c;
    int resorce;
    List<Status> S;

    public statusAdapter(Context c, int resorce) {
        this.c = c;
        this.resorce = resorce;

    }

    public void setS(List<Status> s) {
        S = s;
        notifyDataSetChanged();
    }

    public void addItem(Status s) {


        this.S.add(s);


    }

    @Override
    public int getCount() {
        if (S==null)
            S = new ArrayList<>();
        return S.size();
    }

    @Override
    public Object getItem(int position) {
        return S.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {

            v = LayoutInflater.from(c).inflate(resorce, null, false);

        }

        TextView tv_date = v.findViewById(R.id.date_custom_status_tv);
        TextView tv_weight = v.findViewById(R.id.weight_custom_status_tv);
        TextView tv_statusRate = v.findViewById(R.id.statusValue_custom_status_tv);
        TextView tv_length = v.findViewById(R.id.length_custom_status_tv);

        Status S = (Status) getItem(position);

        tv_date.setText(S.getDate());
        tv_weight.setText(S.getWeight() + " kg");
        tv_statusRate.setText(S.getStatusRate());
        tv_length.setText(S.getLength() + " cm");

        return v;
    }
}
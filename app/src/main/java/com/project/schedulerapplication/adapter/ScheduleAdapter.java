package com.project.schedulerapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.project.schedulerapplication.R;
import com.project.schedulerapplication.beans.Schedules;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private ArrayList<Schedules> mDataList;
    private ScheduleInterface mListener;

    public ScheduleAdapter(ScheduleInterface listner) {
        mDataList = new ArrayList<>();
        mListener = listner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleView;
        public TextView contentView;
        public BootstrapButton deleteBtn;

        public ViewHolder(View v) {
            super(v);

            titleView = (TextView)v.findViewById(R.id.title);
            contentView = (TextView)v.findViewById(R.id.contents);
            deleteBtn = (BootstrapButton)v.findViewById(R.id.btnDelete);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Schedules item = mDataList.get(position);

        holder.titleView.setText(item.title);
        holder.contentView.setText(item.contents);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteScheduleClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addItem(Schedules item) {
        mDataList.add(item);
    }

    public Schedules getItem(int position) {
        return mDataList.get(position);
    }

    public void removeItem(int position) {
        mDataList.remove(position);
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
}

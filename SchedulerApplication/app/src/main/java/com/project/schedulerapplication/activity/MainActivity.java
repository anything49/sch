package com.project.schedulerapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.project.schedulerapplication.R;
import com.project.schedulerapplication.adapter.ScheduleAdapter;
import com.project.schedulerapplication.adapter.ScheduleInterface;
import com.project.schedulerapplication.beans.Schedules;
import com.project.schedulerapplication.db.DatabaseManager;
import com.project.schedulerapplication.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ScheduleInterface {

    CalendarView calendarView;
    DatabaseManager dbManager;

    RecyclerView recyclerView;
    ScheduleAdapter adapter;

    int mYear = 0;
    int mMonth = 0;
    int mDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mYear   = year;
                mMonth = month;
                mDay  = dayOfMonth;

                getScheduleList(mYear + "" + (mMonth + 1) + "" + mDay);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.listview);

        adapter = new ScheduleAdapter(this);
        dbManager = new DatabaseManager(getApplicationContext(), "schedule.db", null, 1);

        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mYear == 0 || mMonth == 0 || mDay == 0) {
            Calendar mcurrentDate = Calendar.getInstance();
            mYear   = mcurrentDate.get(Calendar.YEAR);
            mMonth = mcurrentDate.get(Calendar.MONTH);
            mDay  = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        }

        getScheduleList(mYear + "" + (mMonth + 1) + "" + mDay);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        menu.add("등록").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, AddSchduleActivity.class);
                startActivity(intent);
                return false;
            }
        }).setIcon(R.drawable.ic_event_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onPrepareOptionsMenu(menu);
    }

    private void getScheduleList(String date) {

        adapter.clear();

        Schedules schedules = new Schedules();
        schedules.date = date;

        ArrayList<Schedules> list = dbManager.select(schedules);
        for(int i=0; i<list.size(); i++) {
            adapter.addItem(list.get(i));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteScheduleClicked(int position) {
        Schedules schedules = adapter.getItem(position);
        dbManager.delete(schedules);
        adapter.removeItem(position);
        adapter.notifyDataSetChanged();
    }
}

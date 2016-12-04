package com.project.schedulerapplication.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.project.schedulerapplication.R;
import com.project.schedulerapplication.beans.Schedules;
import com.project.schedulerapplication.db.DatabaseManager;

import java.util.Calendar;

public class AddSchduleActivity extends AppCompatActivity {

    BootstrapButton btnSelectDate;
    BootstrapEditText etTitle;
    BootstrapEditText etContents;

    int mYear = 0;
    int mMonth = 0;
    int mDay = 0;

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schdule);

        dbManager = new DatabaseManager(getApplicationContext(), "schedule.db", null, 1);

        btnSelectDate = (BootstrapButton)findViewById(R.id.btn_select);
        etTitle = (BootstrapEditText)findViewById(R.id.et_title);
        etContents = (BootstrapEditText)findViewById(R.id.et_contents);

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        menu.add("저장").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Auto-generated method stub
                saveSchedule();
                return false;
            }
        }).setIcon(R.drawable.ic_move_to_inbox_white_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void selectDate() {

        Calendar mcurrentDate = Calendar.getInstance();
        int c_day   = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        int c_month = mcurrentDate.get(Calendar.MONTH);
        int c_year  = mcurrentDate.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear + 1;
                mDay = dayOfMonth;

                btnSelectDate.setText(year + "년 " + mMonth + "월 " + mDay + "일");
            }
        }, c_year, c_month, c_day);
        dialog.show();
    }

    private void saveSchedule() {

        String title = etTitle.getEditableText().toString();
        String contents = etContents.getEditableText().toString();

        if(mYear == 0 || mMonth == 0 || mDay == 0) {
            Toast.makeText(AddSchduleActivity.this, "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(title.isEmpty()) {
            Toast.makeText(AddSchduleActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if(contents.isEmpty()) {
            Toast.makeText(AddSchduleActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = mYear + "" + mMonth + "" + mDay;

        Schedules schedules = new Schedules();
        schedules.date = date;
        schedules.title = title;
        schedules.contents = contents;

        dbManager.insert(schedules);
        finish();
    }
}

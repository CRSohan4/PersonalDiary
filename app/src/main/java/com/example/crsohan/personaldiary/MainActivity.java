package com.example.crsohan.personaldiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import Database.DataBaseHandler;
import Database.Diary;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    AutoCompleteTextView actv;
    DataBaseHandler db;
    String mon, day, yea;
    int day1, month1,year1;
    Button showBtn;
    public List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView2);
        actv = findViewById(R.id.actv);
        showBtn = findViewById(R.id.showBtn);

        db = new DataBaseHandler(this);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this,android.R.layout.simple_list_item_1,dates);

        //to get today's day
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-yyyy");
//        LocalDate now = LocalDate.now();

        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        final Date days = new Date();
        day1 = Integer.parseInt(formatter.format(days).toString());

//        Toast.makeText(this, formatter.format(days), Toast.LENGTH_SHORT).show();

        SimpleDateFormat formatter1 = new SimpleDateFormat("MM");
        Date months = new Date();
        month1 = Integer.parseInt(formatter1.format(months).toString());
//        Toast.makeText(this, formatter1.format(months), Toast.LENGTH_SHORT).show();

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy");
        Date years = new Date();
        year1 = Integer.parseInt(formatter2.format(years).toString());
//        Toast.makeText(this, formatter2.format(years), Toast.LENGTH_SHORT).show();


        dates = db.getAllDiariesInArrayFormat();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,dates);
        actv.setAdapter(adapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month ++;

                  if(Integer.parseInt(String.valueOf(year)) <= year1 ) {
                    if(month <= month1  || year < year1) {
                        if (dayOfMonth <= day1  || month < month1  || year < year1) {

                            Intent k = new Intent(MainActivity.this, NewNoteActivity.class);
                            k.putExtra("yea", String.valueOf(year));
                            k.putExtra("mon", String.valueOf(month));
                            k.putExtra("day", String.valueOf(dayOfMonth));
                            startActivity(k);
                        }else{
                            Toast.makeText(MainActivity.this, "Please select valid date.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Please select valid date.", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(MainActivity.this, "Please select valid date.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = actv.getText().toString();
                try {
                    String checkDate;
                    Diary d = db.getDiary(date);
                    checkDate = d.getDate();

                    if (date.equals(checkDate)) {
                        Intent intent = new Intent(MainActivity.this, NewNoteActivityForAutoText.class);
                        intent.putExtra("date", date);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid Date", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.allDiaries:
                Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;

    }

}

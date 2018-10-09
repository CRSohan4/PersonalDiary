package com.example.crsohan.personaldiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DataBaseHandler;
import Database.Diary;

public class DisplayActivity extends AppCompatActivity {

    private static final String tag1 = "asd";

    ArrayList<HashMap<String, String>> detaillist;
    String one;
    ListView listView;
    DataBaseHandler db;
    String date;
    Diary diary;
    List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        db = new DataBaseHandler(this);
        getSupportActionBar().setTitle("All Diaries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listDiary);
        detaillist = new ArrayList<HashMap<String, String>>();

        loadDiaries();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String d = ((TextView) view.findViewById(R.id.textviewresult)).getText().toString();
                Intent intent = new Intent(DisplayActivity.this,NewNoteActivityForAutoText.class);
                intent.putExtra("date",d);
                startActivity(intent);
            }
        });

    }

    public   void loadDiaries()
    {
//        int count = db.getDiaryCounts();
//        for(int i=1;i<=count;i++)
//        {
//            diary = db.getDiaryById(i);
//            date = diary.getDate();
//            HashMap<String, String> detaillist1 = new HashMap<String, String>();
//            detaillist1.put(tag1, date);
//            detaillist.add(detaillist1);
//        }

        dates = db.getAllDiariesInArrayFormat();
        for (String s : dates) {
            HashMap<String,String> detaillist1 = new HashMap<String, String>();
            detaillist1.put(tag1, s);
            detaillist.add(detaillist1);
        }

        if(detaillist.isEmpty()){
            Toast.makeText(this, "No diaries", Toast.LENGTH_SHORT).show();
        }


        ListAdapter adapter1=new SimpleAdapter(this,detaillist,R.layout.list_view_adapter,
                new String[]{tag1},
                new int[]{R.id.textviewresult});
        listView.setAdapter(adapter1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}

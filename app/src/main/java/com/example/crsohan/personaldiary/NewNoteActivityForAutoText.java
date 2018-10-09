package com.example.crsohan.personaldiary;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import Database.DataBaseHandler;
import Database.Diary;

public class NewNoteActivityForAutoText extends AppCompatActivity {

    EditText contentsET;
    String year, month, day, diaryContents, date;
    Button saveBtn;
    DataBaseHandler db;
    Diary diary;
    Spinner dropdown;
    int updateExits = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note_for_auto_text);



        db = new DataBaseHandler(this);



        contentsET = findViewById(R.id.contents);
        saveBtn = findViewById(R.id.saveBtn);

        try {
            date = getIntent().getStringExtra("date");
        }catch (Exception e){}

        //get the spinner from the xml.
        dropdown = findViewById(R.id.spinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"Happy Day", "Not Happy Day", "Normal Day"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        try {
            diary = db.getDiary(date);
            String d = diary.getDate();
            if(d.equals(date)){
                contentsET.setText(diary.getDiaryContents());

                if(diary.getFeeling().equals("Happy Day")) {
                    dropdown.setSelection(0);
                }else if(diary.getFeeling().equals("Not Happy Day")){
                    dropdown.setSelection(1);
                }else if(diary.getFeeling().equals("Normal Day")){
                    dropdown.setSelection(2);
                }

            }

        }catch (Exception e){}



//        if(diary.getDate() == date){
//            contentsET.setText(diary.getDiaryContents());
//        }



        getSupportActionBar().setTitle(date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryContents = contentsET.getText().toString();

                    diary.setDiaryContents(diaryContents);
                    String f = dropdown.getSelectedItem().toString();
                    diary.setFeeling(f);
                    db.updateDiary(diary);
                    Toast.makeText(NewNoteActivityForAutoText.this, "Updated successfully.", Toast.LENGTH_SHORT).show();
                    finish();
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.deleteBtn:
                alertMessage();

        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;

    }

    public void alertMessage(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message);
        builder.setTitle(R.string.title);

        //buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    db.deleteDiary(diary);
                    Toast.makeText(getApplicationContext(), "Deleted successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Nothing to delete.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        //As we touch on the screen the dialog disappers. To avoid that we need to add
        dialog.setCancelable(false);
        dialog.show();
    }


}


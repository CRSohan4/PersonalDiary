package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends SQLiteOpenHelper{
    String diaries;
    static int i = 0;
    public DataBaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DIARY_TABLE = "CREATE TABLE " + Util.TABLE_NAME
                + "(" + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_DATE + " TEXT UNIQUE, " + Util.KEY_CONTENTS
                + " TEXT NOT NULL, " + Util.KEY_FEELING + " TEXT " + ")";

        db.execSQL(CREATE_DIARY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //droping the table regarding versions
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);

        //Create table again
        onCreate(db);

    }

    //CRUD Operations -> Create, Read, Update, Delete

    //add diary
    public void addDiary(Diary diary){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_DATE, diary.getDate());
        values.put(Util.KEY_CONTENTS, diary.getDiaryContents());
        values.put(Util.KEY_FEELING, diary.getFeeling());
        //Insert row
        db.insert(Util.TABLE_NAME, null, values);
        db.close();//close db connection

    }

    //Get a Diary by id
    public Diary getDiaryById(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{
                        Util.KEY_ID, Util.KEY_DATE, Util.KEY_CONTENTS, Util.KEY_FEELING},Util.KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Diary diary = new Diary(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2),cursor.getString(3));
        return diary;
    }

    public Diary getDiary(String date){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{
                        Util.KEY_ID, Util.KEY_DATE, Util.KEY_CONTENTS, Util.KEY_FEELING},
                Util.KEY_DATE + "=?",
                new String[]{String.valueOf(date)},null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
            //todo: testing
//            return null;
        }

        Diary diary = new Diary(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2),cursor.getString(3));
        return diary;
    }

    //get all diaries
    public List<Diary> getAllDiarys(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Diary> diaryList = new ArrayList<>();

        //select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through our contacts
        if(cursor.moveToFirst()){
            do{
                Diary diary = new Diary();
                diary.setId(Integer.parseInt(cursor.getString(0)));
                diary.setDate(cursor.getString(1));
                diary.setDiaryContents(cursor.getString(2));
                diary.setFeeling(cursor.getString(3));

                //add contact object to our contact list
                diaryList.add(diary);

            }while(cursor.moveToNext());
        }

        return diaryList;
    }


    //to get all diaries via dates
    public List<String> getAllDiariesInArrayFormat(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<String> diaryList = new ArrayList<String>();

        //select all contacts
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        //Loop through our contacts
        if(cursor.moveToFirst()){
            do{
//                Diary diary = new Diary();
//                diary.setId(Integer.parseInt(cursor.getString(0)));
//                diary.setDate(cursor.getString(1));
//                diary.setDiaryContents(cursor.getString(2));
//                diary.setFeeling(cursor.getString(3));
                diaries = cursor.getString(1);

                //add contact object to our contact list
                diaryList.add(diaries);

            }while(cursor.moveToNext());
        }

        return diaryList;
    }







    //Update diary
    public int updateDiary(Diary diary){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_DATE, diary.getDate());
        values.put(Util.KEY_CONTENTS, diary.getDiaryContents());
        values.put(Util.KEY_FEELING, diary.getFeeling());

        //update row
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID + "=?",
                new String[]{String.valueOf(diary.getId())});

    }

    //Delete diary
    public void deleteDiary(Diary diary){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME,Util.KEY_ID + "=?",
                new String[]{String.valueOf(diary.getId())});

        db.close();
        //operation same as updateContact method
    }



    //get diary count
    public int getDiaryCounts(){

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        return cursor.getCount();
    }

}


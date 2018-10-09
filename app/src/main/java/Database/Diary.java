package Database;

public class Diary {

    private int id;
    private String date = "";
    private String diaryContents = "";
    private String feeling = "";

    public Diary(int id, String date, String diaryContents, String feeling) {
        this.id = id;
        this.date = date;
        this.diaryContents = diaryContents;
        this.feeling = feeling;
    }

    public Diary(String date, String diaryContents, String feeling) {
        this.date = date;
        this.diaryContents = diaryContents;
        this.feeling = feeling;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public Diary(String date, String diaryContents) {
        this.date = date;
        this.diaryContents = diaryContents;

    }

    public Diary() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiaryContents() {
        return diaryContents;
    }

    public void setDiaryContents(String diaryContents) {
        this.diaryContents = diaryContents;
    }
}


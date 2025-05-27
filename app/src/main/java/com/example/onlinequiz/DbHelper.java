package com.example.onlinequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "OnlineQuiz";
    private static final String REGISTER = "register";
    private static final String QUESTION = "question";
    public static final String ID_NO = "id";
    public static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String UNIVERSITY = "university";
    private static final String DEPARTMENT = "department";
    private static final String QUESTION_ID = "questionId";
    private static final String QUESTIONS_lIST = "myQuestion";
    private static final String CHOICE_ONE = "choiceOne";
    private static final String CHOICE_TWO = "choiceTwo";
    private static final String CHOICE_THREE = "choiceThree";
    private static final String CHOICE_FOUR = "choiceFour";
    private static final String ANSWER = "answer";
    private static final String MARK = "mark";
    Context context;

    public DbHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + REGISTER + "(" + ID_NO + " TEXT PRIMARY KEY," + NAME + " TEXT," + PASSWORD + " TEXT," + UNIVERSITY + " TEXT," + DEPARTMENT + " TEXT)";
        db.execSQL(query);
        String str = "CREATE TABLE " + QUESTION + "(" + QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + QUESTIONS_lIST + " TEXT," + CHOICE_ONE + " TEXT," + CHOICE_TWO + " TEXT," + CHOICE_THREE + " TEXT," + CHOICE_FOUR + " TEXT," + ANSWER + " TEXT)";
        db.execSQL(str);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 8) {
            db.execSQL("ALTER TABLE " + REGISTER + " ADD COLUMN " + MARK + " INTEGER");
        }
    }

    public long studentRegister(String idNo, String name, String pass, String university, String department) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID_NO, idNo);
        cv.put(NAME, name);
        cv.put(PASSWORD, pass);
        cv.put(UNIVERSITY, university);
        cv.put(DEPARTMENT, department);
        long insert = sql.insert(REGISTER, null, cv);
        return insert;
    }


    public boolean checkStudent(String idNo, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor query = db.query(REGISTER, new String[]{ID_NO}, ID_NO + "=? AND " + PASSWORD + "=?", new String[]{idNo, password}, null, null, null);
        boolean exist = query.getCount() > 0;
        return exist;
    }

    public long insertQuestion(String question, String one, String two, String three, String four, String ans) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QUESTIONS_lIST, question);
        cv.put(CHOICE_ONE, one);
        cv.put(CHOICE_TWO, two);
        cv.put(CHOICE_THREE, three);
        cv.put(CHOICE_FOUR, four);
        cv.put(ANSWER, ans);
        long inserted = sql.insert(QUESTION, null, cv);
        return inserted;
    }
    public QuestionModel getQuestionById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QUESTION + " WHERE " + QUESTION_ID + "=?", new String[]{id});
        if (cursor.moveToFirst()) {
            String question = cursor.getString(cursor.getColumnIndexOrThrow(QUESTIONS_lIST));
            String choice1 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_ONE));
            String choice2 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_TWO));
            String choice3 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_THREE));
            String choice4 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_FOUR));
            cursor.close();
            return new QuestionModel(question, choice1, choice2, choice3, choice4);
        } else {
            cursor.close();
            return null;
        }
    }

    public boolean deleteQuestion(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(QUESTION, QUESTION_ID + "=?", new String[]{id});
        return result > 0;
    }

    public QuestionModel displayById(String queId) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QUESTION + " WHERE " + QUESTION_ID + "=?", new String[]{queId});
        if (cursor.moveToFirst()){
            String question = cursor.getString(cursor.getColumnIndexOrThrow(QUESTIONS_lIST));
            String choice1 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_ONE));
            String choice2 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_TWO));
            String choice3 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_THREE));
            String choice4 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_FOUR));
            cursor.close();
            return new QuestionModel(question, choice1, choice2, choice3, choice4);
        }
        else {
            cursor.close();
            return null;
        }
    }
    public QuestionData getQuestionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QUESTION + " WHERE " + QUESTION_ID + " = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            QuestionData q = new QuestionData();
            q.setId(cursor.getInt(0));
            q.setQuestion(cursor.getString(1));
            q.setChoice1(cursor.getString(2));
            q.setChoice2(cursor.getString(3));
            q.setChoice3(cursor.getString(4));
            q.setChoice4(cursor.getString(5));
            q.setAnswer(cursor.getString(6));
            cursor.close();
            return q;
        }
        return null;
    }
    public boolean updateQuestion(int id, String question, String c1, String c2, String c3, String c4, String ans) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTIONS_lIST, question);
        values.put(CHOICE_ONE, c1);
        values.put(CHOICE_TWO, c2);
        values.put(CHOICE_THREE, c3);
        values.put(CHOICE_FOUR, c4);
        values.put(ANSWER, ans);

        int rows = db.update(QUESTION, values, QUESTION_ID + "=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public Cursor getQuestion(int qId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + QUESTION + " WHERE " + QUESTION_ID + " = ?", new String[]{String.valueOf(qId)});
    }
    public int getQuestionCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM "+ QUESTION , null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    public String getCorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + ANSWER + " FROM "  + QUESTION + " WHERE " + QUESTION_ID + " = ?", new String[]{String.valueOf(questionId)});
        String correctAnswer = null;
        if (cursor.moveToFirst()) {
            correctAnswer = cursor.getString(0);
        }
        cursor.close();
        return correctAnswer;
    }

    public List<Integer> getAllQuestionIds() {
        List<Integer> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + QUESTION_ID + " FROM " + QUESTION, null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ids;
    }
    public List<QuizItem> getAllQuestions() {
        List<QuizItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QUESTION, null); // Make sure QUESTION is your table name

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(QUESTION_ID));
                String question = cursor.getString(cursor.getColumnIndexOrThrow(QUESTIONS_lIST));
                String c1 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_ONE));
                String c2 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_TWO));
                String c3 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_THREE));
                String c4 = cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_FOUR));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow(ANSWER));

                list.add(new QuizItem(id, question, c1, c2, c3, c4,answer));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }


    public void updateStudentMark(String studentId, int mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MARK", mark);

        db.update(REGISTER, values, ID_NO + " = ?", new String[]{studentId});
        db.close();
    }

    public Cursor getStudentsWithMarks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + ID_NO + ", " + NAME + ", " + DEPARTMENT + ", " + MARK + " FROM " + REGISTER, null);
    }
    public QuizItem getQuestionByIdd(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(QUESTION, null, QUESTION_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            QuizItem item = new QuizItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(QUESTION_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(QUESTIONS_lIST)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_ONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_TWO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_THREE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(CHOICE_FOUR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ANSWER)));
            cursor.close();
            return item;
        }
        return null;
    }

    public int updateQuestions(QuizItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QUESTIONS_lIST, item.getQuestion());
        values.put(CHOICE_ONE, item.getChoice1());
        values.put(CHOICE_TWO, item.getChoice2());
        values.put(CHOICE_THREE, item.getChoice3());
        values.put(CHOICE_FOUR, item.getChoice4()); // If QuizItem has 4 options
        values.put(ANSWER, item.getAnswer());
        return db.update(QUESTION, values, QUESTION_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

}
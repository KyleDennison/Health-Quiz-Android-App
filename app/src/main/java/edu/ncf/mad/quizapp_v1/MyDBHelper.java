package edu.ncf.mad.quizapp_v1;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MyDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuizData";

    private static final String TABLE_QUIZ_QUESTIONS = "QuizQuestions";
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTIONS = "questions";
    private static final String KEY_OPTIONS = "options";
    private static final String KEY_CORRECT_ANSWER = "correct_answer";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_DIFFICULTY = "difficulty";

    private static final String TABLE_OPTIONS = "quiz_options";
    private static final String KEY_OPTION_1 = "quiz_option_1";
    private static final String KEY_OPTION_2 = "quiz_option_2";
    private static final String KEY_OPTION_3 = "quiz_option_3";
    private static final String KEY_OPTION_4 = "quiz_option_4";
    private static final String KEY_OPTION_5 = "quiz_option_5";
    private static final String KEY_OPTION_6 = "quiz_option_6";

    private static final String TABLE_ANSWERS = "quiz_answers";
    private static final String KEY_ANSWER_1 = "quiz_answer_1";
    private static final String KEY_ANSWER_2 = "quiz_answer_2";
    private static final String KEY_ANSWER_3 = "quiz_answer_3";
    private static final String KEY_ANSWER_4 = "quiz_answer_4";
    private static final String KEY_ANSWER_5 = "quiz_answer_5";
    private static final String KEY_ANSWER_6 = "quiz_answer_6";

    private static final String TABLE_SCORES = "quiz_scores";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";

    public int IDcounter;
//        List<Contact> contactList = new ArrayList<Contact>();
//        Contact temp;
//        double count = 0.5;

    Context c;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUIZ_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUIZ_QUESTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUESTIONS
                + " TEXT," + KEY_OPTIONS + " INTEGER," + KEY_CORRECT_ANSWER + " INTEGER," + KEY_PICTURE + " INTEGER," + KEY_DIFFICULTY + " TEXT," + "unique (" + KEY_QUESTIONS + "," + KEY_OPTIONS + "," + KEY_CORRECT_ANSWER + "," + KEY_PICTURE + "," + KEY_DIFFICULTY + ") ON CONFLICT REPLACE)";
        db.execSQL(CREATE_QUIZ_QUESTIONS_TABLE);

        String CREATE_OPTIONS_TABLE = "CREATE TABLE " + TABLE_OPTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OPTION_1
                + " TEXT," + KEY_OPTION_2 + " TEXT," + KEY_OPTION_3 + " TEXT," + KEY_OPTION_4 + " TEXT," + KEY_OPTION_5 + " TEXT," + KEY_OPTION_6 + " TEXT," + "unique (" + KEY_OPTION_1 + "," + KEY_OPTION_2 + "," + KEY_OPTION_3 + "," + KEY_OPTION_4 + "," + KEY_OPTION_5 + "," + KEY_OPTION_6 + ") ON CONFLICT REPLACE)";
        db.execSQL(CREATE_OPTIONS_TABLE);

        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME +
                 " TEXT," + KEY_SCORE+ " INTEGER," + KEY_DIFFICULTY + " TEXT," + "unique (" + KEY_NAME + "," + KEY_SCORE + "," + KEY_DIFFICULTY+ ") ON CONFLICT REPLACE)";
        db.execSQL(CREATE_SCORES_TABLE);


        /*
        String CREATE_ANSWER_TABLE = "CREATE TABLE " + TABLE_ANSWERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ANSWER_1
                + " INTEGER," + KEY_ANSWER_2 + " INTEGER," + KEY_ANSWER_3 + " INTEGER," + KEY_ANSWER_4 + " INTEGER," + KEY_ANSWER_5 + " INTEGER," + KEY_ANSWER_6 + " INTEGER," + "unique (" + KEY_ANSWER_1 + "," + KEY_ANSWER_2 + "," + KEY_ANSWER_3 + "," + KEY_ANSWER_4 + "," + KEY_ANSWER_5 + "," + KEY_ANSWER_6 + ") ON CONFLICT REPLACE)";
        db.execSQL(CREATE_ANSWER_TABLE);
        System.out.println("Answer table created");
        */

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_QUESTIONS);
        /*
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        */
        onCreate(db);
    }



    public void addQuestion(MultipleQuestion question) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] options = question.getOptions();
        ArrayList<ArrayList<String>> answers = question.getAnswers();
        String aTemp = "";
        for(int i = 0; i < answers.size(); i++){
            //aTemp = aTemp + answers[i] + ",";
        }
        /*
        String vOption = "";
        for (int i = 0; i < options.length; i++) {
            vOption = vOption + options[i] + ",";
        }
        */
        /*
        int[] answers = question.getAnswers();
        String vAnswer = "";
        for (int i = 0; i < answers.length; i++) {
            vAnswer = vAnswer + answers[i] + ",";
        }
        */
        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_QUIZ_QUESTIONS);
        if(checkTable(TABLE_QUIZ_QUESTIONS, question.getQuestion())){System.out.println("q already in table");}
        else {
            //System.out.println("question added");
            ContentValues values = new ContentValues();
            question.setID((int) numRows);
            //System.out.println("counter is " + numRows);
            //values.put(KEY_ID, IDcounter);
            values.put(KEY_ID, question.getID());
            values.put(KEY_QUESTIONS, question.getQuestion());
            values.put(KEY_OPTIONS, question.getID());
            values.put(KEY_CORRECT_ANSWER,  aTemp);
            values.put(KEY_PICTURE, question.getPicture());
            values.put(KEY_DIFFICULTY, question.getDifficulty());
            db.insert(TABLE_QUIZ_QUESTIONS, null, values);
            //System.out.println("added " + question.getID() + " " + question.getDifficulty() +  " to table with answers " + aTemp);


            int counter = 0;
            ContentValues values2 = new ContentValues();
            values2.put(KEY_ID, question.getID());
            for (int i = 0; i < options.length; i++) {
                values2.put("quiz_option_" + (i + 1), options[i]);
                counter++;
            }
            for (int i = counter; i < 6; i++) {
                values2.put("quiz_option_" + (i + 1), "null");
            }

                db.insert(TABLE_OPTIONS, null, values2);

            /*
            int counter2 = 0;
            ContentValues values3 = new ContentValues();
            values3.put(KEY_ID, question.getID());
            for (int i = 0; i < answers.length; i++) {
                values3.put("quiz_answer_" + (i + 1), (Integer) answers[i]);
                counter2++;
                //System.out.println("added" + answers[i] + " quiz_answer_" + (i + 1)+ "to answers table");
            }
            for (int i = counter2; i < 6; i++) {
                values3.put("quiz_answer_" + (i + 1), 9);
                //System.out.println("added" + 9 + " quiz_answer_" + (i + 1)+ "to answers table");
            }

            db.insert(TABLE_ANSWERS, null, values3);
                */

        }
            db.close();

    }

    public MultipleQuestion[] getAllQuestions(String diff) {
        String selectQuery = "SELECT * FROM " + TABLE_QUIZ_QUESTIONS + " WHERE difficulty=" + "'" + diff + "'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<MultipleQuestion> questionList = new ArrayList<MultipleQuestion>();

        if (cursor.moveToFirst()) {
            do {
                MultipleQuestion q = new MultipleQuestion(99999, "temp", new String[5], new ArrayList<ArrayList<String>>(), new byte[5], "easy", "blank");
                q.setID(Integer.parseInt(cursor.getString(0)));
                q.setQuestion(cursor.getString(1));

                ArrayList<String> temp = new ArrayList<>();
                Integer id = cursor.getInt(2);
                String query = "SELECT * FROM " + TABLE_OPTIONS + " WHERE id=" + id + ";";
                Cursor c = db.rawQuery(query, null);
                //Cursor c = db.query(TABLE_OPTIONS, new String[]{KEY_OPTION_1, KEY_OPTION_2, KEY_OPTION_3, KEY_OPTION_4, KEY_OPTION_5, KEY_OPTION_6},"id=?", new String[id],null, null,null );
                if (c.moveToFirst()) {
                    do {
                        temp.add(c.getString(1));
                        //System.out.println("option 1" + c.getString(1));
                        temp.add(c.getString(2));
                        //System.out.println("option 2" + c.getString(2));
                        temp.add(c.getString(3));
                        //System.out.println("option 3" + c.getString(3));
                        temp.add(c.getString(4));
                        //System.out.println("option 4" + c.getString(4));
                        temp.add(c.getString(5));
                       // System.out.println("option 5" + c.getString(5));
                        temp.add(c.getString(6));
                        //System.out.println("option 6" + c.getString(6));
                    }
                    while (c.moveToNext());}
                q.setOptions(temp.toArray(new String[temp.size()]));

                /*
                ArrayList<Integer> ans = new ArrayList<>();
                Integer idAns = cursor.getInt(3);
                String queryAns = "SELECT * FROM " + TABLE_ANSWERS + " WHERE id=" +  "'" + idAns + "'"+ ";";
                Cursor cAns = db.rawQuery(queryAns, null);
                //Cursor c = db.query(TABLE_OPTIONS, new String[]{KEY_OPTION_1, KEY_OPTION_2, KEY_OPTION_3, KEY_OPTION_4, KEY_OPTION_5, KEY_OPTION_6},"id=?", new String[id],null, null,null );
                if (cAns.moveToFirst()) {
                    do {
                        //System.out.println("answer 0" + c.getString(0));
                        ans.add(cAns.getInt(1));
                        ///System.out.println("answer 1" + cAns.getString(1));
                        ans.add(cAns.getInt(2));
                        //System.out.println("answer 2" + cAns.getString(2));
                        ans.add(cAns.getInt(3));
                        //System.out.println("answer 3" + cAns.getString(3));
                        ans.add(cAns.getInt(4));
                       //.out.println("answer4" + cAns.getString(4));
                        ans.add(cAns.getInt(5));
                        //System.out.println("answer 5" + cAns.getString(5));
                        ans.add(cAns.getInt(6));
                        //System.out.println("answer 6" + cAns.getString(6));
                    }
                    while (cAns.moveToNext());}
                    int[] a = new int[6];
                    for(int i = 0; i < ans.size(); i++){a[i] = ans.get(i);}
                    q.setAnswers(a);
                    */
                String[] x = cursor.getString(3).split(",");
                int[] answers = new int[x.length];
                for(int i = 0; i < x.length; i++){answers[i] = Integer.parseInt(x[i]);}
                //q.setAnswers(answers);
                //q.setPicture(Integer.parseInt(cursor.getString(4)));
                q.setDifficulty(cursor.getString(5));
                questionList.add(q);
            }
            while (cursor.moveToNext());
        }
        db.close();
        MultipleQuestion[] x = questionList.toArray(new MultipleQuestion[questionList.size()]);
        //System.out.println("size is " + x.length);
        return x;
    }


    public void addScore(String name, int score, String difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        long numRows = DatabaseUtils.queryNumEntries(db, TABLE_SCORES);
        ContentValues values = new ContentValues();
        values.put(KEY_ID, (int) numRows);
        values.put(KEY_NAME, name);
        values.put(KEY_SCORE, score);
        values.put(KEY_DIFFICULTY, difficulty);
        db.insert(TABLE_SCORES, null, values);
        //System.out.println("added " + numRows +  " to score table with name " + name + " and score" + score);
        db.close();
    }

    public String getAllScores(String difficuty) {
        String selectQuery = "SELECT * FROM " + TABLE_SCORES + " WHERE difficulty=" + "'" + difficuty + "'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String scores = "";

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                System.out.println("name is " + name);
                String score = cursor.getString(2);
                System.out.println("score is " + score);
                scores = scores  + name + ":" + score + ",";

            }
            while (cursor.moveToNext());
        }
        db.close();
        return scores;
    }


    public void deleteQuestion(String key){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUIZ_QUESTIONS, KEY_ID + "=?", new String[]{key});
    }

    public boolean checkTable(String table, String question) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String query = "SELECT * FROM " + table + " WHERE questions=" + "'" + question+ "'" + ";";
            Cursor c = db.rawQuery(query, null);

            if (c.moveToFirst())
            {
                c.close();
                return true;
            }
            else
            {
                c.close();
                return false;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }



}

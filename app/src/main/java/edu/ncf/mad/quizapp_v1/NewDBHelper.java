package edu.ncf.mad.quizapp_v1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class NewDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "questions.db";
    private static final int DATABASE_VERSION = 1;

    public NewDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MultipleQuestion[] getAllQuestions(String diff) {
        int search = 0;
        if(diff.equals("easy")){search = 1;}
        else{search = 3;}
        //String selectQuery = "SELECT * FROM questions WHERE difficulty=" + "'" + diff + "'" + ";";
        String selectQuery = "SELECT * FROM questions WHERE difficulty=" + "'" + search + "'" + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<MultipleQuestion> questionList = new ArrayList<MultipleQuestion>();

        if (cursor.moveToFirst()) {
            do {
                String[] options = new String[]{"option1", "option2"};
                ArrayList<ArrayList<String>> t = new ArrayList<ArrayList<String>>();
                int[] ans = new int[]{1, 2};
                MultipleQuestion q = new MultipleQuestion(99999, "temp", options, t, new byte[5], "easy", "blank");
                int id = Integer.parseInt(cursor.getString(0));
                q.setID(id);
              //  System.out.println(id + " " + cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
               // System.out.println(id + " " + cursor.getString(1));
                int diffID = cursor.getInt(2);
                String difficulty = "";
                if(diffID == 3){difficulty = "Hard"; }
                else{difficulty = "Easy"; }
                q.setDifficulty(difficulty);
               // System.out.println(id + " diff " + difficulty);
                byte[] img = cursor.getBlob(3);
                System.out.println(id + "  img " + cursor.getBlob(3));
                //System.out.println(img);
                q.setPicture(img);
                //System.out.println(img.length);

                String explanation = cursor.getString(4);
               // System.out.println(id + "  expla " + cursor.getString(4));
                q.setExplanation(explanation);

                q.setNumAnswers(cursor.getInt(5));
               // System.out.println(id + " numans " + cursor.getInt(5));
                q.setType(cursor.getString(6));
               // System.out.println(id + " type " + cursor.getString(6));
                q.setNumCorrect(cursor.getInt(7));
                //System.out.println(id + " Num Correct " + cursor.getInt(7));


                String selectQuery2 = "SELECT * FROM answers WHERE question_id=" + "'" + id + "'" + ";";;
                Cursor cursor2 = db.rawQuery(selectQuery2, null);
                ArrayList<ArrayList<String>> AnswersList = new ArrayList<>();
                if (cursor2.moveToFirst()) {
                    do {
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(cursor2.getString(2));
                        //System.out.println(id + " answer id is " + cursor2.getString(0));
                        //System.out.println(id + " answer qid is " + cursor2.getString(1));
                        //System.out.println(id + " answer text is " + cursor2.getString(2));
                        temp.add(cursor2.getString(3));
                        //System.out.println(id + " answer correctness is " + cursor2.getString(3));
                        AnswersList.add(temp);
                    }
                    while (cursor2.moveToNext());}
                    q.setAnswers(AnswersList);
                //for(ArrayList a : AnswersList){System.out.println(a.get(0) + " " + a.get(1));}


                questionList.add(q);
            }
            while (cursor.moveToNext());
        }
        db.close();
        MultipleQuestion[] x = questionList.toArray(new MultipleQuestion[questionList.size()]);
        //System.out.println("size is " + x.length);
        return x;
    }
}

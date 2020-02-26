package edu.ncf.mad.quizapp_v1;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
    Context c;
    String difficulty = "";
    public Parser(Context co) throws IOException {
        c = co;
    }
    public String LoadData(String inFile, String pDifficulty) {
        difficulty = pDifficulty;
        String tContents = "";

        try {
            InputStream stream = c.getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

    public ArrayList<String> sortData(String input){
        ArrayList<String> output = new ArrayList<String>();
        input = input.substring(input.indexOf("["), input.lastIndexOf("]"));
        while(input.length() > 1){
            String temp;
            int start = input.indexOf("{");
            int end = input.indexOf("}");
            output.add(input.substring(start, end + 1));
            //System.out.println(input.substring(start, end +1 ));
            input = input.substring(end + 1);
            //System.out.println(input + " " + input.length());
        }
        return output;
    }

    public MultipleQuestion[] makeQuestions(ArrayList<String> input){
        MultipleQuestion[] output = new MultipleQuestion[50];
        ArrayList<MultipleQuestion> out = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < input.size(); i++){
            String currentSelected = input.get(i);

            String question = currentSelected.substring(currentSelected.indexOf("Q") + 11);
            currentSelected = question;
            question = question.substring(0, question.indexOf('"' ));
            //System.out.println(question);


            String possibleOptions = currentSelected.substring(currentSelected.indexOf("[") + 1, currentSelected.indexOf("]"));
            currentSelected = currentSelected.substring(currentSelected.indexOf("]") + 1);
            //System.out.println(currentSelected);
            String[] options = possibleOptions.split(",");

            String Answers = currentSelected.substring(currentSelected.indexOf("[") + 1, currentSelected.indexOf("]"));
            String[] answers = Answers.split(",");
            ArrayList<Integer> temp = new ArrayList<>();
            for(int t = 0; t < answers.length; t++){
                if(answers[t] != ""){
                temp.add( Integer.parseInt(answers[t]));}
            }
            Integer[] realAnswer = temp.toArray(new Integer[temp.size()]);
            int[] x = new int[realAnswer.length];
            for (int t = 0;t < realAnswer.length; t++ ){
                x[t] = realAnswer[t];
                //System.out.println("answer is " + x[t]);
            }


            //output[i] = new MultipleQuestion(counter,question, options, x );
            int picture = 0;
            if(counter % 2 == 0){picture = R.drawable.fish;}
            else{picture = R.drawable.notebook;}

           // out.add(new MultipleQuestion(counter,question, options, x,new byte[5], difficulty, "blank" ));
            counter++;


        }
        output = out.toArray(new MultipleQuestion[out.size()]);
        return output;
    }

}




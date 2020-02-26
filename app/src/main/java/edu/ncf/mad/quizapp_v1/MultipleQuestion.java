package edu.ncf.mad.quizapp_v1;

import java.util.ArrayList;

public class MultipleQuestion {
    private String mQuestion;
    private String[] mOptions;
    private ArrayList<ArrayList<String>> mAnswers;
    private int mID;
    private byte[] mPicture;
    private String mDifficulty;
    private String mExplanation;
    private int mNumAnswers = 0;
    private int mNumCorrect = 0;
    private String mType = "";

    public MultipleQuestion(int pID, String pQuestion, String[] pOptions, ArrayList<ArrayList<String>> pAnswers, byte[] pPicture, String pDifficulty, String pExplanation) {
        mID = pID;
        mQuestion = pQuestion;
        mOptions = pOptions;
        mAnswers = pAnswers;
        mPicture = pPicture;
        mDifficulty = pDifficulty;
        mExplanation = pExplanation;
    }

    public String getQuestion() {
        return mQuestion;
    }
    public void setQuestion(String pQuestion) {
        mQuestion = pQuestion;
    }
    public ArrayList<ArrayList<String>> getAnswers() {
        return mAnswers;
    }
    public void setAnswers(ArrayList<ArrayList<String>> pAnswers) {
        mAnswers = pAnswers;
    }

    public String[] getOptions() {
        return mOptions;
    }
    public void setOptions(String[] pOptions) {
        mOptions = pOptions;
    }

    public int getID() {
        return mID;
    }
    public void setID(int pID) {
        mID=pID;
    }

    public byte[] getPicture(){return mPicture;}
    public void setPicture(byte[] pPicture){mPicture = pPicture;}

    public String getDifficulty(){return mDifficulty;}
    public void setDifficulty(String pDifficulty){mDifficulty = pDifficulty;}

    public String getExplanation(){return mExplanation;}
    public void setExplanation(String pExplanation){mExplanation = pExplanation;}

    public int getNumAnswers(){return mNumAnswers;}
    public void setNumAnswers(int pNumAnswers){mNumAnswers = pNumAnswers;}

    public String getType(){return mType;}
    public void setType(String pType){mType = pType;}

    public int getNumCorrect(){return mNumCorrect;}
    public void setNumCorrect(int pNumCorrect){mNumCorrect = pNumCorrect;}

}

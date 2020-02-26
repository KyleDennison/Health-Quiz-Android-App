package edu.ncf.mad.quizapp_v1;

public class Question {
    private int mQuestionResourceID;
    private int mAnswerResourceID;
    public Question(int questionResourceID, int answerResourceID) {
        mQuestionResourceID = questionResourceID;
        mAnswerResourceID = answerResourceID;
    }
    public int getQuestionResourceID() {
        return mQuestionResourceID;
    }
    public void setQuestionResourceID(int questionResourceID) {
        mQuestionResourceID = questionResourceID;
    }
    public int getAnswerResourceID() {
        return mAnswerResourceID;
    }
    public void setAnswerResourceID(int answerResourceID) {
        mAnswerResourceID = answerResourceID;
    }
}

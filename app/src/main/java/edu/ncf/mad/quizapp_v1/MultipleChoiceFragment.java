package edu.ncf.mad.quizapp_v1;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChoiceFragment extends Fragment {

    Button mBtnSubmit;
    RadioGroup mRadioGroup;
    List<CompoundButton> mBtns;
    ImageView mImage;
    TextView tvResults;
    Resources mRes;
    TextView mTxtQuestion;
    TextView tvCorrectness;

    int mCurrentIndex;
    public static String difficulty = "";
    public static String correctness = "";
    ArrayList<ArrayList<String>> mAnswers; // array of correct answers
    TextView currentQuestionNum;
    int questionNumCounter = 1;
    TextView totalQuestionNum;

    String data;
    public ArrayList<Integer> selected = new ArrayList<>();

    MyDBHelper db;
    NewDBHelper newDB;
    public static int score = 0;
    public static int count;
    public MultipleQuestion[] mQuestionBank;
    public static int bankSize;

    Button exitQuiz;
    TextView points;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_multiple_choice, container, false);

        mRes = getResources();

        ((MainActivity) getActivity()).loadProgress();

        db = new MyDBHelper(getContext());
        newDB = new NewDBHelper(getContext());

        Parser p = null;

        {
            try {
                p = new Parser(getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (DifficultyFragment.isEasy == true) {
            data = p.LoadData("easyQuestions.txt", "easy");
            difficulty = "easy";
        } else {
            data = p.LoadData("mediumQuestions.txt", "medium");
            difficulty = "hard";
        }
        ArrayList<String> data2 = p.sortData(data);
        MultipleQuestion[] questions = p.makeQuestions(data2);
        for (MultipleQuestion q : questions) {
            db.addQuestion(q);
        }

        MultipleQuestion[] temp = new MultipleQuestion[]{new MultipleQuestion(100, "Click next to begin", new String[]{}, new ArrayList<ArrayList<String>>(), new byte[5], "easy", "blank"), new MultipleQuestion(100, "Click next to begin", new String[]{}, new ArrayList<ArrayList<String>>(), new byte[5], "easy", "blank")};
        mQuestionBank = temp;
        GetQuestionsAsyncTask getQuestionsAsyncTask = new GetQuestionsAsyncTask();
        getQuestionsAsyncTask.execute();


        points = rootView.findViewById(R.id.points);
        exitQuiz = rootView.findViewById(R.id.exit_quiz);
        mBtnSubmit = (Button) rootView.findViewById(R.id.button_submit_mc_answer);
        mTxtQuestion = (TextView) rootView.findViewById(R.id.textViewMCQuestion);
        mImage = (ImageView) rootView.findViewById(R.id.imgHolder);
        mRadioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        currentQuestionNum = rootView.findViewById(R.id.currentQuestionNumber);
        totalQuestionNum = rootView.findViewById(R.id.totalQuestionNumber);
        tvResults = (TextView) rootView.findViewById(R.id.tvResults);
        tvCorrectness = (TextView) rootView.findViewById(R.id.tvCorrectness);

        points.setText("0");

        //totalQuestionNum.setText(String.valueOf(mQuestionBank.length));
        currentQuestionNum.setText(String.valueOf(questionNumCounter));

        exitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });


        setClickForSubmitBtn();

        return rootView;
    }

    public void updateQuestion() {
        currentQuestionNum.setText(String.valueOf(questionNumCounter));
        String vQuestion = mQuestionBank[mCurrentIndex].getQuestion();
        //String[] vChoices = mQuestionBank[mCurrentIndex].getOptions();
        mAnswers = mQuestionBank[mCurrentIndex].getAnswers();

        //mImage.setImageResource(mQuestionBank[mCurrentIndex].getPicture());
        //System.out.println(mQuestionBank[mCurrentIndex].getPicture());
        byte[] myPic = mQuestionBank[mCurrentIndex].getPicture();
        Bitmap b = BitmapFactory.decodeByteArray(myPic, 0, myPic.length);
        mImage.setImageBitmap(b);
        mImage.setImageResource(R.drawable.fish);


        // at this point
        // vQuestion --->id of the string for the question (dont need text because setText will take id too)
        // vChoices--> text for choices
        // vAnswers int array of correct answers

        // set the question
        mTxtQuestion.setText(vQuestion);
        // add the correct number of buttons
        mRadioGroup.removeAllViews();
        mBtns = new ArrayList();
        for (int i = 0; i < mAnswers.size(); i++) {
            if (mQuestionBank[mCurrentIndex].getNumCorrect() > 1) { // multiple correct answers
                System.out.println("There are Multiple Answers!");
                mBtns.add(new CheckBox(getContext()));
            } else {
                mBtns.add(new RadioButton(getContext()));
            }


            //for radio button color
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{

                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled} //enabled
                    },
                    new int[]{

                            R.color.colorPrimaryDark //disabled
                            , R.color.colorPrimaryDark //enabled

                    }
            );

            mBtns.get(i).setText(mAnswers.get(i).get(0));
            mBtns.get(i).setTextColor(getResources().getColor(R.color.colorPrimary));
            mBtns.get(i).setTextSize(17);
            mBtns.get(i).setButtonTintList(colorStateList);
            mRadioGroup.addView(mBtns.get(i));


        }
    }

    private void submitQuestion() {

        ((MainActivity) getActivity()).loadProgress();
        //increment # of questions sumbitted to know when to end quiz
        count++;
        questionNumCounter++;

        if (isCurrentSelectionCorrect()) {
            if (difficulty.equals("easy")) {
                score = score+ 10;

            } else {
                score = score + 20;
            }
            points.setText(String.valueOf(score));
            //Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
            correctness = "CORRECT";
        } else {
            //Toast.makeText(getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
            correctness = "INCORRECT";
        }
    }

    private boolean isCurrentSelectionCorrect() {
        List<Integer> vSelections = new ArrayList();
        for (int i = 0; i < mBtns.size(); i++) {
            if (mBtns.get(i).isChecked()) {
                vSelections.add(i);
                selected.add(i);
                //.print("Selected" + i);
            }
        }

        System.out.println("There are " + vSelections.size() + " answers");
        for (int i : vSelections) {
            //System.out.println(i);
            //System.out.println("text"+mAnswers.get(i).get(1)+"text");
            if (mAnswers.get(i).get(1).equals("*")) ;
            else {
                return false;
            }
        }

        return true;
    }

    private class GetQuestionsAsyncTask extends android.os.AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            getQuestions();
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            updateQuestion();
            bankSize = mQuestionBank.length;
            totalQuestionNum.setText(String.valueOf(mQuestionBank.length));
        }
    }

    public void getQuestions() {
        MultipleQuestion[] temp = newDB.getAllQuestions(difficulty);
        MultipleQuestion[] switcher = new MultipleQuestion[10];
        for (int i = 0; i < 10; i++) {
            switcher[i] = temp[i];
        }
        mQuestionBank = switcher;
    }

    public void replaceFragment(Fragment someFragment) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void feedback() {

        mImage.setVisibility(View.INVISIBLE);

        // at this point
        // vQuestion --->id of the string for the question (dont need text because setText will take id too)
        // vChoices--> text for choices
        // vAnswers int array of correct answers

        // set the question
        mTxtQuestion.setText("");
        // add the correct number of buttons
        mRadioGroup.removeAllViews();
        for (int i = 0; i < mAnswers.size(); i++) {
            mBtns.get(i).setText(mAnswers.get(i).get(0));
            if (mAnswers.get(i).get(1).equals("*")) {
                mBtns.get(i).setTextColor(getResources().getColor(R.color.green));
            } else {
                mBtns.get(i).setTextColor(getResources().getColor(R.color.red));
            }
            mRadioGroup.addView(mBtns.get(i));
        }

        tvCorrectness.setText(correctness);
        String options = "";
        String answers = "";
        String[] temp = mQuestionBank[mCurrentIndex].getOptions();
        //int[] temp2 = mQuestionBank[mCurrentIndex].getAnswers();
        //for(Integer i : selected){options = options + temp[i] + ",";}
        //for(int i : temp2){answers = answers + temp2[i] + ",";}
        String result = "You selected " + options + " the right answer is " + answers;
        tvResults.setText(mQuestionBank[mCurrentIndex].getExplanation());

        mBtnSubmit.setText("ok");
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (count < mQuestionBank.length) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    mImage.setVisibility(View.VISIBLE);
                    updateQuestion();
                    mBtnSubmit.setText("Submit Answer");
                    setClickForSubmitBtn();
                    tvResults.setText("");
                    selected.clear();
                    tvCorrectness.setText("");
                } else {
                    if (UserFragment.name.equals("")) {
                        Fragment fragment = new ResultsFragment();
                        replaceFragment(fragment);
                    } else {
                        db.addScore(UserFragment.name, score, difficulty);
                        System.out.println("going to add a score");
                        Fragment fragment = new ResultsFragment();
                        replaceFragment(fragment);
                    }
                }
            }
        });

    }

    public void setClickForSubmitBtn() {

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuestion();
                feedback();
            }
        });
    }
}


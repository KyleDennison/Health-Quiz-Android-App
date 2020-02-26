package edu.ncf.mad.quizapp_v1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ResultsFragment extends Fragment {

    TextView score;
    Button backHomeBtn;
    TextView mTotal;
    MyDBHelper helper;
    TextView tvScores;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        score = rootView.findViewById(R.id.quizScoreTxt);
        backHomeBtn = rootView.findViewById(R.id.backHomeBtn);
        mTotal = rootView.findViewById(R.id.totalQ);
        tvScores = rootView.findViewById(R.id.tvScores);

        ((MainActivity)getActivity()).resetProgressBar();

        score.setText(String.valueOf(MultipleChoiceFragment.score));
        mTotal.setText("Out of " + MultipleChoiceFragment.bankSize );

        helper = new MyDBHelper(getContext());
        String s = helper.getAllScores(MultipleChoiceFragment.difficulty);
        String[] scores = s.split(",");
        s = "";
        for(String string : scores){s = s + string + System.lineSeparator();}
        tvScores.setText("TOP SCORES" + System.lineSeparator() + s);

        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipleChoiceFragment.score = 0;
                MultipleChoiceFragment.count = 0;
                Fragment fragment = new UserFragment();
                replaceFragment(fragment);
            }
        });


        return rootView;
    }

    public void replaceFragment(Fragment someFragment) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}

package edu.ncf.mad.quizapp_v1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DifficultyFragment extends Fragment {

    Button easyBtn;
    Button mediumBtn;
    public static boolean isEasy;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_difficulty, container, false);

        ((MainActivity)getActivity()).hideBottomNav();

        easyBtn = rootView.findViewById(R.id.easyBtn);
        mediumBtn = rootView.findViewById(R.id.mediumBtn);

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
                isEasy = true;
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
                isEasy = false;
            }
        });


        return rootView;
    }

    public void replaceFragment () {
        Fragment fragment = new MultipleChoiceFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

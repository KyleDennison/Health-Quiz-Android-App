package edu.ncf.mad.quizapp_v1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    public ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        onBackPressed();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new UserFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    @Override
    public void onBackPressed() {
        resetProgressBar();
        MultipleChoiceFragment.score = 0;
        MultipleChoiceFragment.count = 0;
        Fragment fragment = new UserFragment();
        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment someFragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void hideBottomNav() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNav() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void loadProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(mProgressStatus);
        mProgressStatus = mProgressStatus + 10;
    }

    public void resetProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mProgressStatus = 0;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new UserFragment();
                            break;

                        case R.id.nav_books:
                            selectedFragment = new TextBooksFragment();
                            break;

                        case R.id.nav_cards:
                            selectedFragment = new FlashCards();
                            break;
                    }

                    replaceFragment(selectedFragment);

                    return true;
                }
            };

}


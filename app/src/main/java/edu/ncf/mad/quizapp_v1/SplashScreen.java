package edu.ncf.mad.quizapp_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    startActivity(new Intent(SplashScreen.this, MainActivity.class));

                finish();
            }
        }, 1000);
    }
}
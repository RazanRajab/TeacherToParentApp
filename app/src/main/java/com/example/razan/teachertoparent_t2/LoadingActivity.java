package com.example.razan.teachertoparent_t2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private CountDownTimer mCountDownTimer;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar = findViewById(R.id.progressBar);
       // progressBar.setVisibility(View.GONE);

        progressBar.setProgress(i);
        mCountDownTimer=new CountDownTimer(1200,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                progressBar.setProgress(i*100/(1200/1000));
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                progressBar.setProgress(100);
                LoadingActivity.this.finish();
                Intent n =new Intent(getApplicationContext(),MyChats.class);
                startActivity(n);
            }
        };
        mCountDownTimer.start();
          /*  try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

    }
}

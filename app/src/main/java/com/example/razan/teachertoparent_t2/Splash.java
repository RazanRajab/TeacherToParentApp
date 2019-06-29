package com.example.razan.teachertoparent_t2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends Activity {
    LinearLayout l1;
    ImageView l2;
    Animation uptodown,downtoup;

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        l1 = (LinearLayout) findViewById(R.id.l1);
        TextView t2= (TextView)findViewById(R.id.t) ;
        // Typeface f= Typeface.createFromAsset(getAssets(),"fonts/Dancing Script.ttf");
        // t2.setTypeface(f);

        l2 = (ImageView) findViewById(R.id.iconimg);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l2.setAnimation(uptodown);
        l1.setAnimation(downtoup);

        /* New Handler to start the Login-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Login-Activity. */

                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}

package com.gurusankar149.bitbank10th;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    private TextView app_name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        app_name=findViewById(R.id.app_name);
        mAuth=FirebaseAuth.getInstance();
        Database.db= FirebaseFirestore.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
     overridePendingTransition(0,0);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.app_name_animation);
        app_name.setAnimation(animation);
        new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (user!=null)
                {
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            SplashActivity.this.finish();
                }
                else{
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                SplashActivity.this.finish();}
            }
        }.start();
    }
}
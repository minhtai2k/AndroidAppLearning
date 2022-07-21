package com.example.itelishver3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Thread bg = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                }catch (Exception e){}
                finally {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        startActivity(new Intent(IntroActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                        finish();
                    }

                }
            }
        };
        bg.start();
    }
}
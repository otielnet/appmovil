package ec.gescom.gescom.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ec.gescom.gescom.MainActivityMenu;
import ec.gescom.gescom.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent=new Intent(SplashActivity.this, MainActivityMenu.class);
              startActivity(intent);
              finish();
            }
        },3000);

    }
}

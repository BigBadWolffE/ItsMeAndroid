package com.indocyber.itsmeandroid.view.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.services.firebase.MyFireBaseMessagingService;
import com.indocyber.itsmeandroid.utilities.Preference;
import com.indocyber.itsmeandroid.view.home.activity.HomeActivity;
import com.indocyber.itsmeandroid.view.login.LoginOptionActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private Preference preference;
    protected boolean _active = true;
    protected int _splashTime = 3000;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Window window = getWindow();
        MyFireBaseMessagingService.getToken(this);
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorwhite));

        preference = new Preference(this);
        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    if (preference.getLogin()) {
                        finish();
                        Intent newIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        startActivityForResult(newIntent, 0);
                    }else {
                        finish();
                        Intent newIntent = new Intent(SplashScreenActivity.this, LoginOptionActivity.class);
                        startActivityForResult(newIntent, 0);
                    }
                }
            }
        };
        splashTread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear();
        editor.commit();
    }
}

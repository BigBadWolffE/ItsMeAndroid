package com.indocyber.itsmeandroid.view.addcc;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.indocyber.itsmeandroid.R;


/**
 * @author Muhammad Faisal
 * @version 1.0
 */
public class AddCcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cc);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarText = findViewById(R.id.txtToolbar);
        toolbarText.setText("Add Credit Card");
        toolbar.setNavigationIcon();
    }
}

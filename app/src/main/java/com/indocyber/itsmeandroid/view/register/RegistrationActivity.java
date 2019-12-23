package com.indocyber.itsmeandroid.view.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.indocyber.itsmeandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private String idQuestion;
    private String idAnswer;
    private String nameQuestion;
    private String nameAnswer;
    private Spinner mSpnrQuestion;
    private Spinner mSpnrAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Registration");
            getSupportActionBar().setElevation(0f);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mSpnrQuestion = findViewById(R.id.spnrQuestion);
        mSpnrAnswer = findViewById(R.id.spnrAnswer);

        setSpinnerAnswer();
        setSpinnerQuestion();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSpinnerQuestion(){
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            /*String[] idSpinner = {"1", "2", "3"};
            String[] nameSpinner = {"Donasi Kemanusiaan", "Donasi Pendidikan", "Donasi Kesehatan"};*/

            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("id", "1");
            hm.put("level_name", "What are your hobby?");
            listSpinner.add(hm);
            /*for (int i = 0; i < 3; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }*/
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrQuestion.setAdapter(adapter);
            mSpnrQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                    String id = hm.get("id");
                    String level_name = hm.get("level_name");
                   /* typeId = id;
                    typeName = level_name;*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpinnerAnswer(){
        try {
            List<HashMap<String, String>> listSpinner = new ArrayList<HashMap<String, String>>();

            /*String[] idSpinner = {"1", "2", "3"};
            String[] nameSpinner = {"Donasi Kemanusiaan", "Donasi Pendidikan", "Donasi Kesehatan"};*/

            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("id", "1");
            hm.put("level_name", "Playing futsal");
            listSpinner.add(hm);
            /*for (int i = 0; i < 3; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("id", idSpinner[i]);
                hm.put("level_name", nameSpinner[i]);
                listSpinner.add(hm);
            }*/
            String[] from = {"id", "level_name"};
            int[] to = {R.id.id_spinner, R.id.nama_spinner};
            SimpleAdapter adapter = new SimpleAdapter(this, listSpinner, R.layout.layout_spinner, from, to);
            mSpnrAnswer.setAdapter(adapter);
            mSpnrAnswer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idLong) {
                    HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                    String id = hm.get("id");
                    String level_name = hm.get("level_name");
                   /* typeId = id;
                    typeName = level_name;*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

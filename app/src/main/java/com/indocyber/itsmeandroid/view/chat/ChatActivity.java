package com.indocyber.itsmeandroid.view.chat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indocyber.itsmeandroid.R;
import com.indocyber.itsmeandroid.model.Message;
import com.indocyber.itsmeandroid.view.chat.adapter.AdapterChat;

import static com.indocyber.itsmeandroid.utilities.UtilitiesCore.getFormattedTimeEvent;

public class ChatActivity extends AppCompatActivity {
    private EditText edtxtContent;
    private AdapterChat adapter;
    private RecyclerView recycleChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initToolbar();
        iniComponent();

    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setTitle(null);
        }
    }

    public void iniComponent() {
        recycleChat = findViewById(R.id.recycleChat);
        ImageButton imgBtnBack = findViewById(R.id.lyt_back);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleChat.setLayoutManager(layoutManager);
        recycleChat.setHasFixedSize(true);

        adapter = new AdapterChat(this);
        recycleChat.setAdapter(adapter);
        adapter.insertItem(new Message(adapter.getItemCount(), "Hai..", false, adapter.getItemCount() % 5 == 0, getFormattedTimeEvent(System.currentTimeMillis())));
        adapter.insertItem(new Message(adapter.getItemCount(), "Hello!", true, adapter.getItemCount() % 5 == 0, getFormattedTimeEvent(System.currentTimeMillis())));

        FloatingActionButton btnSend = findViewById(R.id.btn_send);
        edtxtContent = findViewById(R.id.textContent);
        btnSend.setOnClickListener(view -> sendChat());

        imgBtnBack.setOnClickListener(v -> finish());
        //(findViewById(R.id.imgBtnBack)).bringToFront();
        //(findViewById(R.id.imgBtnBack)).setOnClickListener(v -> finish());
    }

    private void sendChat() {
        final String msg = edtxtContent.getText().toString();
        if (msg.isEmpty()) return;
        adapter.insertItem(new Message(adapter.getItemCount(), msg, true, adapter.getItemCount() % 5 == 0, getFormattedTimeEvent(System.currentTimeMillis())));
        edtxtContent.setText("");
        recycleChat.scrollToPosition(adapter.getItemCount() - 1);
        new Handler().postDelayed(() -> {
            adapter.insertItem(new Message(adapter.getItemCount(), msg, false, adapter.getItemCount() % 5 == 0, getFormattedTimeEvent(System.currentTimeMillis())));
            recycleChat.scrollToPosition(adapter.getItemCount() - 1);
        }, 1000);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

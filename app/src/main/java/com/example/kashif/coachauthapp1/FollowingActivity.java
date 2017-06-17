package com.example.kashif.coachauthapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class FollowingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.following_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedItemId = item.getItemId();

        switch (selectedItemId) {
            case R.id.action_add_following: {
                Intent followeringIntent = new Intent(FollowingActivity.this,ABCD.class);
                startActivity(followeringIntent);

            }
        }

        return super.onOptionsItemSelected(item);

    }
}
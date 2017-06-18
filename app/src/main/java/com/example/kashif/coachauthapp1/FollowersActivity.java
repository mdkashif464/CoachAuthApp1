package com.example.kashif.coachauthapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class FollowersActivity extends AppCompatActivity {


    private RecyclerView followersListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);


        followersListRecyclerView =(RecyclerView)findViewById(R.id.following_recycler_listView);
        followersListRecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}

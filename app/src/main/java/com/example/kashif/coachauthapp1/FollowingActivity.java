package com.example.kashif.coachauthapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FollowingActivity extends AppCompatActivity {

    private RecyclerView followingListRecyclerView;

    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails/"+currentUser.getUid()+"/MyFollowing");

        followingListRecyclerView =(RecyclerView)findViewById(R.id.following_recycler_listView);
        followingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerAdapter<UserModel, FollowerViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UserModel, FollowerViewHolder>
                        (UserModel.class, R.layout.all_followers_list_recycler_view, FollowerViewHolder.class, databaseReference) {

                    @Override
                    protected void populateViewHolder(final FollowerViewHolder viewHolder, UserModel model, int position) {

                        if (model.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            // previouslyFollowing.setVisibility(View.INVISIBLE);
                        }

                        viewHolder.setUserProfileImage(model.getProfileImageUrl());
                        viewHolder.setName(model.getName());


                    }
                };
        followingListRecyclerView.setAdapter(firebaseRecyclerAdapter);

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
                Intent followingIntent = new Intent(FollowingActivity.this,FollowPeopleActivity.class);
                startActivity(followingIntent);

            }
        }
        return super.onOptionsItemSelected(item);

    }
}
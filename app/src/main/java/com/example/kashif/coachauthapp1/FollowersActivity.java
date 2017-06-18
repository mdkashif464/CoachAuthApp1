package com.example.kashif.coachauthapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FollowersActivity extends AppCompatActivity {


    private RecyclerView followersListRecyclerView;
    private DatabaseReference databaseReference;
    ImageView previouslyFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        previouslyFollowing = (ImageView)findViewById(R.id.previously_following_iv);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails");

        followersListRecyclerView =(RecyclerView)findViewById(R.id.follower_recycler_listView);
        followersListRecyclerView.setLayoutManager(new LinearLayoutManager(this));




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
        followersListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}

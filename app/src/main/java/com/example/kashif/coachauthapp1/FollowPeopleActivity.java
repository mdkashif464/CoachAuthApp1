package com.example.kashif.coachauthapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FollowPeopleActivity extends AppCompatActivity {


    private SearchView allUsersListSearchView;
    private Button allUsersSearchButton;
    private RecyclerView allUsersListRecyclerView;

    private DatabaseReference mdatabaseReference;

    private FirebaseUser user;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_people);


        allUsersListSearchView = (SearchView) findViewById(R.id.all_users_searchiew);
        allUsersSearchButton = (Button) findViewById(R.id.all_users_search_button);
        allUsersListRecyclerView = (RecyclerView) findViewById(R.id.all_user_list_recyclerview);



        allUsersListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails");


        allUsersSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryFromUser = String.valueOf(allUsersListSearchView.getQuery());
                searchForUsersMatchingQuery(queryFromUser);
            }
        });
    }


    public void searchForUsersMatchingQuery(String queryFromUser){
        Query query = mdatabaseReference.orderByChild("Name").startAt(queryFromUser).limitToFirst(10);

        FirebaseRecyclerAdapter<UserModel, AllUserListViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UserModel, AllUserListViewHolder>(UserModel.class, R.layout.all_users_list_recyclerview_layout, AllUserListViewHolder.class, query) {

            @Override
            protected void populateViewHolder(final AllUserListViewHolder viewHolder, UserModel model, int position) {

                if (model.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    viewHolder.followUserButton.setText("It's you");
                    viewHolder.followUserButton.setClickable(false);
                }

                viewHolder.setUserProfileImage(model.getProfileImageUrl());
                viewHolder.setName(model.getName());

            }
        };
        allUsersListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}

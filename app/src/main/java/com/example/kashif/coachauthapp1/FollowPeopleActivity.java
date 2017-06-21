package com.example.kashif.coachauthapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_people);


        allUsersListSearchView = (SearchView) findViewById(R.id.all_users_searchiew);
        allUsersListSearchView.setIconifiedByDefault(false);
      //  allUsersSearchButton = (Button) findViewById(R.id.all_users_search_button);
        allUsersListRecyclerView = (RecyclerView) findViewById(R.id.all_user_list_recyclerview);

        allUsersListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();






       allUsersListSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               if (newText.length() >= 2){
                   String userQuery = newText.substring(0,1).toUpperCase() + newText.substring(1);
                   allUsersListRecyclerView.removeAllViews();
                   searchForUsersMatchingQuery(userQuery);
               }
               return false;
           }
       });









        /*allUsersSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryFromUser = String.valueOf(allUsersListSearchView.getQuery());
                allUsersListRecyclerView.removeAllViews();
                searchForUsersMatchingQuery(queryFromUser);
            }
        });*/
    }


    public void searchForUsersMatchingQuery(String queryFromUser){
        Query query = mdatabaseReference.orderByChild("Name").startAt(queryFromUser).endAt(queryFromUser+"\uf8ff");

        FirebaseRecyclerAdapter<UserModel, AllUserListViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UserModel, AllUserListViewHolder>(UserModel.class, R.layout.all_users_list_recyclerview_layout, AllUserListViewHolder.class, query) {

                    @Override
                    protected void populateViewHolder(AllUserListViewHolder viewHolder, final UserModel model, int position) {


                        if (model.getUniqueUserId().equals(currentUser.getUid())){
                            viewHolder.followUserButton.setVisibility(View.GONE);
                        }

                        viewHolder.setUserProfileImage(model.getProfileImageUrl());
                        viewHolder.setName(model.getName());
                        viewHolder.followUserButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"Followed "+model.getName(),Toast.LENGTH_SHORT).show();
                                addUserToMyFollowingList(currentUser.getUid(), model.getUniqueUserId());
                            }
                        });
                    }
                };

        allUsersListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public void addUserToMyFollowingList(String currentUserUniqueId, String selectedUserUniqueId){
        mdatabaseReference.child(currentUserUniqueId+"/MyFollowing").child(selectedUserUniqueId).setValue(selectedUserUniqueId);
        mdatabaseReference.child(selectedUserUniqueId+"/MyFollowers").child(currentUserUniqueId).setValue(currentUserUniqueId);

    }

}
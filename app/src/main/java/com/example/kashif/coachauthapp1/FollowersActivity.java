package com.example.kashif.coachauthapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.value;

public class FollowersActivity extends AppCompatActivity {


    private RecyclerView followersListRecyclerView;
    private DatabaseReference databaseReference;

    ArrayList<String> Array_followers_name;

    private FirebaseUser currentUser;

    FollowerAdapter followerAdapter;

    ImageView previouslyFollowing;


    ArrayList<FollowersModel> all_followers_model_Arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        previouslyFollowing = (ImageView) findViewById(R.id.previously_following_iv);
        all_followers_model_Arraylist =new ArrayList<FollowersModel>();
        followerAdapter = new FollowerAdapter();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Array_followers_name = new ArrayList<>();
        followersListRecyclerView = (RecyclerView) findViewById(R.id.follower_recycler_listView);
        followersListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//this is final  array list

        databaseReference.child("allUsersDetails/" + currentUser.getUid() + "/MyFollowers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ankur","Data changed called1");

                Map<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();

                int i = 0;
                if(map == null){
                    Toast.makeText(getApplicationContext(),"No Followers",Toast.LENGTH_SHORT).show();
                }
                else {
                    for (String key : map.keySet()) {
                        Log.d("result", "Value is: " + key);
                        Array_followers_name.add(key);
                        i++;
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("result", "Failed to read value.", error.toException());
            }
        });
        databaseReference.child("allUsersDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ankur","Data changed called");
                for (int j = 0; j < (Array_followers_name.size()); j++) {
                    String user_name = dataSnapshot.child(Array_followers_name.get(j) + "/Name").getValue(String.class);
                    String user_image_url = dataSnapshot.child(Array_followers_name.get(j) + "/ProfileImageUrl").getValue(String.class);
                    all_followers_model_Arraylist.add(j,new FollowersModel(user_name,user_image_url));
                    followerAdapter.getList(all_followers_model_Arraylist);



                    Log.d("result1", "Value is " + all_followers_model_Arraylist.size());
                    Toast.makeText(FollowersActivity.this,"ans is"+all_followers_model_Arraylist.size(),Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("result", "Failed to read value.", error.toException());
            }
        });

        followersListRecyclerView = (RecyclerView) findViewById(R.id.follower_recycler_listView);
        followersListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        followersListRecyclerView.setAdapter(followerAdapter);


    }
}

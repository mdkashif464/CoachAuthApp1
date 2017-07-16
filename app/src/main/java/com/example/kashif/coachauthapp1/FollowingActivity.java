package com.example.kashif.coachauthapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
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

public class FollowingActivity extends AppCompatActivity {


    ArrayList<FollowingModel> all_followers_model_Arraylist;






    private RecyclerView followingListRecyclerView;

    ArrayList<String> Array_followers_name;

    DatabaseReference databaseReference;

    private FirebaseUser currentUser;

    FollowingAdapter followingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Array_followers_name = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        all_followers_model_Arraylist =new ArrayList<FollowingModel>();
        followingAdapter = new FollowingAdapter();

        followingListRecyclerView =(RecyclerView)findViewById(R.id.following_recycler_listView);
        followingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.child("allUsersDetails/" + currentUser.getUid() + "/MyFollowing").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ankur","Data changed called1");

                Map<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
//this is final  array list

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
                    all_followers_model_Arraylist.add(j,new FollowingModel(user_name,user_image_url));
                   // followingAdapter.getList(all_followers_model_Arraylist);



                    Log.d("ankur", "Value is " + all_followers_model_Arraylist.size());
                    Toast.makeText(FollowingActivity.this,"ans is"+all_followers_model_Arraylist.size(),Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("result", "Failed to read value.", error.toException());
            }
        });
        followingAdapter.getList(all_followers_model_Arraylist);
        followingListRecyclerView.setAdapter(followingAdapter);


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
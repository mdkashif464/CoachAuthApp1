package com.example.kashif.coachauthapp1;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PersonProfile extends AppCompatActivity {


    private String username;
    private String usermail;
    private String userimageurl;
    private String uniqueUserId;

    private TextView username_tv;
    private TextView usermail_tv;
    private ImageView userimage_iv;
    private Button followers_bt;
    private Button following_bt;

    private DatabaseReference databaseReference;
    private DatabaseReference currentUserDatabaseReference;

    private RecyclerView userLIstRecyclerView;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);


        username_tv = (TextView) findViewById(R.id.user_profile_name_tv);
        usermail_tv = (TextView) findViewById(R.id.user_profile_email_tv);
        userimage_iv = (ImageView) findViewById(R.id.profile_image);
        followers_bt = (Button)findViewById(R.id.profile_following_bt);
        following_bt = (Button)findViewById(R.id.profile_following_bt);


//        userLIstRecyclerView =(RecyclerView)findViewById(R.id.user_list_recycler_view);
//        userLIstRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails");


        Bundle Bundle = getIntent().getExtras();
        if (Bundle != null) {
            username = Bundle.getString("username");
            uniqueUserId = Bundle.getString("uniqueregid");
            usermail = Bundle.getString("usermail");
            userimageurl = Bundle.getString("userimageurl");
        }


        getSupportActionBar().setTitle("WELCOME " + username);


        username_tv.append(username);
        usermail_tv.append(usermail);
        Picasso.with(PersonProfile.this)
                .load(userimageurl)
                .into(userimage_iv);

        HashMap<String, Object> userDetails = new HashMap<>();
        userDetails.put("Name", username);
        userDetails.put("Email", usermail);
        userDetails.put("ProfileImageUrl", userimageurl);


         databaseReference.child(uniqueUserId).setValue(userDetails);


//        Query Q = databaseReference.orderByChild("Name").startAt("Ay").limitToFirst(10);
//
//        final FirebaseRecyclerAdapter<UserModel,AllUserListViewHolder> firebaseRecyclerAdapter = new
//                FirebaseRecyclerAdapter<UserModel, AllUserListViewHolder>(    UserModel.class,
//                        R.layout.user_list_model_layout,
//                        AllUserListViewHolder.class,
//                        Q) {
//
//
//                    @Override
//                    protected void populateViewHolder(AllUserListViewHolder viewHolder, UserModel model, int position) {
//
//                        if (model.getEmail().equals(usermail)){
//                            viewHolder.setTitle(model.getName());
//                            viewHolder.userMail_tv.setVisibility(View.GONE);
//                        }
//                        else {
//                            viewHolder.setTitle(model.getName());
//                            viewHolder.setDesc(model.getEmail());
//                        }
//                    }
//                };
//
//        userLIstRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }
        currentUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        currentUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String skills = dataSnapshot.child("Skills").getValue(String.class);
                String achievements = dataSnapshot.child("Achievements").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        followers_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersIntent = new Intent(PersonProfile.this,FollowingActivity.class);
                startActivity(followersIntent);
            }
        });

        following_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followeringIntent = new Intent(PersonProfile.this,FollowingActivity.class);
                startActivity(followeringIntent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedItemId = item.getItemId();

        switch (selectedItemId) {
            case R.id.action_logout: {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goToLoginScreen();
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


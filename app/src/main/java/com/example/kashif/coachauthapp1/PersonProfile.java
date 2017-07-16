package com.example.kashif.coachauthapp1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PersonProfile extends AppCompatActivity {


    private String username;
    private String usermail;
    private String userimageurl;
    private String uniqueUserId;
    private String selected_primary_skill;
    private String selected_secondary_skill;
    private String shared_preference_uniqueUserId;
    String user_name;
    String user_mail;
    String user_unique_id;
    String user_dob;
    String user_role;
    String user_current_city ;
    String user_batting_style;
    String user_bowling_style;
    String skills;
    String achievements;

    Uri user_image_url;

    private TextView username_tv;
    private TextView usermail_tv;
    private TextView user_skills_tv;
    private TextView user_achievement_tv;
    private TextView user_role_tv;
    private TextView user_dob_tv;
    private TextView user_batting_style_tv;
    private TextView user_bowling_style_tv;
    private TextView user_current_city_tv;
    private  TextView user_follower_no_tv;
    private TextView user_following_no_tv;


    private Button followers_bt;
    private Button following_bt;

    SharedPreferences sharedPreferences;

    private DatabaseReference databaseReference;
    private DatabaseReference currentUserDatabaseReference;
    FollowerAdapter followerAdapter;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);


        username_tv = (TextView) findViewById(R.id.user_profile_name_tv);
        usermail_tv = (TextView) findViewById(R.id.user_profile_email_tv);
        user_skills_tv = (TextView) findViewById(R.id.user_profile_skill_tv);
        user_achievement_tv = (TextView) findViewById(R.id.user_profile_achievements_tv);
        user_dob_tv = (TextView) findViewById(R.id.user_profile_dob_tv);
        user_current_city_tv = (TextView) findViewById(R.id.user_profile_address_city_tv);
        user_role_tv = (TextView) findViewById(R.id.user_profile_role__tv);
        user_batting_style_tv = (TextView) findViewById(R.id.user_batting_style__tv);
        user_bowling_style_tv = (TextView) findViewById(R.id.user_bowling_style__tv);
        user_follower_no_tv =(TextView)findViewById(R.id.user_follower_no_tv);
        user_following_no_tv =(TextView)findViewById(R.id.user_following_no_tv);
        followerAdapter = new FollowerAdapter();
        followerAdapter.getItemCount();
        Log.d("ankur","size is"+followerAdapter.getItemCount());

        followers_bt = (Button) findViewById(R.id.profile_followers_bt);
        following_bt = (Button) findViewById(R.id.profile_following_button);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails");
        user = FirebaseAuth.getInstance().getCurrentUser();

        user_name = user.getDisplayName();
        user_mail = user.getEmail();
        user_image_url = user.getPhotoUrl();
        user_unique_id= user.getUid();


        final CircularImageView userimage_iv = (CircularImageView) findViewById(R.id.user_profile_imageview);

        userimage_iv.setBorderColor(getResources().getColor(R.color.com_facebook_button_background_color));
        userimage_iv.setBorderWidth(10);
        userimage_iv.setShadowRadius(1);


        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Bundle Bundle = getIntent().getExtras();
        if (Bundle != null) {
            username = Bundle.getString("username");
            uniqueUserId = Bundle.getString("uniqueregid");
            usermail = Bundle.getString("usermail");
            userimageurl = Bundle.getString("userimageurl");

        }
        editor.putString("uniqueUserId",uniqueUserId);
        editor.commit();
        SharedPreferences settings = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        shared_preference_uniqueUserId = settings.getString("uniqueUserId", "null");
        Log.d("ankur", "result is id " + FirebaseAuth.getInstance().getCurrentUser().getUid());

        Toast.makeText(getApplicationContext(),"uniqueId "+uniqueUserId,Toast.LENGTH_SHORT).show();
        Bundle UserEditProfileActivityBundle = getIntent().getExtras();
        if (Bundle != null) {
            selected_primary_skill = UserEditProfileActivityBundle.getString("selcted_primary_skill");
            selected_secondary_skill = UserEditProfileActivityBundle.getString("selected_secondary_skill");

        }


        getSupportActionBar().setTitle("WELCOME " + user_name);


        username_tv.setText(user_name);
        usermail_tv.setText(user_mail);
        Picasso.with(PersonProfile.this)
                .load(user_image_url)
                .into(userimage_iv);

        final HashMap<String, Object> userDetails = new HashMap<>();
        userDetails.put("UniqueUserId", user_unique_id);
        userDetails.put("Name", user_name);
        userDetails.put("Email", user_mail);
        userDetails.put("ProfileImageUrl", user_image_url);
//        Log.d("ankur",uniqueUserId);


        databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    databaseReference.child(uniqueUserId).setValue(userDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        currentUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails/" + user.getUid());

        currentUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long user_follower_no =dataSnapshot.child("MyFollowers").getChildrenCount();
                long user_following_no = dataSnapshot.child("MyFollowing").getChildrenCount();
                String user_name  = dataSnapshot.child("Name").getValue(String.class);
                String user_image_url = dataSnapshot.child("ProfileImageUrl").getValue(String.class);
                String user_email = dataSnapshot.child("Email").getValue(String.class);
                user_dob = dataSnapshot.child("user_dob").getValue(String.class);
                user_role = dataSnapshot.child("user_role").getValue(String.class);
                user_current_city = dataSnapshot.child("user_address_city").getValue(String.class);
                user_batting_style = dataSnapshot.child("user_batting_hand").getValue(String.class);
                user_bowling_style = dataSnapshot.child("user_bowling_hand").getValue(String.class);
                skills = dataSnapshot.child("user_skills").getValue(String.class);
                achievements = dataSnapshot.child("user_achievement").getValue(String.class);
                user_follower_no_tv.setText(""+user_follower_no);
                user_following_no_tv.setText(""+user_following_no);
                user_dob_tv.setText(user_dob);
                user_role_tv.setText(user_role);
                username_tv.setText(user_name);
                usermail_tv.setText(user_email);
                Picasso.with(PersonProfile.this)
                        .load(user_image_url)
                        .into(userimage_iv);

                user_current_city_tv.setText(user_current_city);
                user_batting_style_tv.setText(user_batting_style);
                user_bowling_style_tv.setText(user_bowling_style);
                user_skills_tv.setText(skills);
                user_achievement_tv.setText(achievements);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        followers_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followersIntent = new Intent(PersonProfile.this, FollowersActivity.class);
                startActivity(followersIntent);
            }
        });


        following_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent followingIntent = new Intent(PersonProfile.this, FollowingActivity.class);
                startActivity(followingIntent);
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
            case R.id.user_profile_edit: {
                Intent edit_your_profile_intent = new Intent(PersonProfile.this, UserEditProfileActivity.class);
                edit_your_profile_intent.putExtra("username", user_name);
                edit_your_profile_intent.putExtra("usermail", user_mail);
                edit_your_profile_intent.putExtra("userimageurl", user_image_url.toString());
                Log.d("ankur",user_image_url.toString());

                edit_your_profile_intent.putExtra("uniqueUserId", user_unique_id);
                edit_your_profile_intent.putExtra("user_dob",user_dob);
                edit_your_profile_intent.putExtra("user_role",user_role);
                edit_your_profile_intent.putExtra("user_skills",skills);
                edit_your_profile_intent.putExtra("user_achievements",achievements);
                edit_your_profile_intent.putExtra("user_current_city",user_current_city);
                startActivity(edit_your_profile_intent);
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
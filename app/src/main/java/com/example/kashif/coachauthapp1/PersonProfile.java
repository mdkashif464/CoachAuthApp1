package com.example.kashif.coachauthapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class PersonProfile extends AppCompatActivity {
    String username;
    String usermail;
    String userimageurl;
    TextView username_tv;
    TextView usermail_tv;
    ImageView userimage_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);

        username_tv = (TextView)findViewById(R.id.username_tv);
        usermail_tv = (TextView)findViewById(R.id.usermail_tv);
        userimage_iv = (ImageView) findViewById(R.id.user_image_iv);


        Bundle Bundle= getIntent().getExtras();
        if (Bundle!=null){
            username = Bundle.getString("username");
            usermail = Bundle.getString("usermail");
            userimageurl = Bundle.getString("userimageurl");
        }
        getSupportActionBar().setTitle("WELCOME "+username);

        username_tv.append(username);
        usermail_tv.setText(usermail);
        Picasso.with(PersonProfile.this)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .placeholder(R.mipmap.ic_launcher)
                .resize(150, 150)
                .centerCrop()
                .into(userimage_iv);

    }
}


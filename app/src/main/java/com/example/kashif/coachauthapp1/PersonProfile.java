package com.example.kashif.coachauthapp1;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
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
        FacebookSdk.sdkInitialize(this.getApplicationContext());
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
        usermail_tv.append(usermail);
        Picasso.with(PersonProfile.this)
                .load(userimageurl)
                .placeholder(R.mipmap.ic_launcher)
                .resize(150, 150)
                .centerInside()
                .into(userimage_iv);


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

        switch (selectedItemId){
            case R.id.action_logout :{
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                goToLoginScreen();
                break;
            }
        }

        return super.onOptionsItemSelected(item);

    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


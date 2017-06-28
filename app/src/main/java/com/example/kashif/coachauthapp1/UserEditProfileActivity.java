package com.example.kashif.coachauthapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UserEditProfileActivity extends AppCompatActivity {

    private String username;
    private String usermail;
    private String userimageurl;
    private String user_skills;
    private String user_achievement;
    private String user_dob;
    private String user_address_city;
    private String user_batting_hand;
    private String user_bowling_hand;
    private String user_wicketkeeper;
    String selcted_primary_skill;
    String selected_secondary_skill;
    String user_role;
    int selected_primary_item_position;
    int selected_secondary_item_position;

    String unique_user_Id;


    private TextView user_name_tv;
    private ImageView user_image_iv;
    private TextView user_email_tv;
    private TextView user_batting_style_tv;
    private TextView user_bowling_style_tv;
    private EditText user_dob_et;
    private EditText user_address_city_et;
    private EditText user_address_state_et;
    private EditText user_skill_et;
    private EditText user_achievement_et;

    private Button user_credential_save_button;

    private Spinner user_primary_skill_spinner;
    private Spinner user_secondary_skill_spinner;
    private Spinner user_batting_skill_spinner;
    private Spinner user_bowling_skill_spinner;

    private ArrayAdapter<CharSequence> user_primary_skill_adapter;
    private ArrayAdapter<CharSequence> user_secondary_skill_adapter;
    private ArrayAdapter<CharSequence> user_batting_skill_adapter;
    private ArrayAdapter<CharSequence> user_bowling_skill_adapter;

    private HashMap<String, Object> user_edit_profile_details;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("allUsersDetails");


        user_name_tv = (TextView) findViewById(R.id.user_name_tv);
        user_email_tv = (TextView) findViewById(R.id.user_email_tv);
        user_batting_style_tv = (TextView) findViewById(R.id.user_batting_style__tv);
        user_bowling_style_tv = (TextView) findViewById(R.id.user_bowling_style__tv);

        user_image_iv = (ImageView) findViewById(R.id.user_image_iv);


        user_dob_et = (EditText) findViewById(R.id.user_dob_et);
        user_address_city_et = (EditText) findViewById(R.id.user_address_city_et);
        user_address_state_et = (EditText) findViewById(R.id.user_address_state_et);
        user_skill_et = (EditText) findViewById(R.id.user_skills_et);
        user_achievement_et = (EditText) findViewById(R.id.user_achievement_et);

        user_credential_save_button = (Button) findViewById(R.id.submit_btn);


        user_primary_skill_spinner = (Spinner) findViewById(R.id.user_skill_set_primary_spinner);
        user_secondary_skill_spinner = (Spinner) findViewById(R.id.user_skill_set_secondary_spinner);
        user_batting_skill_spinner = (Spinner) findViewById(R.id.user_batsman_skill_set_spinner);
        user_bowling_skill_spinner = (Spinner) findViewById(R.id.user_bowler_skill_set_spinner);


        Bundle Bundle = getIntent().getExtras();
        if (Bundle != null) {
            username = Bundle.getString("username");
            usermail = Bundle.getString("usermail");
            userimageurl = Bundle.getString("userimageurl");
            unique_user_Id = Bundle.getString("uniqueUserId");
        }

        Picasso.with(UserEditProfileActivity.this)
                .load(userimageurl)
                .into(user_image_iv);


        user_name_tv.setText(username);
        user_email_tv.setText(usermail);


        user_primary_skill_adapter = ArrayAdapter.createFromResource(this, R.array.user_primary_skill_set_array, android.R.layout.simple_spinner_item);
        user_primary_skill_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_primary_skill_spinner.setAdapter(user_primary_skill_adapter);

        user_secondary_skill_adapter = ArrayAdapter.createFromResource(this, R.array.user_secondary_skill_set_array, android.R.layout.simple_spinner_item);
        user_secondary_skill_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_secondary_skill_spinner.setAdapter(user_secondary_skill_adapter);

        user_batting_skill_adapter = ArrayAdapter.createFromResource(this, R.array.user_batting_skill_set_array, android.R.layout.simple_spinner_item);
        user_batting_skill_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_batting_skill_spinner.setAdapter(user_batting_skill_adapter);


        user_bowling_skill_adapter = ArrayAdapter.createFromResource(this, R.array.user_bowling_skill_set_array, android.R.layout.simple_spinner_item);
        user_bowling_skill_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user_bowling_skill_spinner.setAdapter(user_bowling_skill_adapter);

        user_batting_skill_spinner.setVisibility(View.INVISIBLE);
        user_batting_style_tv.setVisibility(View.INVISIBLE);
        user_bowling_skill_spinner.setVisibility(View.INVISIBLE);
        user_bowling_style_tv.setVisibility(View.INVISIBLE);

        user_primary_skill_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selcted_primary_skill = parent.getItemAtPosition(position).toString();
                // selected_primary_item_position = parent.getSelectedItemPosition();
                // user_secondary_skill_spinner.removeViewsInLayout(selected_primary_item_position+1,1);
                Log.d("ankur", selcted_primary_skill);
                if (selcted_primary_skill == user_primary_skill_spinner.getItemAtPosition(0)) {
                    user_bowling_hand = "-";
                    user_bowling_skill_spinner.setVisibility(View.INVISIBLE);
                    user_bowling_style_tv.setVisibility(View.INVISIBLE);
                    user_batting_skill_spinner.setVisibility(View.VISIBLE);
                    user_batting_style_tv.setVisibility(View.VISIBLE);
                } else if (selcted_primary_skill == user_primary_skill_spinner.getItemAtPosition(1)) {
                    user_batting_hand = "-";
                    user_batting_skill_spinner.setVisibility(View.INVISIBLE);
                    user_batting_style_tv.setVisibility(View.INVISIBLE);
                    user_bowling_skill_spinner.setVisibility(View.VISIBLE);
                    user_bowling_style_tv.setVisibility(View.VISIBLE);
                } else {
                    user_batting_skill_spinner.setVisibility(View.VISIBLE);
                    user_batting_style_tv.setVisibility(View.VISIBLE);
                    user_bowling_skill_spinner.setVisibility(View.VISIBLE);
                    user_bowling_style_tv.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        user_secondary_skill_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selected_secondary_skill = parent.getItemAtPosition(position).toString();
                //    selected_secondary_item_position = parent.getSelectedItemPosition();
                //      user_secondary_skill_spinner.removeViewAt(selected_primary_item_position+1);

                if (selected_secondary_skill == user_secondary_skill_spinner.getItemAtPosition(1)) {
                    user_wicketkeeper = selected_secondary_skill;
                } else {
                    return;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        user_batting_skill_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_batting_hand = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        user_bowling_skill_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_bowling_hand = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        user_credential_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ankur", "New value is" + user_dob);
                Log.d("ankur", "New value is" + user_edit_profile_details);
                dataStorage();
                databaseReference.child(unique_user_Id).updateChildren(user_edit_profile_details);
                Log.d("ankur", "New value is 1" + user_edit_profile_details);


                Intent person_profile_intent = new Intent(UserEditProfileActivity.this, PersonProfile.class);
                person_profile_intent.putExtra("selcted_primary_skill", selcted_primary_skill);
                person_profile_intent.putExtra("selected_secondary_skill", selected_secondary_skill);

                startActivity(person_profile_intent);

            }

        });
    }

    public void dataStorage() {
        if (selected_secondary_skill == user_wicketkeeper) {
            user_role = ("WicketKeeper" + "-" + selcted_primary_skill);
        } else {
            user_role = selcted_primary_skill;
        }
        user_dob = user_dob_et.getText().toString();
        user_skills = user_skill_et.getText().toString();
        user_achievement = user_achievement_et.getText().toString();
        user_address_city = user_address_city_et.getText().toString();
        Log.d("ankur", "give ans" + user_dob);


        if (user_dob == null || user_address_city == null || user_skills == null || user_role == null || user_achievement == null || user_bowling_hand == null || user_bowling_hand == null ||
                user_wicketkeeper == null) {
            Toast.makeText(getApplicationContext(), "Please Enter all the details", Toast.LENGTH_SHORT).show();
        } else {

            user_edit_profile_details = new HashMap<>();
            user_edit_profile_details.put("user_dob", user_dob);
            user_edit_profile_details.put("user_address_city", user_address_city);
            user_edit_profile_details.put("user_skills", user_skills);
            user_edit_profile_details.put("user_role", user_role);
            user_edit_profile_details.put("user_achievement", user_achievement);
            user_edit_profile_details.put("user_batting_hand", user_batting_hand);
            user_edit_profile_details.put("user_bowling_hand", user_bowling_hand);
            user_edit_profile_details.put("user_wicketkeeper", user_wicketkeeper);
            Log.d("ankur", "this is " + user_edit_profile_details);
            Log.d("ankur", "this is " + unique_user_Id);
        }
    }

}
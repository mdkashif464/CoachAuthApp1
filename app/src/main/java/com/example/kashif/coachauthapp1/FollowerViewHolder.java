package com.example.kashif.coachauthapp1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Ayush on 6/18/2017.
 */

public class FollowerViewHolder  extends RecyclerView.ViewHolder {

    private ImageView userProfileImageView;
    private TextView userNameTextView;



    public FollowerViewHolder(View itemView) {
        super(itemView);

        userProfileImageView = (ImageView) itemView.findViewById(R.id.user_profile_imageview);
        userNameTextView = (TextView) itemView.findViewById(R.id.user_name_textview);

    }



    public void setName(String name) {
        userNameTextView.setText(name);
    }

    public void setUserProfileImage(String profileImageUrl) {
        Picasso.with(itemView.getContext()).load(profileImageUrl).into(userProfileImageView);

    }
}

package com.example.kashif.coachauthapp1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ayush on 6/17/2017.
 */

public class AllUserListViewHolder extends RecyclerView.ViewHolder {


    TextView userName_tv = (TextView) itemView.findViewById(R.id.user_name_tv);
    TextView userMail_tv = (TextView) itemView.findViewById(R.id.user_email_tv);
    TextView player_achievment_tv = (TextView)itemView.findViewById(R.id.user_profile_achievements_tv) ;
    TextView player_skills_tv = (TextView)itemView.findViewById(R.id.user_profile_skills_tv);



    public AllUserListViewHolder(View itemView) {
        super(itemView);}

    public void setTitle(String name) {
        userName_tv.setText(name);
    }

    public void setDesc(String mail) {
        userMail_tv.setText(mail);

    }
    public void setAchievement(String achievement) {
        player_achievment_tv.setText(achievement);
    }
    public void  setSkills(String skills){
        player_skills_tv.setText(skills);
    }

}

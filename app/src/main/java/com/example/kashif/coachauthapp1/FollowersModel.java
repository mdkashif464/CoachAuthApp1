package com.example.kashif.coachauthapp1;

/**
 * Created by Ayush on 6/21/2017.
 */

public class FollowersModel {

    private String User_name;
    private String User_image_Url;

    public FollowersModel(String user_name, String user_image_Url) {
        User_name = user_name;
        User_image_Url = user_image_Url;
    }






    public String getUser_name() {
        return User_name;
    }


    public String getUser_image_Url() {
        return User_image_Url;
    }

}
package com.example.kashif.coachauthapp1;

/**
 * Created by Ayush on 6/16/2017.
 */

public class UserModel {

    private String UniqueUserId;
    private String Name;
    private String Email;
    private String ProfileImageUrl;
    private String Achievemants;
    private String Skills;
    private String FollowingUsers;
    private String Followers;


    public String getUniqueUserId(){
        return UniqueUserId;
    }

    public void setUniqueUserId(String uniqueUserId){
        this.UniqueUserId = uniqueUserId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String userName) {
        Name = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfileImageUrl() {
        return ProfileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        ProfileImageUrl = profileImageUrl;
    }

    public String getAchievemants() {
        return Achievemants;
    }

    public void setAchievemants(String achievemants) {
        Achievemants = achievemants;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }
}

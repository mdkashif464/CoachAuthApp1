package com.example.kashif.coachauthapp1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ayush on 6/18/2017.
 */

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    ArrayList<FollowingModel> following_model_array_list = new ArrayList<FollowingModel>();

    Context context;



    @Override
    public FollowingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.follower_adapter, parent, false);
        Log.d("result1","calling");
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowingViewHolder holder, int position) {
        holder.user_name.setText(following_model_array_list.get(position).getUser_name());
        Picasso.with(context)
                .load(following_model_array_list.get(position).getUser_image_Url())
                .into(holder.user_image);
        Log.d("result1", "ans is" + following_model_array_list.size());
    }

    @Override
    public int getItemCount() {
        return following_model_array_list.size();    }

    public void getList(ArrayList<FollowingModel> followingList){
        this.following_model_array_list=followingList;
        Log.d("ankur", String.valueOf(followingList.size())+"followerList size");
    }

    public class FollowingViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
        ImageView user_image;

        public FollowingViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name_textview);
            user_image = (ImageView) itemView.findViewById(R.id.user_profile_imageview);


        }
    }
}
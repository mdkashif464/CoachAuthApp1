package com.example.kashif.coachauthapp1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by Ayush on 6/20/2017.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {

    ArrayList<FollowersModel> followersModels = new ArrayList<FollowersModel>();

    Context context;

    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.follower_adapter, parent, false);
        Log.d("result1","calling");
        return new FollowerViewHolder(view);

    }


//this is final  array list
    @Override
    public void onBindViewHolder(FollowerViewHolder holder, final int position) {
      holder.user_name.setText(followersModels.get(position).getUser_name());
        Picasso.with(context)
                .load(followersModels.get(position).getUser_image_Url())
               .into(holder.user_image);
        Log.d("result1", "ans is" + followersModels.size());
    }

    @Override
    public int getItemCount() {

        return followersModels.size();

    }

    public void getList(ArrayList<FollowersModel> followerList){
        this.followersModels=followerList;
        Log.d("ankur", String.valueOf(followerList.size())+"followerList size");
    }

    public class FollowerViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
        ImageView user_image;

        public FollowerViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name_textview);
            user_image = (ImageView) itemView.findViewById(R.id.user_profile_imageview);


        }
    }
}

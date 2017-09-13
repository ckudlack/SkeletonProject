package com.cdk.skeletonproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdk.skeletonproject.data.SoundCloudUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<SoundCloudUser> users = new ArrayList<>();

    public UsersAdapter(List<SoundCloudUser> users) {
        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(View.inflate(parent.getContext(), R.layout.user_list_item, null));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final SoundCloudUser soundCloudUser = users.get(position);
        holder.bind(soundCloudUser.getAvatarUrl(), soundCloudUser.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void update(List<SoundCloudUser> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar) ImageView avatar;
        @BindView(R.id.username) TextView userName;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String avatarUrl, String user) {
            Glide.with(this.itemView.getContext()).load(avatarUrl).into(avatar);
            userName.setText(user);
        }
    }
}

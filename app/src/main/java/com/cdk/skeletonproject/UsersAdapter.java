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
    private UserItemClickListener itemClickListener;

    public UsersAdapter(List<SoundCloudUser> users, UserItemClickListener itemClickListener) {
        this.users.addAll(users);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final UserViewHolder userViewHolder = new UserViewHolder(View.inflate(parent.getContext(), R.layout.user_list_item, null));
        userViewHolder.itemView.setOnClickListener(view -> {
            final int position = userViewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                final SoundCloudUser soundCloudUser = users.get(position);
                soundCloudUser.setUserIsSelected(!soundCloudUser.isUserSelected());
                notifyItemChanged(position);
                itemClickListener.onItemClick(soundCloudUser);
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final SoundCloudUser soundCloudUser = users.get(position);
        holder.bind(soundCloudUser.getAvatarUrl(), soundCloudUser.getUsername());
        holder.setCheckVisibility(soundCloudUser.isUserSelected());
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
        @BindView(R.id.check_mark) ImageView checkMark;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String avatarUrl, String user) {
            Glide.with(this.itemView.getContext()).load(avatarUrl).into(avatar);
            userName.setText(user);
        }

        public void setCheckVisibility(boolean isSelected) {
            checkMark.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public interface UserItemClickListener {
        void onItemClick(SoundCloudUser user);
    }
}

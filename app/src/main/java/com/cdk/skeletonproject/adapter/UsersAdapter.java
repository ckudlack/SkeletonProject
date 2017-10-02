package com.cdk.skeletonproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.data.Artist;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<Artist> users = new ArrayList<>();
    private UserItemClickListener itemClickListener;

    public UsersAdapter(List<Artist> users, UserItemClickListener itemClickListener) {
        this.users.addAll(users);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final UserViewHolder userViewHolder = new UserViewHolder(View.inflate(parent.getContext(), R.layout.user_list_item, null));
        userViewHolder.itemView.setOnClickListener(view -> {
            final int position = userViewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                final Artist artist = users.get(position);
                artist.setUserIsSelected(!artist.isUserSelected());
                notifyItemChanged(position);
                itemClickListener.onItemClick(artist);
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final Artist artist = users.get(position);
        holder.bind(artist.getAvatarUrl(), artist.getUsername());
        holder.setCheckVisibility(artist.isUserSelected());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void update(List<Artist> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    // TODO: Use RX
    public List<Artist> getSelectedUsers() {
        List<Artist> selectedUsers = new ArrayList<>();

        for (Artist user : users) {
            if (user.isUserSelected()) {
                selectedUsers.add(user);
            }
        }

        return selectedUsers;
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
        void onItemClick(Artist user);
    }
}
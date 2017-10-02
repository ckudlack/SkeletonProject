package com.cdk.skeletonproject.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.data.Artist;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<Artist> artists = new ArrayList<>();

    public EventsAdapter() {
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final EventsViewHolder eventsViewHolder = new EventsViewHolder(View.inflate(parent.getContext(), R.layout.artist_event_list_item, null));
        eventsViewHolder.songKickButton.setOnClickListener(view -> {
            final int adapterPosition = eventsViewHolder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                // TODO: Launch SongKick intent from parent Activity
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(artists.get(adapterPosition).getEvents().get(0).getEventUri()));
                parent.getContext().startActivity(browserIntent);
            }
        });
        return eventsViewHolder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        holder.bindEvent(artists.get(position));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void setArtists(List<Artist> artists) {
        this.artists.clear();
        this.artists.addAll(artists);
        notifyDataSetChanged();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.artist_name) TextView artistName;
        @BindView(R.id.event_name) TextView eventName;
        @BindView(R.id.songkick_button) Button songKickButton;
        @BindView(R.id.artist_image) ImageView artistAvatar;

        public EventsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindEvent(Artist artist) {
            artistName.setText(artist.getUsername());
            eventName.setText(artist.getEvents().get(0).getDisplayName());
            Glide.with(itemView.getContext()).load(artist.getAvatarUrl()).into(artistAvatar);
        }
    }
}

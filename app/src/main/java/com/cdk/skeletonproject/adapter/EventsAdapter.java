package com.cdk.skeletonproject.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.utils.BitmapUtils;

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
        final EventsViewHolder eventsViewHolder = new EventsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_event_list_item, parent, false));
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

            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    final Palette palette = Palette.from(resource).maximumColorCount(32).generate();
                    final Palette.Swatch swatch = BitmapUtils.checkVibrantSwatch(palette);
                    itemView.setBackgroundColor(swatch.getRgb());
                    artistName.setTextColor(swatch.getTitleTextColor());
                    eventName.setTextColor(swatch.getTitleTextColor());
                    artistAvatar.setImageBitmap(resource);
                }
            };

            Glide.with(itemView.getContext()).load(artist.getAvatarUrl()).asBitmap().into(target);

        }
    }
}

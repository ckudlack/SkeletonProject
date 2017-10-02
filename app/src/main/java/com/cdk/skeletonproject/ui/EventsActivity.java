package com.cdk.skeletonproject.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.adapter.EventsAdapter;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.contract.EventsContract;
import com.cdk.skeletonproject.mvp.datasource.SongKickLocalDataSource;
import com.cdk.skeletonproject.mvp.datasource.SongKickRemoteDataSource;
import com.cdk.skeletonproject.mvp.presenter.EventsPresenter;
import com.cdk.skeletonproject.mvp.repository.SongKickRepository;
import com.cdk.skeletonproject.mvp.usecase.EventsUseCase;
import com.cdk.skeletonproject.network.Api;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends AppCompatActivity implements EventsContract.View {

    @BindView(R.id.events_list) RecyclerView eventsRecyclerView;

    @BindString(R.string.key_songkick) String songKickKey;

    private EventsContract.Presenter presenter;
    private EventsAdapter adapter = new EventsAdapter();

    public static Intent newIntent(Context context) {
        return new Intent(context, EventsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ButterKnife.bind(this);

        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        eventsRecyclerView.setAdapter(adapter);

        initPresenter();

        presenter.initialize(songKickKey);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public void showEventsList(List<Artist> artists) {
        adapter.setArtists(artists);
    }

    private void initPresenter() {
        presenter = new EventsPresenter(this,
                new EventsUseCase(
                        new SongKickRepository(
                                new SongKickLocalDataSource(), new SongKickRemoteDataSource(Api.getSongKickService()))));
    }
}

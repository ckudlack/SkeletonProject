package com.cdk.skeletonproject.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.ScanCompleteEvent;
import com.cdk.skeletonproject.adapter.UsersAdapter;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.contract.MainContract;
import com.cdk.skeletonproject.mvp.datasource.SoundCloudLocalDataSource;
import com.cdk.skeletonproject.mvp.datasource.SoundCloudRemoteDataSource;
import com.cdk.skeletonproject.mvp.presenter.MainPresenter;
import com.cdk.skeletonproject.mvp.repository.SoundCloudRepository;
import com.cdk.skeletonproject.mvp.usecase.MainUseCase;
import com.cdk.skeletonproject.network.Api;
import com.cdk.skeletonproject.service.SoundCloudScanningService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements MainContract.View, UsersAdapter.UserItemClickListener, OnSuccessListener<Location>, SwipeRefreshLayout.OnRefreshListener {

    private static final int COARSE_LOCATION_PERMISSION_CODE = 9876;
    public static final String EXTRA_LOCATION = "location";

    @BindView(R.id.def_user_name) TextView userNameView;
    @BindView(R.id.users_list) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.key_soundcloud) String clientKey;
    @BindString(R.string.key_songkick) String songKickKey;

    private MainContract.Presenter presenter;
    private UsersAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_LOCATION)) {
            userLocation = savedInstanceState.getParcelable(EXTRA_LOCATION);
        }

        initPresenter();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, this);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    COARSE_LOCATION_PERMISSION_CODE);
        }

        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.getDefaultUser(clientKey);
    }

    private void initPresenter() {
        presenter = new MainPresenter(this, new MainUseCase(
                new SoundCloudRepository(
                        new SoundCloudLocalDataSource(),
                        new SoundCloudRemoteDataSource(Api.getSoundCloudService()))
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_user:
                startUserSelectionActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            presenter.getDefaultUser(clientKey);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == COARSE_LOCATION_PERMISSION_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(EXTRA_LOCATION, userLocation);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @OnClick(R.id.scan_button)
    void scanButtonClicked() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Start scanning?")
                .setMessage("This will scan the artists you've selected to see if they're performing near you." +
                        " Please double check that you have selected correctly")
                .setPositiveButton("Continue", (dialogInterface, i) -> presenter.dialogContinueClicked(adapter.getSelectedUsers()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        alertDialog.show();
    }

    @SuppressWarnings("unused")
    public void onEvent(ScanCompleteEvent event) {
        presenter.onScanComplete();
        EventBus.getDefault().removeStickyEvent(ScanCompleteEvent.class);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable error) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setUserList(List<Artist> users) {
        if (adapter == null) {
            adapter = new UsersAdapter(users, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.update(users);
        }
    }

    @Override
    public void startUserSelectionActivity() {
        startActivityForResult(new Intent(this, SetUserActivity.class), 123);
    }

    @Override
    public void startScanningService(List<Artist> artists) {
        startService(new Intent(this, SoundCloudScanningService.class)
                .putParcelableArrayListExtra("ARTISTS", (ArrayList<? extends Parcelable>) artists)
                .putExtra(EXTRA_LOCATION, userLocation));
    }

    @Override
    public void registerEventBus() {
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void startEventsActivity() {
        startActivity(EventsActivity.newIntent(this));
    }

    @Override
    public void onItemClick(Artist user) {

    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            this.userLocation = location;
        }
    }

    @Override
    public void onRefresh() {
        presenter.getDefaultUserFollowing(clientKey);
    }
}

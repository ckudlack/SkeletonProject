package com.cdk.skeletonproject.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.UsersAdapter;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.MainContract;
import com.cdk.skeletonproject.mvp.datasource.SoundCloudLocalDataSource;
import com.cdk.skeletonproject.mvp.datasource.SoundCloudRemoteDataSource;
import com.cdk.skeletonproject.mvp.presenter.MainPresenter;
import com.cdk.skeletonproject.mvp.repository.SoundCloudRepository;
import com.cdk.skeletonproject.mvp.usecase.SoundCloudSearchUseCase;
import com.cdk.skeletonproject.network.Api;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.View, UsersAdapter.UserItemClickListener, OnSuccessListener<Location>, SwipeRefreshLayout.OnRefreshListener {

    private static final int COARSE_LOCATION_PERMISSION_CODE = 9876;

    @BindView(R.id.def_user_name) TextView userNameView;
    @BindView(R.id.def_user_image) ImageView userAvatarView;
    @BindView(R.id.users_list) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;

    @BindString(R.string.key) String clientKey;

    private MainContract.Presenter presenter;
    private UsersAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this, new SoundCloudSearchUseCase(new SoundCloudRepository(new SoundCloudLocalDataSource(Realm.getDefaultInstance()), new SoundCloudRemoteDataSource(Api.getNetworkService()))));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
    public void setUserList(List<SoundCloudUser> users) {
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
    public void setupUI(String username, String avatarUrl) {
        userNameView.setText(username + " follows:");
        Glide.with(this).load(avatarUrl).into(userAvatarView);
    }

    @Override
    public void onItemClick(SoundCloudUser user) {

    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            // TODO: Connect this information to SongKick
        }
    }

    @Override
    public void onRefresh() {
        presenter.getDefaultUserFollowing(clientKey);
    }
}

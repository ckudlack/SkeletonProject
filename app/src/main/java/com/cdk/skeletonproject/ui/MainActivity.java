package com.cdk.skeletonproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.widget.EditText;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MainContract.View, UsersAdapter.UserItemClickListener {

    @BindView(R.id.username) EditText userNameField;
    @BindView(R.id.users_list) RecyclerView recyclerView;

    private MainContract.Presenter presenter;
    private UsersAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this, new SoundCloudSearchUseCase(new SoundCloudRepository(new SoundCloudLocalDataSource(Realm.getDefaultInstance()), new SoundCloudRemoteDataSource(Api.getNetworkService()))));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.init();
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

    @OnClick(R.id.button)
    void onClickButton() {
        final Editable text = userNameField.getText();
        presenter.buttonClicked(String.valueOf(text), getResources().getString(R.string.key));
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
        startActivity(new Intent(this, SetUserActivity.class));
    }

    @Override
    public void onItemClick(SoundCloudUser user) {

    }
}

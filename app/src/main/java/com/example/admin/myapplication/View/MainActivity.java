package com.example.admin.myapplication.View;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.admin.myapplication.Contact;
import com.example.admin.myapplication.Model.InfoBean;
import com.example.admin.myapplication.Presenter.MainPresenter;
import com.example.admin.myapplication.Presenter.RecyclerViewAdapter;
import com.example.admin.myapplication.R;

public class MainActivity extends AppCompatActivity implements Contact.MainView {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private MainPresenter mainPresenter = new MainPresenter(this);
    private InfoBean infoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();
        getData();
        refresh();
    }

    @Override
    public void initId() {
        swipeRefreshLayout = findViewById(R.id.SRL);
    }

    @Override
    public void initRV(InfoBean infoBean) {
        recyclerView = findViewById(R.id.RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (infoBean != null) {
            recyclerViewAdapter = new RecyclerViewAdapter(infoBean);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1)) {//就很牛逼的判断方法 canSrollVertically -1往下 1往上
                        mainPresenter.getBeforeData(recyclerViewAdapter.getDate());
                    }
                }
            });
        }
    }

    @Override
    public void getData() {
        mainPresenter.getLatestData();
    }

    @Override
    public void refresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView = null;
            recyclerViewAdapter = null;
            getData();
            Toast.makeText(this, "刷新成功！", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void loadMore(InfoBean infoBean) {
        recyclerViewAdapter.update(infoBean);
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }
}
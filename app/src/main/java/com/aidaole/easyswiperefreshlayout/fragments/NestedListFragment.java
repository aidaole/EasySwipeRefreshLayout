package com.aidaole.easyswiperefreshlayout.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aidaole.easyswiperefreshlayout.EasySwipeRefreshLayout;
import com.aidaole.easyswiperefreshlayout.NestedListView;
import com.aidaole.easyswiperefreshlayout.R;
import com.aidaole.easyswiperefreshlayout.adapters.CustomListAdapter;
import com.aidaole.easyswiperefreshlayout.repo.ArticleRepo;

import java.util.ArrayList;

public class NestedListFragment extends Fragment {

    private EasySwipeRefreshLayout mRefreshLayout;
    private NestedListView mNestedListview;
    private CustomListAdapter mAdapter;
    private ArticleRepo mArticleRepo;

    public static NestedListFragment newInstance() {
        NestedListFragment fragment = new NestedListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.nestedlist_fragment, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        mArticleRepo = new ArticleRepo();
        mRefreshLayout = root.findViewById(R.id.refresh_layout);
        mNestedListview = root.findViewById(R.id.recyclerview);
        mAdapter = new CustomListAdapter(new ArrayList<>());
        mAdapter.insert(mArticleRepo.getArticles(), 0);
        mNestedListview.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new EasySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.insert(mArticleRepo.getRadomArticle(), 0);
                        mRefreshLayout.stopRefreshing();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 3 * 1000);
            }
        });
    }
}

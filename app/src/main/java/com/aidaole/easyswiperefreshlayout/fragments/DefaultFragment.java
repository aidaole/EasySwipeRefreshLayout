package com.aidaole.easyswiperefreshlayout.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aidaole.easyswiperefreshlayout.EasySwipeRefreshLayout;
import com.aidaole.easyswiperefreshlayout.R;
import com.aidaole.easyswiperefreshlayout.adapters.CustomRecyclerAdapter;
import com.aidaole.easyswiperefreshlayout.entity.Article;
import com.aidaole.easyswiperefreshlayout.repo.ArticleRepo;

import java.util.ArrayList;

public class DefaultFragment extends Fragment {

    private EasySwipeRefreshLayout mRefreshlayout;
    private RecyclerView mRecyclerview;
    private CustomRecyclerAdapter mAdapter;
    private ArticleRepo mArticleRepo;

    public DefaultFragment() {
        mArticleRepo = new ArticleRepo();
    }

    public static DefaultFragment newInstance() {
        DefaultFragment fragment = new DefaultFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext())
                .inflate(R.layout.default_fragment, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        mRefreshlayout = root.findViewById(R.id.refresh_layout);
        mRecyclerview = root.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new CustomRecyclerAdapter(new ArrayList<Article>());
        mAdapter.insert(mArticleRepo.getArticles(), 0);
        mRecyclerview.setAdapter(mAdapter);
        mRefreshlayout.setOnRefreshListener(new EasySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.insert(mArticleRepo.getRadomArticle(), 0);
                        mRefreshlayout.stopRefreshing();
                    }
                }, 3 * 1000);
            }
        });
    }
}

package com.aidaole.easyswiperefreshlayout.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aidaole.easyswiperefreshlayout.EasySwipeRefreshLayout;
import com.aidaole.easyswiperefreshlayout.R;
import com.aidaole.easyswiperefreshlayout.adapters.CustomRecyclerAdapter;
import com.aidaole.easyswiperefreshlayout.layout.MoveHeaderRefreshLayout;
import com.aidaole.easyswiperefreshlayout.entity.Article;
import com.aidaole.easyswiperefreshlayout.repo.ArticleRepo;

import java.util.ArrayList;

public class MoveHeaderFragment extends Fragment {

    private MoveHeaderRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private CustomRecyclerAdapter mAdapter;

    private ArticleRepo mArticleRepo;

    public static MoveHeaderFragment newInstance() {
        MoveHeaderFragment fragment = new MoveHeaderFragment();
        return fragment;
    }

    public MoveHeaderFragment() {
        mArticleRepo = new ArticleRepo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.moveheader_fragment,
                container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        mRefreshLayout = root.findViewById(R.id.refresh_layout);
        mRecyclerView = root.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CustomRecyclerAdapter(new ArrayList<Article>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.insert(mArticleRepo.getArticles(), 0);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setOnRefreshListener(new EasySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.insert(mArticleRepo.getRadomArticle(), 0);
                        mRefreshLayout.stopRefreshing();
                    }
                }, 3 * 1000);
            }
        });
    }
}

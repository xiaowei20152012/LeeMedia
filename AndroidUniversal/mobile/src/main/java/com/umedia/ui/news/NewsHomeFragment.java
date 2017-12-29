package com.umedia.ui.news;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonlibrary.net.OkGenericsCallback;
import com.squareup.okhttp.Request;
import com.umedia.android.R;
import com.umedia.ui.news.model.GirlResult;
import com.umedia.ui.news.model.NewInfo;
import com.umedia.ui.news.model.NewsDataSource;
import com.umedia.utils.Const;
import com.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsHomeFragment extends Fragment {
    private View rootView;
    private NewsDataSource dataSource;
    private XRecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewInfo> newList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new NewsDataSource();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        recyclerView = rootView.findViewById(R.id.list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.refreshComplete();

                    }
                }, 2000);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.loadMoreComplete();
                    }
                }, 2000);
            }
        });
        adapter = new NewsAdapter(newList);
        recyclerView.setAdapter(adapter);
        dataSource.loadNewsList(Const.NEWS_SERVER + Const.GIRLS_URL, new OkGenericsCallback<GirlResult>() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(GirlResult response) {
                List<NewInfo> list = response.getResults();
                for (NewInfo info : list) {
                    newList.add(info);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

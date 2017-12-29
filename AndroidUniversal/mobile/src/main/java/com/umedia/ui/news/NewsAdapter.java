package com.umedia.ui.news;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umedia.android.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemBinder> {


    @Override
    public NewsItemBinder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsItemBinder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsItemBinder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NewsItemBinder extends RecyclerView.ViewHolder {

        public NewsItemBinder(View itemView) {
            super(itemView);
        }
    }
}

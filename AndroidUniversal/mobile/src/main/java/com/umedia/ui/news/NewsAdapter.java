package com.umedia.ui.news;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.commonlibrary.image.ImageLoader;
import com.umedia.android.R;
import com.umedia.ui.news.model.NewInfo;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemBinder> {
    private List<NewInfo> list;

    public NewsAdapter(List<NewInfo> list) {
        this.list = list;
        if (list == null) {
            this.list = new ArrayList<>();
        }
    }


    @Override
    public NewsItemBinder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsItemBinder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsItemBinder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NewsItemBinder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private Context context;

        public NewsItemBinder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            iv = itemView.findViewById(R.id.new_item_iv);
        }

        public void bindData(NewInfo data) {
            ImageLoader.load(context, data.getUrl(), iv);
        }
    }
}

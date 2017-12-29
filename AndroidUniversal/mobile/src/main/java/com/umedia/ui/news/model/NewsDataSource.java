package com.umedia.ui.news.model;


import com.commonlibrary.net.OkApiClient;
import com.commonlibrary.net.OkGenericsCallback;
import com.commonlibrary.net.OkStringCallback;
import com.umedia.utils.ClientsUtil;

public class NewsDataSource {

    private OkApiClient newsListClient;

    public void loadNewsList(String url, OkStringCallback callback) {
        newsListClient = new OkApiClient.Builder().url(url).build();
        newsListClient.request(callback);
    }


    public void release() {
        ClientsUtil.release(newsListClient);
    }
}

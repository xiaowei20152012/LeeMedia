package com.umedia.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.umedia.android.R;
import com.umedia.ui.news.NewsHomeFragment;
import com.umedia.utils.LogHelper;

public class NewsListActivity extends BaseActivity {
    private static final String TAG = LogHelper.makeLogTag(NewsListActivity.class);
    private static final String FRAGMENT_TAG = "news_list_container";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslist);
        initializeToolbar();
        navigateToNews();
    }

    private void navigateToNews() {
        LogHelper.d(TAG, "navigateToBrowser, mediaId=");
        NewsHomeFragment fragment = getNewsFragment();

        if (fragment == null) {
            fragment = new NewsHomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.slide_out_to_right);
            transaction.replace(R.id.container, fragment, FRAGMENT_TAG);
            // If this is not the top level media (root), we add it to the fragment back stack,
            // so that actionbar toggle and Back will work appropriately:
            transaction.commitAllowingStateLoss();
        }
    }

    private NewsHomeFragment getNewsFragment() {
        return (NewsHomeFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

}

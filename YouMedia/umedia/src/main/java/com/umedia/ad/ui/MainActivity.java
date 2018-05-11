package com.umedia.ad.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.umedia.ad.R;
import com.umedia.ad.base.BaseActivity;
import com.umedia.ad.ui.song.SongsFragment;

public class MainActivity extends BaseActivity {
    private Fragment songFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songFragment = SongsFragment.instance();
        replaceFragment();
    }

    private void replaceFragment() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, songFragment).
                commitAllowingStateLoss();
    }
}

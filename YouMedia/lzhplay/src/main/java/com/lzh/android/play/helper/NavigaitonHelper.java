package com.lzh.android.play.helper;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.util.PreferencesUtils;
import com.lzh.android.play.MainActivity;
import com.lzh.android.play.R;
import com.lzh.android.play.util.AssertUtil;
import com.lzh.android.play.util.Const;
import com.lzh.android.play.util.PreferenceKey;

public class NavigaitonHelper {
    public static NavigaitonHelper instance(MainActivity activity) {
        return new NavigaitonHelper(activity);
    }

    private final MainActivity activity;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView navHeaderImageView;
    private TextView navHeaderTitle;
    private TextView navHeaderContent;
    private View navHeaderBottom;

    private int idNavCamera;
    private int idNavGallery;
    private int idNavSlideshow;
    private int idNavManage;
    private int idNavShare;
    private int idNavSend;
    private int idActionSetting;

    public NavigaitonHelper(MainActivity activity) {
        this.activity = activity;
        init();
    }

    private void init() {
        drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        idNavCamera = R.id.nav_camera;
        idNavGallery = R.id.nav_gallery;
        idNavSlideshow = R.id.nav_slideshow;
        idNavManage = R.id.nav_manage;
        idNavShare = R.id.nav_share;
        idNavSend = R.id.nav_send;
        idActionSetting = R.id.action_settings;

//        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, activity.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navHeaderImageView = navigationView.findViewById(R.id.nav_header_imageView);
        navHeaderTitle = navigationView.findViewById(R.id.nav_header_title);
        navHeaderContent = navigationView.findViewById(R.id.nav_header_content);
        navHeaderBottom = navigationView.findViewById(R.id.nav_header_bottom);

        navigationView.setNavigationItemSelectedListener(activity);
        checkMenu(PreferencesUtils.getInt(activity, PreferenceKey.KEY_MENU));
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == idNavCamera) {
            // Handle the camera action
            resetMenu(Const.MENU_CAMERA);
        } else if (id == idNavGallery) {
            resetMenu(Const.MENU_GALLERY);
        } else if (id == idNavSlideshow) {
            resetMenu(Const.MENU_SLIDE_SHOW);
        } else if (id == idNavManage) {
            resetMenu(Const.MENU_MANAGE);
        } else if (id == idNavShare) {
            resetMenu(Const.MENU_SHARE);
        } else if (id == idNavSend) {
            resetMenu(Const.MENU_SEND);
        } else {
            resetMenu(Const.MENU_CAMERA);
        }
        navigationView.setCheckedItem(id);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == idActionSetting) {

            return true;
        }

        return false;
    }

    public boolean handleBackPress() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    public void resetMenu(int currentSelect) {
        PreferencesUtils.putInt(activity, PreferenceKey.KEY_MENU, currentSelect);
    }

    public void checkMenu(int select) {
        if (select == Const.MENU_CAMERA) {
            navigationView.setCheckedItem(idNavCamera);
        } else if (select == Const.MENU_GALLERY) {
            navigationView.setCheckedItem(idNavGallery);

        } else if (select == Const.MENU_SLIDE_SHOW) {
            navigationView.setCheckedItem(idNavSlideshow);

        } else if (select == Const.MENU_MANAGE) {
            navigationView.setCheckedItem(idNavManage);

        } else if (select == Const.MENU_SHARE) {
            navigationView.setCheckedItem(idNavShare);

        } else if (select == Const.MENU_SEND) {
            navigationView.setCheckedItem(idNavSend);

        } else {
            navigationView.setCheckedItem(idNavCamera);

        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void setHeaderView(String url, String title, String content) {
        if (AssertUtil.isEmpty(url)) {

        } else {

        }
        if (AssertUtil.isEmpty(title)) {
            navHeaderBottom.setVisibility(View.GONE);
            navHeaderTitle.setText("");
        } else {
            navHeaderBottom.setVisibility(View.VISIBLE);
            navHeaderTitle.setText(title);
        }
        if (AssertUtil.isEmpty(content)) {
            navHeaderContent.setText("");
        } else {
            navHeaderContent.setText(content);
        }
    }
}

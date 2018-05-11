package com.umedia.ad.ui.song;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umedia.ad.R;
import com.umedia.ad.base.BaseFragment;
import com.umedia.ad.provider.main.MainMusicModel;

public class SongsFragment extends BaseFragment implements MainMusicModel.Listener {
    private View rootView;
    private MainMusicModel model;

    private SongAdapter adapter;
    private RecyclerView recyclerView;

    public static Fragment instance() {
        return new SongsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = MainMusicModel.create(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_song, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        adapter = new SongAdapter(model.getSongs());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        if (getUserVisibleHint()) {
            model.loadData();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        model.release();
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            model.loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void loading() {

    }

    @Override
    public void loaded() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String error) {

    }
}

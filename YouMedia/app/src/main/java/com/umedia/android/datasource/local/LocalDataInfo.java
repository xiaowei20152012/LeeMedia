package com.umedia.android.datasource.local;


import com.umedia.android.model.FileInfo;

import java.util.ArrayList;

public class LocalDataInfo {

    private ArrayList<FileInfo> totalInfos;

    private ArrayList<FileInfo> videoInfos;

    private ArrayList<FileInfo> audioInfos;

    private ArrayList<FileInfo> imageInfos;

    private ArrayList<FileInfo> otherInfos;


    public LocalDataInfo() {
        totalInfos = new ArrayList<>(1);
        videoInfos = new ArrayList<>(1);
        audioInfos = new ArrayList<>(1);
        imageInfos = new ArrayList<>(1);
        otherInfos = new ArrayList<>(1);
    }

    public void setVideoInfo(FileInfo fileInfo) {
        totalInfos.add(fileInfo);
        videoInfos.add(fileInfo);
    }

    public void setMusicInfo(FileInfo fileInfo) {
        totalInfos.add(fileInfo);
        audioInfos.add(fileInfo);
    }

    public void setImageInfo(FileInfo fileInfo) {
        totalInfos.add(fileInfo);
        imageInfos.add(fileInfo);
    }

    public void setOtherInfo(FileInfo fileInfo) {
        totalInfos.add(fileInfo);
        otherInfos.add(fileInfo);
    }

    public ArrayList<FileInfo> getTotalInfos() {
        return totalInfos;
    }

    public ArrayList<FileInfo> getVideoInfos() {
        return videoInfos;
    }

    public ArrayList<FileInfo> getAudioInfos() {
        return audioInfos;
    }

    public ArrayList<FileInfo> getImageInfos() {
        return imageInfos;
    }

    public ArrayList<FileInfo> getOtherInfos() {
        return otherInfos;
    }

    private void setOtherInfos(ArrayList<FileInfo> otherInfos) {
        this.otherInfos = otherInfos;
    }

    private void setImageInfos(ArrayList<FileInfo> imageInfos) {
        this.imageInfos = imageInfos;
    }

    private void setAudioInfos(ArrayList<FileInfo> audioInfos) {
        this.audioInfos = audioInfos;
    }

    private void setVideoInfos(ArrayList<FileInfo> videoInfos) {
        this.videoInfos = videoInfos;
    }

    private void setTotalInfos(ArrayList<FileInfo> totalInfos) {
        this.totalInfos = totalInfos;
    }
}

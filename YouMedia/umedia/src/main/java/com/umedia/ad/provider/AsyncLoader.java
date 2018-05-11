package com.umedia.ad.provider;

import android.os.AsyncTask;


public class AsyncLoader {

    private LoadCallBack listener;
    private Loader loader;

    public AsyncLoader(LoadCallBack listener) {
        this.listener = listener;
    }

    public void load() {
        if (loader != null) {
            loader.cancel(true);
        }
        loader = new Loader(listener);
        loader.execute();
    }

    public void release() {
        if (loader != null) {
            loader.cancel(true);
        }
    }


    public interface LoadCallBack<T> {
        T doInbackground();

        void loaded(T result);

        void loadError(String msg);
    }

    private static class Loader extends AsyncTask {
        private LoadCallBack listener;

        public Loader(LoadCallBack listener) {
            this.listener = listener;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Object result = null;
            if (listener != null) {
                try {
                    result = listener.doInbackground();
                } catch (Exception ignore) {
                        listener.loadError(ignore.getMessage());
                }
            }
            if (listener != null) {
                listener.loaded(result);
            }
            return null;
        }


    }
}

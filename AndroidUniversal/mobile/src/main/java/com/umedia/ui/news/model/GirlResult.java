package com.umedia.ui.news.model;


import java.util.List;

public class GirlResult {
    private boolean error;
    private List<NewInfo> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewInfo> getResults() {
        return results;
    }

    public void setResults(List<NewInfo> results) {
        this.results = results;
    }
}

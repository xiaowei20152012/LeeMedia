package com.umedia.ad.util;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class JsonRequest {


    public static class RequestInfo{
        private List<Object> resources;
        public static RequestInfo create(){
            return new RequestInfo();
        }
        public RequestInfo(){
            resources = new ArrayList<>(1);
        }

        public void add(Object item){
            resources.add(item);
        }

       @Override
       public String toString() {
           Gson gson = new Gson();
           return "{"+"\"resources\":"+ gson.toJson(resources)+"}";
       }
    }

    public static class HistoryRequest{
        private String id;
        private String type;
        private int status;


        public HistoryRequest(String id, String type, int status) {
            this.id = id;
            this.type = type;
            this.status = status;
        }
    }
}

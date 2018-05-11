package com.sqlite.javalib;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class myClass1 {
    public static void main(String[] args) {

        HistoryRequest historyRequest = new HistoryRequest("id","type",5);
        RequestInfo info = RequestInfo.create();
        info.add(historyRequest);
        Gson go = new Gson();
        System.out.println(info.toString());

    }

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

//       @Override
//       public String toString() {
//           Gson gson = new Gson();
//           return gson.toJson(resources);
//       }
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

package com.umedia.utils;


import com.commonlibrary.net.OkApiClient;

public final class ClientsUtil {

    public static final void release(OkApiClient... clients) {
        for (OkApiClient client : clients) {
            if (client != null) {
                client.release();
            }
        }
    }

}

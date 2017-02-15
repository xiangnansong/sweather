package com.example.song.sweathor.util;

/**
 * Created by song on 15-2-13.
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}

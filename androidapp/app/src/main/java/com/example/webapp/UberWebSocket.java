package com.example.webapp;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UberWebSocket extends Service{
    private WebSocketClient mWebSocketClient;

    final String LOG_TAG = "myLogs";
    private String token = "";


    public void onCreate() {
        super.onCreate();

        //connectWebSocket();
        Log.d(LOG_TAG, "onCreate");
    }
    private static void sendMessageToActivity(String msg, Context dristnya) {
        Intent intent = new Intent("SUPA");
        intent.putExtra("msg", msg);
        LocalBroadcastManager.getInstance(dristnya).sendBroadcast(intent);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        this.token = intent.getStringExtra("TOKEN");
        //connectWebSocket();
       // sendMessageToActivity("AAA",this);
        connectWebSocket();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    public void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("http://192.168.1.229:9091/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        Map<String,String> httpHeaders = new HashMap<String,String>();
        httpHeaders.put("token", token);


        mWebSocketClient = new WebSocketClient(uri, httpHeaders) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");

                //mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                sendMessageToActivity(s, UberWebSocket.this);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }


        };
        mWebSocketClient.connect();
       // sendMessage(token);
    }

    public void sendMessage(String msg) {
        mWebSocketClient.send(msg);
    }
}

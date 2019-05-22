package com.example.webapp;

import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Chat extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*
        private static WebsocketServer INSTANCE = new WebsocketServer();
    public static WebsocketServer getIstance() {
        return INSTANCE;
    }
     */
    private ArrayAdapter<String> arrayAdapter;
    private String uuid, token, chat_uuid, nickname = "";
    private ArrayList<String> all_msgs;
    private DrawerLayout drawLayout;
    private List<String> dataList = new ArrayList<String>();

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = null;
            message = intent.getStringExtra("msg");
            if (message == null || message.equals(""))
            {
                return;
            }
            JSONObject recievedData, params_json;
            String uuid_msg, text;
            String[] array = null;
            try {
                recievedData = new JSONObject(message);
                params_json = recievedData.getJSONObject("params");
                text = params_json.getString("text");
                uuid_msg = params_json.getString("uuid");

                dataList.add(text);
                all_msgs.add(uuid_msg);
                arrayAdapter.notifyDataSetChanged();

            }catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
        uuid = extras.getString("UUID");
        token = extras.getString("TOKEN");
        chat_uuid = extras.getString("CHAT");
        nickname = extras.getString("LOGIN");
        SendJSON sender = new SendJSON(1000000, 100000);
        String result = null;

        //

        EditText edi = findViewById(R.id.chat_input);
        Point size = new Point();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        LinearLayout.LayoutParams vi_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (size.y * 0.2));
        edi.setLayoutParams(vi_params);

        ListView msg_list = findViewById(R.id.all_msg);

        size = new Point();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        vi_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (size.y * 0.50));
        msg_list.setLayoutParams(vi_params);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dataList);
        msg_list.setAdapter(arrayAdapter);

        JSONObject postData = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("uuid", chat_uuid);
            postData.put("id", "1234");
            postData.put("jsonrpc", "2.0");
            postData.put("method", "creat_user");
            postData.put("params", params);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        try{
            String IP = new Kostyl().IP;
            result = sender.execute(IP + "/chat", null, "GET", chat_uuid, token).get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
        }
        //TODO name on top of list and check is result is only error code

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.navigator_view);
        drawLayout = findViewById(R.id.draw_layout);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawLayout, toolbar,
                R.string.navigation_open, R.string.navigation_close);
        drawLayout.addDrawerListener(toggle);
        toggle.syncState();

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("SUPA"));


        String result2[] = result.split("\n"), name;
        JSONObject recievedData, params_json;
        JSONArray all_uuids;
        try {
            recievedData = new JSONObject(result2[0]);
            params_json = recievedData.getJSONObject("params");
            all_uuids = params_json.getJSONArray("messagesUUIDs");
            name = params_json.getString("name");
            this.all_msgs = new ArrayList<String>();
            for (int i = 0; i < all_uuids.length(); i++) {
                all_msgs.add(all_uuids.get(i).toString());
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        draw_chats();
        Button send_button = findViewById(R.id.send);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO send shit
                SendJSON sender = new SendJSON(1000000, 100000);
                JSONObject postData = new JSONObject();
                JSONObject params = new JSONObject();
                String result = null;
                TextView msg = findViewById(R.id.chat_input);
                try {
                    params.put("chatUUID", chat_uuid);
                    params.put("text", msg.getText().toString());
                    postData.put("id", "1234");
                    postData.put("jsonrpc", "2.0");
                    postData.put("method", "creat_user");
                    postData.put("params", params);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                try{
                    String IP = new Kostyl().IP;
                    result = sender.execute(IP + "/message", postData.toString(), "POST", null, token).get();
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch(ExecutionException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    private void draw_chats() {
        ListView msg_list = findViewById(R.id.all_msg);
        //TODO for each uuid get text msg
        for(String s : all_msgs)
        {
            SendJSON sender = new SendJSON(1000000, 100000);
            JSONObject postData = new JSONObject();
            JSONObject params = new JSONObject();
            String result = null;
            try {
                params.put("uuid", s);
                postData.put("id", "1234");
                postData.put("jsonrpc", "2.0");
                postData.put("method", "creat_user");
                postData.put("params", params);

            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            try{
                String IP = new Kostyl().IP;
                result = sender.execute(IP + "/message", null, "GET", s, token).get();
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch(ExecutionException e)
            {
                e.printStackTrace();
            }
            String result2[] = result.split("\n");
            JSONObject recievedData, params_json;
            String cur_msg = null;
            try {
                recievedData = new JSONObject(result2[0]);
                params_json = recievedData.getJSONObject("params");
                cur_msg = params_json.getString("text");

            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            if (cur_msg!=null)
            {
                dataList.add(cur_msg);
            }

        }

    }

    @Override
    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            /*TODO /chats */
            case R.id.chats_all:
                Intent intent1 = new Intent(this, MainMenu.class);
                intent1.putExtra("LOGIN", nickname);
                intent1.putExtra("UUID", uuid);
                intent1.putExtra("TOKEN", token);
                startActivity(intent1);
                finish();
                break;

            case R.id.friends:
                Intent intent = new Intent(this, Friendlist.class);
                intent.putExtra("LOGIN", nickname);
                intent.putExtra("UUID", uuid);
                intent.putExtra("TOKEN", token);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawLayout.isDrawerOpen(GravityCompat.START))
        {
            drawLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
}

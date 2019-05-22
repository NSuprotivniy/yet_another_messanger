package com.example.webapp;

import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawLayout;
    private String nickname, uuid, token;
    private ArrayList<String> all_chats;
    static final int ADD_CHAT = 1;
    private String message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Bundle extras = getIntent().getExtras();
        Toolbar toolbar = findViewById(R.id.toolbar);


        drawLayout = findViewById(R.id.draw_layout);
        nickname = extras.getString("LOGIN");
        uuid = extras.getString("UUID");
        token = extras.getString("TOKEN");

        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.navigator_view);

        View headerView = navigationView.getHeaderView(0);
        TextView nickname_text = headerView.findViewById(R.id.nickname_in_menu);
        nickname_text.setText(nickname);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawLayout, toolbar,
                R.string.navigation_open, R.string.navigation_close);
        drawLayout.addDrawerListener(toggle);
        toggle.syncState();

        SendJSON sender = new SendJSON(1000000, 100000);
        String result = null;
        //TODO remove this plug and parse real JSON
        //String [] all_chats2 = {"Chat one\nuuid1", "Chat 2\nuuid2"};
        JSONObject postData = new JSONObject();
        JSONObject params = new JSONObject();
        try {
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
            result = sender.execute(IP + "/chats", null, "GET", null, token).get();
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
        JSONArray all_uuids, all_names;
        String[] array = null;
        try {
            recievedData = new JSONObject(result2[0]);
            params_json = recievedData.getJSONObject("params");
            all_uuids = params_json.getJSONArray("uuids");
            all_names = params_json.getJSONArray("names");
            array = new String[all_uuids.length()];
            for (int i = 0; i < all_uuids.length(); i++) {
                array[i] = all_names.get(i) + "\n" + all_uuids.get(i);
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(array != null) {
            this.all_chats = new ArrayList<String>(Arrays.asList(array));
        }
        else
        {
            this.all_chats = new ArrayList<String>();
        }
        //this.all_chats = all_chats2;
        //Request /chats params:{name: "", uuid: ""}
      //  String [] chats = geChats();
        if (all_chats != null)
        {
            draw_chats();
        }
    }

    private void draw_chats()
    {
        ListView chats_list = findViewById(R.id.Chats);
        List<String> dataList = new ArrayList<String>();
        if(all_chats != null) {
            for (String s : all_chats) {
                String[] cur = s.split("\n");
                dataList.add(cur[0]);
            }
        }
        Point size = new Point();
        ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        LinearLayout.LayoutParams vi_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(size.y*0.70));
        chats_list.setLayoutParams(vi_params);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dataList);
        chats_list.setAdapter(arrayAdapter);

        chats_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                String[] chosen = all_chats.get(index).split("\n");
                Intent intent = new Intent(MainMenu.this, Chat.class);
                intent.putExtra("UUID", uuid);
                intent.putExtra("TOKEN", token);
                intent.putExtra("CHAT", chosen[1]);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, AddChat.class);
                intent.putExtra("LOGIN", nickname);
                intent.putExtra("UUID", uuid);
                intent.putExtra("TOKEN", token);
                startActivityForResult(intent, ADD_CHAT );

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String result = data.getStringExtra("name");
        if (result.equals(""))
        {
            return;
        }
        JSONObject recievedData, params_json;
        String result2[] = result.split("\n");
        try {
            recievedData = new JSONObject(result2[1]);
            params_json = recievedData.getJSONObject("params");
            uuid = params_json.getString("uuid");

        }catch (JSONException e)
        {
            e.printStackTrace();
            return;
        }
        result = result2[0] + "\n" + uuid;
        all_chats.add(result);
        draw_chats();
    }

    private String [] geChats()
    {
        SendJSON sender = new SendJSON(1000000, 1000000);
        String chats_str;
        try{
            String IP = new Kostyl().IP + "/chats";
            chats_str = sender.execute(IP, null, "GET", "123uuid123").get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
            return  null;
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
            return null;
        }
        JSONObject recievedData, params_json, cur;
        try {
            recievedData = new JSONObject(chats_str);
            params_json = recievedData.getJSONObject("params");


        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
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

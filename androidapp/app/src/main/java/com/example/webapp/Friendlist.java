package com.example.webapp;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Friendlist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawLayout;
    private String nickname, uuid, tocken;
    private String[] all_friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        Bundle extras = getIntent().getExtras();
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawLayout = findViewById(R.id.draw_layout);
        nickname = extras.getString("LOGIN");
        uuid = extras.getString("UUID");
        tocken = extras.getString("TOKEN");
        ListView friend_list = findViewById(R.id.Chats);
        Point size = new Point();
        ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        LinearLayout.LayoutParams vi_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(size.y*0.70));
        friend_list.setLayoutParams(vi_params);

        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.navigator_view);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Friendlist.this, AddFriend.class);
                Bundle b = new Bundle();
                b.putString("UUID", uuid);
                b.putString("TOKEN", tocken);
                startActivity(intent);
            }
        });

        View headerView = navigationView.getHeaderView(0);
        TextView nickname_text = headerView.findViewById(R.id.nickname_in_menu);
        nickname_text.setText(nickname);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawLayout, toolbar,
                R.string.navigation_open, R.string.navigation_close);
        drawLayout.addDrawerListener(toggle);
        toggle.syncState();
        String [] all_chats2 = {"Vasya\nuuid1", "Petya\nuuid2"};
        SendJSON sender = new SendJSON(1000000, 100000);
        //TODO remove this plug
        //Request /chats params:{name: "", uuid: ""}
        //  String [] friend = geChats();
        this.all_friends = all_chats2;
        //Request /chats params:{name: "", uuid: ""}
        //  String [] chats = geChats();
        if (all_friends != null)
        {
            draw_friends();
        }
    }

    private void draw_friends()
    {
        ListView friend_list = findViewById(R.id.Chats);
        List<String> dataList = new ArrayList<String>();
        for (String s : all_friends) {
            String[] cur = s.split("\n");
            dataList.add(cur[0]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dataList);
        friend_list.setAdapter(arrayAdapter);

        friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                //Object clickItemObj = adapterView.getAdapter().getItem(index);
                String[] chosen = all_friends[index].split("\n");
                //TODO get friend with uuid, that i get

            }
        });
    }


    private String [] geFirends()
    {
        SendJSON sender = new SendJSON(10000, 10000);
        String chats_str;
        try{
            chats_str = sender.execute("http://192.168.0.107:8080/friends", null, "GET", "123uuid123").get();
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
                Intent intent = new Intent(this, MainMenu.class);
                intent.putExtra("LOGIN", nickname);
                intent.putExtra("UUID", uuid);
                startActivity(intent);
                finish();
                break;

            case R.id.friends:
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

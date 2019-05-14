package com.example.webapp;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
        String [] all_chats2 = {"Chat one\nuuid1", "Chat 2\nuuid2"};
       /* try{
            result = sender.execute("http://192.168.0.107:8080/chats", null, "GET", uuid, token).get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
        }*/
        this.all_chats = new ArrayList<String>(Arrays.asList(all_chats2));
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
        for (String s : all_chats) {
            String[] cur = s.split("\n");
            dataList.add(cur[0]);
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
                //Object clickItemObj = adapterView.getAdapter().getItem(index);
                //String[] chosen = all_chats[index].split("\n");
                //TODO get chat with uuid by index from all_chats

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, AddChat.class);
                intent.putExtra("LOGIN", nickname);
                intent.putExtra("UUID", uuid);
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
        all_chats.add(result);
        draw_chats();
    }

    private String [] geChats()
    {
        SendJSON sender = new SendJSON(1000000, 1000000);
        String chats_str;
        try{
            chats_str = sender.execute("http://192.168.0.107:8080/chats", null, "GET", "123uuid123").get();
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
                break;

            case R.id.friends:
                Intent intent = new Intent(this, Friendlist.class);
                intent.putExtra("LOGIN", nickname);
                intent.putExtra("UUID", uuid);
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

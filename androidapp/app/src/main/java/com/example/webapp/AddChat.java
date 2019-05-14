package com.example.webapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddChat extends Activity {

    String tocken, uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);
        ListView friend_list = findViewById(R.id.Chats);
        Point size = new Point();
        ((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        LinearLayout.LayoutParams vi_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(size.y*0.55));
        friend_list.setLayoutParams(vi_params);

        Bundle extras = getIntent().getExtras();
        tocken = extras.getString("TOKEN");
        uuid = extras.getString("UUID");

        SendJSON sender = new SendJSON(1000000, 1000000);
        String chats_str;
       /* try{
            chats_str = sender.execute("http://192.168.0.107:8080/friends", null, "GET", null, tocken).get();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
        }*/

        //TODO Parse friendlist from this answer, but for now this plug
        String [] all_friends = {"Vasya\nuuid1", "Petya\nuuid2"};
        List<String> dataList = new ArrayList<String>();
        for (String s : all_friends) {
            String[] cur = s.split("\n");
            dataList.add(cur[0]);
        }

        friend_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, dataList);
        friend_list.setAdapter(arrayAdapter);

        Button Cancel_button = findViewById(R.id.Cancel);
        Button Ok_button = findViewById(R.id.OK);

        Ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                try{
                    SendJSON sender = new SendJSON(100, 100);
                    //result = sender.execute("http://192.168.43.15:9090/chats", null, "GET", uuid, token).get();
                    // test
                    JSONObject postData = new JSONObject();
                    JSONObject params = new JSONObject();
                    JSONArray array = new JSONArray();
                    EditText name = findViewById(R.id.chat_name);
                    String chat_name = name.getText().toString();
                    if (chat_name.equals(""))
                    {
                        Intent intent = new Intent();
                        intent.putExtra("name", "");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    try {
                        postData.put("id", "1234");
                        postData.put("jsonrpc", "2.0");
                        postData.put("method", "creat_user");

                        String parts[] = {uuid, "123uuid"};
                        array.put(uuid);
                        array.put(uuid);

                        params.put("participantsUUIDs",array);
                        params.put("name","chat_1");

                        postData.put("params", params);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //
                    result = sender.execute("http://192.168.43.15:9090/chat", postData.toString(), "POST", null, tocken).get();
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
        catch(ExecutionException e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                //intent.putExtra("name", result);
                //TODO remove plug, parse answer
                intent.putExtra("name", "Kirill\nuuid234");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "");
                setResult(RESULT_OK, intent);
                finish();
                finish();
            }
        });
    }
}

package com.example.webapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import com.android.volley.RequestQueue;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private TextView Answer;
    private  RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button CharButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CharButton = findViewById(R.id.ChatButton);
        Answer = findViewById(R.id.textView);
        CharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject postData = new JSONObject();
                JSONObject params = new JSONObject();
                String result = null;
                try {
                    postData.put("id", "0");
                    postData.put("jsonrpc", "2.0");
                    postData.put("method", "abc");
                    postData.put("params", params);
                    SendJSON sender = new SendJSON();
                    try{
                        result = sender.execute("http://192.168.0.109:8080/chat", postData.toString()).get();
                    }catch (ExecutionException e)
                    {
                        Answer.setText("Error");
                        return;
                    }catch (InterruptedException e)
                    {
                        Answer.setText("Error");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Answer.setText("Error");
                    return;
                }
                Answer.setText(result);
            }
        });

    }

    private class SendJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes( params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result);
            //return result;
        }
    }

}
package com.example.webapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddFriend extends Activity {

    private String uuid = null, token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Bundle b = getIntent().getExtras();
        String value = null;
        if(b != null) {
            uuid = b.getString("UUID");
            token = b.getString("TOKEN");
        }
        int width, heigth;
        width = dm.widthPixels;
        heigth = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(heigth*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        Button Cancel_button = findViewById(R.id.Cancel);
        Button Ok_button = findViewById(R.id.OK);

        EditText EmailFriend = findViewById(R.id.email_friend);

        Ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Send GET request with my uuid and email

            }
        });

        Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

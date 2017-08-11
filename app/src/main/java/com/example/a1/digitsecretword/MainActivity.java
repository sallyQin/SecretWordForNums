package com.example.a1.digitsecretword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button button;
    CoverView coverView;
    private boolean started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coverView  = (CoverView) findViewById(R.id.coverView);

        button = (Button) findViewById(R.id.button_cover);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && !started){
            started = true;
            coverView.startCoverBaloonThread();
        }
    }

    public void enabledButton(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);
            }
        });

    }
}

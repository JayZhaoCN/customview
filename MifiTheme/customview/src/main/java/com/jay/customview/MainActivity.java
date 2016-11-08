package com.jay.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStyle(STYLE.BACK_AND_MORE, R.color.bg_color_red_dark);
        setTitle("Custom View");
    }
}

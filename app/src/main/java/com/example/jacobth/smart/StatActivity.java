package com.example.jacobth.smart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by jacobth on 2016-04-09.
 */
public class StatActivity extends Activity implements View.OnClickListener{

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        backButton =(ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

    }
}

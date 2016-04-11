package com.example.jacobth.smart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.*;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by jacobth on 2016-04-09.
 */
public class StatActivity2 extends Activity implements View.OnClickListener{

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats2);

        backButton =(ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        Intent i = getIntent();
        String category = i.getExtras().getString("category");
      //  setLayout(category);

        BarChart chart = (BarChart) findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataSet = new BarDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarData data = new BarData(labels, dataSet);
        chart.setData(data);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.animateY(2000);
    }
    @Override
    public void onClick(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setLayout(String item) {
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        TextView textView = (TextView)findViewById(R.id.textHigh);

        switch (item) {
            case "Rån":
                textView.setText("Anmälda rån per 100 000 invånare från och med år 1950");
                imageView.setImageResource(R.drawable.robbery2);
                break;
            case "Misshandel":
                textView.setText("Anmäld misshandel per 100 000 invånare från och med år 1950");
                imageView.setImageResource(R.drawable.violence);
                break;
            case "Sexualbrott":
                textView.setText("Anmälda sexualbrott per 100 000 invånare från och med år 1950");
                imageView.setImageResource(R.drawable.sexual);
                break;
        }
    }
}

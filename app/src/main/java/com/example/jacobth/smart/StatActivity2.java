package com.example.jacobth.smart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        setLayout(category);
    }
    @Override
    public void onClick(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void drawChart(String file) {
        BarChart chart = (BarChart) findViewById(R.id.chart);
        ArrayList<BarEntry> entries = getData(file);

        BarDataSet dataSet = new BarDataSet(entries, "Number of crimes");
        ArrayList<String> labels = getLabels(entries.size());
        BarData data = new BarData(labels, dataSet);

        chart.setData(data);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        chart.animateY(2000);
    }

    private void setLayout(String item) {
        TextView textView = (TextView)findViewById(R.id.textHigh);

        switch (item) {
            case "Rån":
                textView.setText("Anmälda rån per 100 000 invånare från och med år 1950");
                drawChart("robberyper.txt");
                break;
            case "Misshandel":
                textView.setText("Anmäld misshandel per 100 000 invånare från och med år 1950");
                drawChart("violenceper.txt");
                break;
            case "Sexualbrott":
                textView.setText("Anmälda sexualbrott per 100 000 invånare från och med år 1950");
                drawChart("violenceper.txt");
                break;
        }
    }

    private ArrayList<String> getLabels(int size) {
        ArrayList<String> labels = new ArrayList<String>();
        for(int i = 0; i < size; i++) {
            if(i == 0) {
                labels.add("1950");
            }
            else if(i == size/2) {
                labels.add("1991");
            }
            else if(i == size-1) {
                labels.add("2014");
            }
            else {
                labels.add("");
            }
        }
        return labels;
    }

    private ArrayList<BarEntry> getData(String file) {
        int i = 0;
        ArrayList<BarEntry> entries = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    entries.add(new BarEntry(Integer.parseInt(receiveString), i++));
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

}

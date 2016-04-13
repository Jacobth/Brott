package com.example.jacobth.smart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StatActivity extends Activity implements View.OnClickListener, TabHost.OnTabChangeListener{

    private ImageButton backButton;
    private BarChart chart;
    private BarChart chartTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        chart = (BarChart) findViewById(R.id.chart);
        chartTotal = (BarChart) findViewById(R.id.chartTotal);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.perTab);
        tabSpec.setIndicator("per");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.totalTab);
        tabSpec.setIndicator("total");
        tabHost.addTab(tabSpec);

        setTabColor(tabHost);
        tabHost.setOnTabChangedListener(this);
     //   backButton =(ImageButton)findViewById(R.id.backButton);
     //   backButton.setOnClickListener(this);

        Intent i = getIntent();
        String category = i.getExtras().getString("category");
        setPerLayout(category);
        setTotalLayout(category);
    }

    @Override
    public void onTabChanged(String s) {
        if(s == "tag1") {
            chart.animateY(1500);
        }
        else {
            chartTotal.animateY(1500);
        }
    }

    @Override
    public void onClick(View view) {
   //     finish();
      //  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void drawChart(String file, BarChart chart) {
        ArrayList<BarEntry> entries = getData(file);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());

        BarDataSet dataSet = new BarDataSet(entries, "Antal anmälda brott");
        ArrayList<String> labels = getLabels();
        BarData data = new BarData(labels, dataSet);
        data.setValueFormatter(new BarDataFormatter());

        chart.setData(data);
        dataSet.setColor(Color.WHITE);

        chart.animateY(2000);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getBarData().setValueTextColor(Color.WHITE);
        chart.getBarData().setHighlightEnabled(false);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setDescription("");
        chart.getLegend().setTextColor(Color.WHITE);
    }

    private void setPerLayout(String item) {
        TextView textView = (TextView)findViewById(R.id.textHigh);

        switch (item) {
            case "Rån":
                textView.setText("Anmälda rån per 100 000 invånare från och med år 1950");
                drawChart("robberyper.txt", chart);
                break;
            case "Misshandel":
                textView.setText("Anmäld misshandel per 100 000 invånare från och med år 1950");
                drawChart("violenceper.txt", chart);
                break;
            case "Sexualbrott":
                textView.setText("Anmälda sexualbrott per 100 000 invånare från och med år 1950");
                drawChart("sexualper.txt", chart);
                break;
            case "Inbrott":
                textView.setText("Anmälda inbrott per 100 000 invånare från och med år 1950");
                drawChart("breakinper.txt", chart);
                break;
            case "Mord":
                textView.setText("Anmälda mord per 100 000 invånare från och med år 1950");
                drawChart("killingsper.txt", chart);
                break;
            case "Skadegörelse":
                textView.setText("Anmäld skadegörelse per 100 000 invånare från och med år 1950");
                drawChart("damageper.txt", chart);
                break;
        }
    }

    private void setTotalLayout(String item) {
        TextView textView = (TextView)findViewById(R.id.textHighTotal);

        switch (item) {
            case "Rån":
                textView.setText("Totalt antal anmälda rån från och med år 1950");
                drawChart("robberytot.txt", chartTotal);
                break;
            case "Misshandel":
                textView.setText("Totalt antal anmäld misshandel från och med år 1950");
                drawChart("violencetot.txt", chartTotal);
                break;
            case "Sexualbrott":
                textView.setText("Totalt antal anmälda sexualbrott från och med år 1950");
                drawChart("sexualtot.txt", chartTotal);
                break;
            case "Inbrott":
                textView.setText("Totalt antal anmälda inbrott från och med år 1950");
                drawChart("breakintot.txt", chartTotal);
                break;
            case "Mord":
                textView.setText("Totalt antal anmälda mord från och med år 1950");
                drawChart("killingstot.txt", chartTotal);
                break;
            case "Skadegörelse":
                textView.setText("Totalt antal anmälda skadegörelse från och med år 1950");
                drawChart("damagetot.txt", chartTotal);
                break;
        }
    }

    private ArrayList<String> getLabels() {
        ArrayList<String> labels = new ArrayList<String>();

        try {
            InputStream inputStream = getAssets().open("years.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    labels.add(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void setTabColor(TabHost tabHost) {
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }
    }

}

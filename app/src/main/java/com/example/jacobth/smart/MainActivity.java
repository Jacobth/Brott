package com.example.jacobth.smart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadList();
    }

    private void loadList() {
        final ListView listview = (ListView) findViewById(R.id.listView);
        String[] values = new String[] { "Mord", "Misshandel", "Sexualbrott",
                "Våldtäkt", "Bostadsinbrott", "Rån", "Skadegörelse" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                R.layout.list_design, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(500).alpha(0).withEndAction(new Runnable() {

                    @Override
                    public void run() {
                        setContentView(R.layout.stats);
                        backButton =(ImageButton)findViewById(R.id.backButton);
                        ImageView imageView = (ImageView)findViewById(R.id.imageView);
                        TextView textView = (TextView)findViewById(R.id.textHigh);

                        Animation RightSwipe = AnimationUtils.loadAnimation(this, R.layout.left_slide);
                        ScreenAnimation.startAnimation(RightSwipe);

                        switch (item) {
                            case "Rån":
                                textView.setText("Anmälda rån per 100 000 invånare från och med år 1950");
                                imageView.setImageResource(R.drawable.robbery);
                                break;
                            case "Misshandel":
                                textView.setText("Anmäld misshandel per 100 000 invånare från och med år 1950");
                                imageView.setImageResource(R.drawable.violence);
                                break;
                        }
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });
    }

    private void backButton() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.back) {
            setContentView(R.layout.activity_main);
            loadList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case 2:
                setContentView(R.layout.stats);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                try {
                    setContentView(R.layout.activity_main);
                    loadList();
                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void addSpinner() {
        
    }
}

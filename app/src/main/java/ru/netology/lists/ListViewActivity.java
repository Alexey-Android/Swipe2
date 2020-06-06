package ru.netology.lists;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private static final String ATTRIBUTE_NAME_TITLE = "title";
    private static final String ATTRIBUTE_NAME_SUBTITLE = "subtitle";

    List<Map<String, String>> simpleAdapterContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.list);
        prepareContent();

        final BaseAdapter listContentAdapter = createAdapter(simpleAdapterContent);


        list.setAdapter(listContentAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                simpleAdapterContent.remove(position);
                listContentAdapter.notifyDataSetChanged();
            }
        });

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleAdapterContent.clear();
                prepareContent();
                listContentAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        String[] from = {ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_SUBTITLE};
        int[] to = {R.id.tv_title, R.id.tv_subtitle};
        return new SimpleAdapter(this, values, R.layout.list_item, from, to);
    }

    private void prepareContent() {
        try {
            prepareContentFromPrefs();
        } catch (Exception e) {
            e.printStackTrace();
            prepareContentFromAssets();
            SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
            preferences.edit().putString("values", getString(R.string.large_text)).apply();

        }
    }

    private void prepareContentFromPrefs() throws Exception {
        SharedPreferences preferences = getSharedPreferences("values", MODE_PRIVATE);
        String savedStr = preferences.getString("values", "");

        String[] strings;
        if (!savedStr.isEmpty()) {
            strings = savedStr.split("\n\n");

        } else {
            throw new Exception("SharedPreferences has no values");

        }

        for (String str : strings) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_NAME_TITLE, str.length() + "");
            map.put(ATTRIBUTE_NAME_SUBTITLE, str);
            simpleAdapterContent.add(map);
        }
    }

    private void prepareContentFromAssets() {
        String[] strings = getString(R.string.large_text).split("\n\n");
        for (String str : strings) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_NAME_TITLE, str.length() + "");
            map.put(ATTRIBUTE_NAME_SUBTITLE, str);
            simpleAdapterContent.add(map);
        }
    }

}

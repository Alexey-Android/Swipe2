package ru.netology.lists;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private static final String ATTRIBUTE_NAME_TITLE = "title";
    private static final String ATTRIBUTE_NAME_SUBTITLE = "subtitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.list);

        //String[] values = prepareContent();

        List<Map<String, String>> values = prepareContent();

        BaseAdapter listContentAdapter = createAdapter(values);

        list.setAdapter(listContentAdapter);
    }


    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        String[] from = {ATTRIBUTE_NAME_TITLE, ATTRIBUTE_NAME_SUBTITLE};
        int[] to = {R.id.tv_title, R.id.tv_subtitle};
        return new SimpleAdapter(this, values, R.layout.list_item, from, to);
    }


    private List<Map<String,String>> prepareContent() {
        String[] strings = getString(R.string.large_text).split("\n\n");
        List<Map<String, String>> list = new ArrayList<>();
        for (String str : strings) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_NAME_TITLE, str.length() +"");
            map.put(ATTRIBUTE_NAME_SUBTITLE, str);
            list.add(map);
        }
        return list;
    }
}

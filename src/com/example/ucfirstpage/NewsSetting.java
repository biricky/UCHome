package com.example.ucfirstpage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.R.array;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewsSetting extends Activity{
	private View lv;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//lv = findViewById(R.id.news_list);
		List<String> arr = new ArrayList<String>();
		arr.add("Hello");
		arr.add("Tencent");
		arr.add("MIG");
		arr.add("Android");
		arr.add("AAA");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
		((ListView) lv).setAdapter(adapter);
	}

}

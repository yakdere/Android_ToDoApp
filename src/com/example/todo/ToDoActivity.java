package com.example.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {
	ListView lvitems;
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.ETNewItem);
		lvitems = (ListView) findViewById(R.id.LVItems);
		items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvitems.setAdapter(itemsAdapter);
		setupListViewListener();	
	}

	private void setupListViewListener() {
		lvitems.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});
	}
	public void showSoftKeyboard(View view) {
		if(view.requestFocus()) {
			InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
		}
	}
	public void hideSoftKeyboard(View view) {
		InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	public void writeItems() {
		File fileDir = getFilesDir(); 
		File todoFile =new File(fileDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e ){
			e.printStackTrace();
		}

	}

	public void readItems() {
		File fileDir = getFilesDir(); 
		File todoFile =new File(fileDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e){
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}
	//Get new item from Edit Text add to Adapter then clear out the Edit Text to reuse
	public void addToDoItem(View v) { //add button in xml onclick
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		writeItems();
	}

}

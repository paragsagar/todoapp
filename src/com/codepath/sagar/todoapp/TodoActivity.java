package com.codepath.sagar.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends ActionBarActivity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 1080;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText)findViewById(R.id.etNewItem);
		lvItems = (ListView)findViewById(R.id.lvItems);
		readItems();
		todoAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1,todoItems);
		lvItems.setAdapter(todoAdapter);
		
		setupItemListListeners();
	}
	
	private void setupItemListListeners() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeitems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int position,long id) {
			//Call the next Activity and pass on the List Item.
//				((EditText)findViewById(R.id.etEditItem)).setText(todoItems.get(position));
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				  // put "extras" into the bundle for access in the second activity
				  i.putExtra("text",todoItems.get(position)); 
				  i.putExtra("position", position); 
				  // brings up the second activity
				  startActivityForResult(i, REQUEST_CODE);
			}
		});
	}

	private void populateArrayItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("Feed your Fish");
		todoItems.add("Pay Insurance Bill");
		todoItems.add("Switch off the lights!");
		
	}
	
	public void addAddedItem(View v) {
		String newItemText = etNewItem.getText().toString();
		todoAdapter.add(newItemText );
		etNewItem.setText("");
		writeitems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// ActivityOne.java, time to handle the result of the sub-activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String editedText = data.getExtras().getString("editedText");
	     // Toast the name to display temporarily on screen
	     
	     int position = data.getExtras().getInt("position");
	     
//	     Toast.makeText(this, editedText+" position:" +position + " editing item:"+todoItems.get(position), Toast.LENGTH_SHORT).show();//Debug
	     
	     Toast.makeText(this, editedText+" Saved!", Toast.LENGTH_SHORT).show();
	     
	     todoItems.set(position,editedText);
	     todoAdapter.notifyDataSetChanged();
		 writeitems();
	  }
	} 
	
	
	private void readItems()
	{
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir,"todoApp.txt");
		try{
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		}catch (IOException ioe){
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeitems()
	{
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir,"todoApp.txt");
		try{
			FileUtils.writeLines(todoFile, todoItems);
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
}

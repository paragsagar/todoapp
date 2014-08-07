package com.codepath.sagar.todoapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {
	
	private EditText etEditItem;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
	   String text = getIntent().getStringExtra("text");
	   position = getIntent().getIntExtra("position", 0);
		etEditItem = (EditText)findViewById(R.id.etEditItem);
		etEditItem.setText(text);
		etEditItem.findFocus();
		etEditItem.setSelection(text.length());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void saveItem(View v) {
		//todo Save and go back to original page.
		  // Prepare data intent 
		  Intent data = new Intent();
		  // Pass relevant data back as a result
		  data.putExtra("editedText", etEditItem.getText().toString());
		  data.putExtra("position", position);
		  // Activity finished ok, return the data
		  setResult(RESULT_OK, data); // set result code and bundle data for response
		this.finish();
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
}

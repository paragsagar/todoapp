package com.codepath.sagar.todoapp;

import java.util.List;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Object to save into database.
 */
public class TodoDataHandler {
	
  private List<String> todoList;   

  public static final String TABLE_NAME = "todo";
  public static final String DATABASE_NAME = "todo_db";
  public static final int DB_VERSION = 1;
  public static final String todoItem = "todo_item";
  public static final String TABLE_CREATE = "Create Table todo(todo_item text);";
  
  
  DatabaseHelper dbHelper;
  Context ctx;
  SQLiteDatabase db;
  
  public TodoDataHandler(Context ctx) {
	// TODO Auto-generated constructor stub
	  this.ctx = ctx;
	  dbHelper = new DatabaseHelper(ctx); 
}
  
  private static class DatabaseHelper extends SQLiteOpenHelper
  {
	public DatabaseHelper (Context ctx)
	{
		super (ctx,DATABASE_NAME,null,DB_VERSION);
		
	}
	
	public void onCreate(SQLiteDatabase db){
//		check if Table exists
		if(!isTableExists(db,TABLE_NAME))
			db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(db);
	}
	
	
	boolean isTableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}	
  }
  
  
  public boolean tableExists() {
		// SELECT name FROM sqlite_master WHERE type='table' AND name='table_name';
		return false;
	}
  
  public TodoDataHandler  open()
  {
	  db = dbHelper.getWritableDatabase();
	  return this;
  }
  
  public void close()
  {
	  dbHelper.close();
  }
  
  public void clear()
  {
	  db.execSQL("DELETE from todo");
  }
  
  public long insertTodoItems(String data)
  {
	  ContentValues content = new ContentValues();
	  content.put(todoItem, data);
	  return db.insertOrThrow(TABLE_NAME, null, content);
	  
  }
  
  public Cursor getTodoItems ()
  {
	  return db.query(TABLE_NAME, new String [] {todoItem}, null,null, null, null,null);
  }
  

  
}

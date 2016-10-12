package com.voteapp;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Count extends ListActivity{

	SQLiteDatabase db;
	ArrayAdapter<String> aa=null;
	String par="";
	int count=0;
	
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
//	super.setContentView(R.layout.count);
	ArrayList<String> alist=new ArrayList<String>();
	db=openOrCreateDatabase("votings", Context.MODE_PRIVATE,null);
	try
	{
		Cursor c=db.rawQuery("select party from parties",null);
		while(c.moveToNext())
		{
			par=c.getString(0);
			count=0;
			Cursor d=db.rawQuery("select id from casting where party='"+par+"'",null);
			while(d.moveToNext())
			{
				count++;
			}
			alist.add(par+"  :  "+count);
		}
		aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, alist);
		setListAdapter(aa);
	}
	catch(Exception ex)
	{
		Toast.makeText(Count.this,"Unable to Count Votes"+ex.toString(), Toast.LENGTH_LONG).show();
	}
}
}
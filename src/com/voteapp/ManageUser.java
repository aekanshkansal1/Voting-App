package com.voteapp;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ManageUser extends ListActivity{
SQLiteDatabase db;
ArrayAdapter<String> aa=null;
String s[]=new String[4];
Cursor c;
String item;
ArrayList<String> ids=new ArrayList<String>();

	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//setContentView(R.layout.manageuser);
	ArrayList<String> str=new ArrayList<String>();
	AlertDialog.Builder bc=new AlertDialog.Builder(this);
	bc.setTitle("Notice");
	bc.setMessage("Click On User To See Information or Delete It");
	bc.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
		}
	});
	bc.show();
	db=openOrCreateDatabase("votings",Context.MODE_PRIVATE,null);
	try
	{
		Cursor c=db.rawQuery("select name,id from votes",null);
		while(c.moveToNext())
		{
			str.add("Id-"+c.getString(1)+"\n"+"Name-"+c.getString(0));
			ids.add(c.getString(1));
		}
		aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str);
		setListAdapter(aa);
	}
	catch(Exception ex){ Toast.makeText(this,"Unable to get List items"+ex.toString(),Toast.LENGTH_LONG).show();}
}
	@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
		try
		{
			item=ids.get(position);
			c=db.rawQuery("Select * from votes where id='"+item+"'",null);			
			if(c.moveToNext())
			{
				AlertDialog.Builder ab=new AlertDialog.Builder(this);
				ab.setTitle("Information");
				ab.setMessage("Id: "+c.getString(4)+"\n"+"Name: "+c.getString(0)+"\n"+"Age: "+c.getString(2)
						+"\n"+"Password: "+c.getString(1)+"\n"+"Gender: "+c.getString(3));
				ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				ab.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					try
					{
						db.execSQL("delete from votes where id='"+item+"'");
						if(c.getString(5).equals("1"))
						{
						db.execSQL("delete from casting where id='"+item+"'");
						}
						Toast.makeText(ManageUser.this,"Record Successfully deleted",Toast.LENGTH_LONG).show();
						finish();
					}catch(Exception ex){ Toast.makeText(ManageUser.this, "Unable To Delete Record"+ex.toString(),Toast.LENGTH_LONG).show();}
					}
				});
				ab.show();

			}
		}
		catch(Exception ex)
		{
			Toast.makeText(ManageUser.this,ex.toString(),Toast.LENGTH_LONG).show();
		}
		}
}

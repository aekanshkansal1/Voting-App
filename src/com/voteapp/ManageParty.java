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

public class ManageParty extends ListActivity{
ArrayAdapter<String> aa=null;
SQLiteDatabase db;
String del;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	ArrayList<String> alist=new ArrayList<String>();
	AlertDialog.Builder bc=new AlertDialog.Builder(this);
	bc.setTitle("Notice");
	bc.setMessage("Click On Party to Delete It");
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
		Cursor d=db.rawQuery("select party from parties", null);
			while(d.moveToNext())
			{
				alist.add(d.getString(0));
			}
		
		aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alist);
		setListAdapter(aa);
	}
	catch(Exception ex){Toast.makeText(this,"Parties can't be Loaded"+ex.toString(),Toast.LENGTH_LONG).show(); }
}
	@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
		//	super.onListItemClick(l, v, position, id);
		del=aa.getItem(position);
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setTitle("Delete");
		ab.setMessage("Do you want to Delete Party "+del);
		ab.setPositiveButton("Back to View",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		ab.setNeutralButton("Manage Parties",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ab.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				try
				{
					db.execSQL("delete from parties where party='"+del+"'");
					Toast.makeText(ManageParty.this,"Deleted Successfully",Toast.LENGTH_LONG).show();
					finish();
				}
				catch(Exception ex){Toast.makeText(ManageParty.this,"Unable to delete"+ex.toString(),Toast.LENGTH_LONG).show();}
			}
		});
		ab.show();
		}
}

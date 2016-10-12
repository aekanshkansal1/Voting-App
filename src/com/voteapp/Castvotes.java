package com.voteapp;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Castvotes extends ListActivity{
//	String arr[]={"BJP","Congress","SP","BSP","JDU","AAP","RJD"};   //for static Party with out database
	SQLiteDatabase sdb;
	String voted;
	String ids;
	int choice=0;
	ArrayAdapter<String> aa=null;
@Override
protected void onCreate(Bundle b) {
	// TODO Auto-generated method stub
	super.onCreate(b);
	//setContentView(R.layout.castvotes);
	Intent i=getIntent();
	b=i.getExtras();
	ids=b.getString("idss");
	AlertDialog.Builder bc=new AlertDialog.Builder(this);
	bc.setTitle("Notice");
	bc.setMessage("Click Party to Cast Vote");
	bc.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
	
		}
	});
	bc.show();
//	AlertDialog.Builder ab=new AlertDialog.Builder(this);
	sdb=openOrCreateDatabase("votings", Context.MODE_PRIVATE, null);
	
	//code for list
	ArrayList<String> alist=new ArrayList<String>();
	try
	{
	sdb.execSQL("create table casting(id varchar(30),party varchar(30));");
	sdb.execSQL("insert into parties values('BJP')");
	sdb.execSQL("insert into parties values('Congress')");
	sdb.execSQL("insert into parties values('JDU')");
	sdb.execSQL("insert into parties values('RJD')");
	sdb.execSQL("insert into parties values('SHIVSENA')");
	sdb.execSQL("insert into parties values('AAP')");
	sdb.execSQL("insert into parties values('JMM')");
	sdb.execSQL("insert into parties values('CPI')");
	}
	catch(Exception e)
	{
//	Toast.makeText(Castvotes.this,"Error: "+e.toString(), Toast.LENGTH_LONG).show();
	}	
	try
	{
		sdb.execSQL("create table parties(party varchar(50) primary key)");
	}
	catch(Exception ex)
	{
//		Toast.makeText(this,"Table Party Not Created "+ex.toString(),Toast.LENGTH_LONG).show();
	}
	try
	{
		Cursor d=sdb.rawQuery("select party from parties",null);
		while(d.moveToNext())
		{
			alist.add(d.getString(0));
		}
		aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alist);
		setListAdapter(aa);
	}
	catch(Exception ex)
	{
		Toast.makeText(this,"Admin has not added any voting option"+ex.toString(),Toast.LENGTH_LONG).show();
	}
	
	//This is code for alert builder list
/*	ab.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Toast.makeText(Castvotes.this, "click OK to continue", Toast.LENGTH_LONG).show();
			choice=which;
		}
	});
	ab.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			String one="1";
			try
			{
			sdb.execSQL("insert into casting values('"+ids+"','"+arr[choice]+"')");
			//sdb.execSQL("update votes set status='"+one+"' where id='"+ids+"'");
			ContentValues cv=new ContentValues();
			cv.put("status", one);           //to write colm name(where update performed) and data(to be updated)in cv.Where cv is an object of type ContentValues.
			sdb.update("votes", cv, "id="+ids, null);
			Toast.makeText(Castvotes.this, "Successfully casted your vote to"+arr[choice], Toast.LENGTH_LONG).show();
			Intent i=new Intent(Castvotes.this,Profile.class);
			i.putExtra("idss", ids);
			startActivity(i);
			}
			catch(Exception e){ Toast.makeText(Castvotes.this, "Sorry Unable to cast your vote"+e.toString(), Toast.LENGTH_LONG).show();
}
		}
	});
	ab.show();	*/
	
	
}
@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
		voted=aa.getItem(position);
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setTitle("Vote Casting");
		ab.setMessage("Cast Vote To "+voted);
		ab.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String one="1";
				try
				{
				sdb.execSQL("insert into casting values('"+ids+"','"+voted+"')");
				ContentValues cv=new ContentValues();
				cv.put("status", one);           //to write colm name(where update performed) and data(to be updated)in cv.Where cv is an object of type ContentValues.
				sdb.update("votes", cv, "id="+ids, null);
				Toast.makeText(Castvotes.this, "Successfully Casted Your Vote To "+voted, Toast.LENGTH_LONG).show();
				finish();
				}
				catch(Exception e){ Toast.makeText(Castvotes.this, "Sorry Unable To Cast Your Vote"+e.toString(), Toast.LENGTH_LONG).show();
	}
			}
		});
		ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		ab.show();
	}
@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
	super.onRestart();
	finish();
	}
}

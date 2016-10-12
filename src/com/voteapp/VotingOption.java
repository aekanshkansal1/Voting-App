package com.voteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VotingOption extends Activity{
Button b1,b2;
EditText ed1;
SQLiteDatabase db;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.votingoption);
	b1=(Button)findViewById(R.id.button1);
	ed1=(EditText)findViewById(R.id.editText1);
	b2=(Button)findViewById(R.id.button2);
	db=openOrCreateDatabase("votings",Context.MODE_PRIVATE,null);
	try
	{
		db.execSQL("create table parties(party varchar(50) primary key)");
		Toast.makeText(VotingOption.this,"Table created Successfully",Toast.LENGTH_LONG).show();
	}
	catch(Exception ex){
//		Toast.makeText(VotingOption.this,"Parties table already exist"+ex.toString(),Toast.LENGTH_LONG).show();
		}

	b1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String add=ed1.getText().toString();
			try
			{
				Cursor c=db.rawQuery("Select party from parties where party='"+add+"'",null);
				if(!c.moveToNext())
				{
				db.execSQL("insert into parties values('"+add+"')");
				Toast.makeText(VotingOption.this,"Party Added Successfully",Toast.LENGTH_LONG).show();
				ed1.setText("");
				}
				else
				{
					AlertDialog.Builder ab=new AlertDialog.Builder(VotingOption.this);
					ab.setTitle("Notice");
					ab.setMessage("Party Already Added In List");
					ab.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					ab.show();

				}
			}
			catch(Exception ex){Toast.makeText(VotingOption.this,"Parties can't be added"+ex.toString(),Toast.LENGTH_LONG).show(); }

		}
	});
	b2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(VotingOption.this,ManageParty.class);
			startActivity(i);
		}
	});
}
}

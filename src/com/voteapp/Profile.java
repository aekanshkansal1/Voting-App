package com.voteapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity{
TextView tv1,tv2,tv3,tv4,tv5;
SQLiteDatabase dbs;
Button b1;
String name,age,gender,status,s;
	@Override
protected void onCreate(Bundle b) {
	// TODO Auto-generated method stub
	super.onCreate(b);
	setContentView(R.layout.profile);
	Intent i=getIntent();
	b=i.getExtras();
	s=b.getString("idss");
	tv1=(TextView)findViewById(R.id.TextView1);
	tv2=(TextView)findViewById(R.id.TextView2);
	tv3=(TextView)findViewById(R.id.TextView3);
	tv4=(TextView)findViewById(R.id.TextView4);
	tv5=(TextView)findViewById(R.id.TextView5);
	b1=(Button)findViewById(R.id.button1);
	tv1.setText("ID: "+s);
	dbs=openOrCreateDatabase("votings", Context.MODE_PRIVATE, null);
	try
	{
	Cursor d=dbs.rawQuery("SELECT * FROM VOTES WHERE id='"+s+"'",null);
	if(d.moveToNext())
	{
		 name=d.getString(0);
		 age=d.getString(2);
		 gender=d.getString(3);
		 status=d.getString(5);		 
	}
	}
	catch(Exception ex)
	{
		Toast.makeText(Profile.this,ex.toString(),Toast.LENGTH_LONG).show();
	}
	tv2.setText("Name: "+name);
	tv3.setText("Age: "+age);
	tv4.setText("Gender: "+gender);
	if(!status.equals("0"))
	{
		String ptname;
//		b1.setClickable(true);
		b1.setEnabled(false);       //to enable or disable a button
		try
		{
		Cursor p=dbs.rawQuery("select party from casting where id="+s, null);
		if(p.moveToNext())
		{
			ptname=p.getString(0);
			tv5.setText("Status: You have voted "+ptname);
		}
		else
		{
			Toast.makeText(Profile.this,"No entry found", Toast.LENGTH_LONG).show();
		}
		}
		catch(Exception ex){ Toast.makeText(Profile.this, "Error in getting your status"+ex.toString(), Toast.LENGTH_LONG).show();
		}
	}
	else
	{
		b1.setEnabled(true);
		tv5.setText("Status: You Have Not Voted Yet");
//		b1.setClickable(false);
	}
	b1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(Profile.this,Castvotes.class);
			i.putExtra("idss",s);
			startActivity(i);

		}
	});
}
	@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
		super.onRestart();
		finish();
		}
}
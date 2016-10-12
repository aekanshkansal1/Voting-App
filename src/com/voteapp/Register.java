package com.voteapp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Register extends Activity{

	EditText ed1,ed2,ed3;
	RadioGroup rg;
	String gender="male",id1;
	Button b1;
	SQLiteDatabase db;
	int ids;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.register);
	ed1=(EditText)findViewById(R.id.editText1);
	ed2=(EditText)findViewById(R.id.editText2);
	ed3=(EditText)findViewById(R.id.editText3);	
	rg=(RadioGroup)findViewById(R.id.radioGroup1);
	rg.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	{			
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if(checkedId==R.id.radio0)
			{
				gender="male";
			}
			else
			{
				gender="female";
			}
		}
	});

	db=openOrCreateDatabase("votings",Context.MODE_PRIVATE, null);
	try
	{
		db.execSQL("create table votes(name varchar(30),pass varchar(30),age varchar(30),gender varchar(10),id varchar(30),status varchar(2));");
		Toast.makeText(Register.this,"Table created",Toast.LENGTH_LONG).show();
	}
	catch(Exception e)
	{
//		Toast.makeText(Register.this,"Table not created"+e.toString(),Toast.LENGTH_LONG).show(); 
	}

			b1=(Button)findViewById(R.id.button1);
			b1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String nm=ed1.getText().toString();
					String age=ed2.getText().toString();
					String ps=ed3.getText().toString();
					if(nm.equals("") || ps.equals("") || age.equals(""))
					{
						AlertDialog.Builder ab=new AlertDialog.Builder(Register.this);
						ab.setTitle("Notice");
						ab.setMessage("Please Fill out mandatory Fields");
						ab.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
						ab.show();						
					}
					else if(Integer.parseInt(age)<18)
					{
						AlertDialog.Builder ab=new AlertDialog.Builder(Register.this);
						ab.setTitle("Notice");
						ab.setMessage("You can't vote before 18yrs of Age");
						ab.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
						ab.show();
					}
				else
				{
					String st="0";
					Cursor c=db.rawQuery("select max(id) from votes",null);
					try{
					if(c.moveToNext())
					{
						ids=Integer.parseInt(c.getString(0));
						ids=ids+1;
						id1=""+ids;
					}
					}catch(Exception ex)
					{
						ids=10000;
						id1=""+ids;
					}
					db.execSQL("insert into votes values('"+nm+"','"+ps+"','"+age+"','"+gender+"','"+id1+"','"+st+"')");
					Toast.makeText(Register.this, "record inserted", Toast.LENGTH_LONG).show();
					finish();
					}
					
				}
				});
}
}

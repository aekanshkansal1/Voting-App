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
import android.widget.RadioGroup;
import android.widget.Toast;

public class Login extends Activity{
SQLiteDatabase db;
Button b1;
EditText ed1,ed2;
String uname,password;
RadioGroup rg;
String mode="";

	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.login);
	AlertDialog.Builder ab=new AlertDialog.Builder(this);
	ab.setTitle("Admin Credentials");
	ab.setMessage("Name: admin"+"\n"+"password: admin");
	ab.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	});
	ab.show();
	db=openOrCreateDatabase("votings",Context.MODE_PRIVATE, null);
	try
	{
		db.execSQL("create table votes(name varchar(30),pass varchar(30),age varchar(30),gender varchar(10),id varchar(30),status varchar(2));");
		Toast.makeText(Login.this,"Table created",Toast.LENGTH_LONG).show();
	}
	catch(Exception e)
	{
//		Toast.makeText(Login.this,"Table not created"+e.toString(),Toast.LENGTH_LONG).show(); 
	}

	b1=(Button)findViewById(R.id.button1);
	ed1=(EditText)findViewById(R.id.editText1);
	ed2=(EditText)findViewById(R.id.editText2);
	rg=(RadioGroup)findViewById(R.id.radioGroup1);
	rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if(checkedId==R.id.radio0)
			{
				mode="User";
			}
			if(checkedId==R.id.radio1)
			{
				mode="Admin";
			}
		}
	});
	b1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			uname=ed1.getText().toString();
			password=ed2.getText().toString();
			if(mode.equals("Admin"))
			{
				if(uname.equals("admin") && password.equals("admin"))
				{
					Intent i=new Intent(Login.this,Admin.class);
					startActivity(i);
				}
			}
			else
			{
			try
			{
			Cursor c=db.rawQuery("Select id from votes where name='"+uname+"'and pass='"+password+"'",null);
			if(c.moveToNext())
			{
				String ids=c.getString(0);
				Intent i=new Intent(Login.this,Profile.class);
				i.putExtra("idss",ids);
				startActivity(i);
			}
			else
			{
				AlertDialog.Builder bc=new AlertDialog.Builder(Login.this);
				bc.setTitle("Notice");
				bc.setMessage("Invalid Login Credentials");
				bc.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				bc.show();
				ed2.setText("");
			}
			}catch(Exception ex)
			{
				Toast.makeText(Login.this,ex.toString(), Toast.LENGTH_LONG).show();
			}
		}
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

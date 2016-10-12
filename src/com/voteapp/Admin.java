package com.voteapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends Activity{
	Button b1,b2,b3;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.admin);
	b1=(Button)findViewById(R.id.button1);
	b2=(Button)findViewById(R.id.button2);
	b3=(Button)findViewById(R.id.button3);
	b1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(Admin.this,Count.class);
			startActivity(i);
		}
	});
	b2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(Admin.this,ManageUser.class);
			startActivity(i);
		}
	});
	b3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(Admin.this,VotingOption.class);
			startActivity(i);
		}
	});
}
}

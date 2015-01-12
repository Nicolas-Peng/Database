package com.LZP.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Add_member extends Activity {
	private EditText ssn,name,_class,phone;
	private Button confirm;
	private MyDatabaseHelper dao=new MyDatabaseHelper(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_member);
		
		ssn=(EditText)findViewById(R.id.e_ssn);
		name=(EditText)findViewById(R.id.e_name);
		_class=(EditText)findViewById(R.id.e_class);
		phone=(EditText)findViewById(R.id.e_phone);
		confirm=(Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String t_ssn=ssn.getText().toString();
				String t_name=name.getText().toString();
				Log.d("aaaaaaa","aaaaaa");
				String t_class=_class.getText().toString();
				Log.d("bbbbbbb","bbbbbb");
				String t_phone=phone.getText().toString();
				dao.insert2(t_ssn,t_name,t_class,t_phone);
				//dao.query();
				Intent intent=new Intent(Add_member.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		
		
	}
	
}

package com.LZP.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private List<Map<String, Object>> mDataList = new ArrayList<Map<String, Object>>();
	ListView people_list;
	private Button add;
	private static final String TABLE_NAME = "Contacts";
	private MyDatabaseHelper db=new MyDatabaseHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		add = (Button) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Add_member.class);
				startActivityForResult(intent, 1);
			}
		});

		people_list = (ListView) findViewById(R.id.people_list);
		updatelist();
	
		people_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("CallLogActivity",view.toString()+"position="+position);
				Toast t = Toast.makeText(MainActivity.this, "is long clicked",
					    Toast.LENGTH_LONG);
				t.show();
				TextView tx=(TextView)view.findViewById(R.id.ssn);
				final Integer _id=Integer.valueOf(tx.getText().toString());
				tx=(TextView)view.findViewById(R.id.name);
				String name=tx.getText().toString();
				
				//弹出确认对话框
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				//设置标题
				String s=String.format(getResources().getString(R.string.confirm));
				builder.setTitle(s);
				//设置按钮
				builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						deleteMember(_id.toString());
						updatelist();
					}
				}).setNegativeButton("否", null);
				//显示！
				builder.show();
				return false;
			}
		});
		people_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				//View view1=View.inflate(MainActivity.this,R.layout.change,null); 		
				 TextView tv=(TextView)arg1.findViewById(R.id.ssn);
				 final String change_ssn=tv.getText().toString();
				 final AlertDialog MyDialog=new AlertDialog.Builder(MainActivity.this).create();
				 MyDialog.show();
				 Window window=MyDialog.getWindow();//主要是在这里实现自定义弹窗的效果
				 window.setContentView(R.layout.change);
				 Button update=(Button)window.findViewById(R.id.update);
				 Button cancel=(Button)window.findViewById(R.id.cancel);
				 cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyDialog.cancel();
					}
				});
				 final EditText _ssn=(EditText)window.findViewById(R.id.e_ssn);  
				 final EditText _name=(EditText)window.findViewById(R.id.e_name);
				 final EditText _class=(EditText)window.findViewById(R.id.e_class);
				 final EditText _phone=(EditText)window.findViewById(R.id.e_phone);
				update.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String t_ssn=_ssn.getText().toString();
						String t_name=_name.getText().toString();		
						String t_class=_class.getText().toString();
						String t_phone=_phone.getText().toString();
						System.out.println(t_name);
						System.out.println(t_class);
						MyDialog.cancel();
						MyDatabaseHelper db=new MyDatabaseHelper(MainActivity.this);
						db.update(change_ssn, t_name, t_class, t_phone);
						updatelist();
					}
				});
				
			}
		});
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	private void updatelist(){
		
		Cursor c=db.query();
		@SuppressWarnings("deprecation")
		ListAdapter adapter=new SimpleCursorAdapter(this, R.layout.item, c, 
				new String[]{"ssn", "name", "_class", "phone"}, 
				new int[] { R.id.ssn, R.id.name, R.id._class, R.id.phone });
		people_list.setAdapter(adapter);
	}
	
 //删除数据库编号为id的成员
	public void deleteMember(String id){
		MyDatabaseHelper dao=new MyDatabaseHelper(this);
		dao.deleteById(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

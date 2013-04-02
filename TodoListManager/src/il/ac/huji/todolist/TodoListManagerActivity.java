package il.ac.huji.todolist;


import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class TodoListManagerActivity extends Activity {
	TaskDisplayAdapter adapter;
	private SQLiteDatabase db;
	private Cursor cursor;
	TodoDAL todo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		ListView listTasks = 
				(ListView)findViewById(R.id.lstTodoItems);
		registerForContextMenu(listTasks);


		SqlHalper helper = new SqlHalper(this);
		db = helper.getWritableDatabase();


		cursor = db.query("todo",
				new String[] { "_id", "title", "due" },
				null, null, null, null, null);
		String[] from = { "title", "due" };
		int[] to = { R.id.txtTodoTitle, R.id.txtTodoDueDate };
		adapter = new TaskDisplayAdapter(this,
				cursor, from, to);
		listTasks.setAdapter(adapter);

		todo=new TodoDAL(this);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu); 
		return true; 
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.contextmenu, menu);

		AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
		int selectedItemIndex =info.position;
		Cursor cursor = (Cursor)adapter.getItem(selectedItemIndex);
		cursor.moveToPosition(selectedItemIndex);
		menu.setHeaderTitle(cursor.getString(1));
		if(cursor.getString(1).startsWith("Call ")){
			menu.findItem(R.id.menuItemCall).setTitle(cursor.getString(1));

		}
		else{
			menu.removeItem(R.id.menuItemCall);
		}

	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)
				item.getMenuInfo();
		int selectedItemIndex = info.position;
		Cursor cursor1 = (Cursor)adapter.getItem(selectedItemIndex);

		switch (item.getItemId()){
		case R.id.menuItemDelete:
			cursor1.moveToPosition(selectedItemIndex);
			String toRemove=cursor1.getString(1);
			todo.delete(new Task(toRemove,new Date(cursor1.getLong(2))));
			cursor.requery();
			break;
			
		case R.id.menuItemCall:
			String phone=cursor1.getString(1).replace("Call ", "tel:");
			Intent dial = new Intent(Intent.ACTION_DIAL, 
					Uri.parse(phone));
			startActivity(dial);
			break;
		}
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1337 && resultCode == RESULT_OK) {
			String taskName = data.getStringExtra("title");
			Date taskDate=(Date) data.getSerializableExtra("dueDate");
			todo.insert(new Task(taskName,taskDate));
			cursor.requery();
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemAdd:
			Intent intent = new Intent(this, AddNewTodoItemActivity.class);
			startActivityForResult(intent, 1337);
			break;
		}
		return true;

	}

}

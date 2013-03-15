package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
	ArrayAdapter<Task> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_todo_list_manager);
		  ListView listTasks = 
	        		(ListView)findViewById(R.id.lstTodoItems);
	        List<Task> tasks = new ArrayList<Task>();
	        registerForContextMenu(listTasks);
	        
	        adapter = 

			new TaskDisplayAdapter(this, tasks);
	        listTasks.setAdapter(adapter);
	         
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
		Task t=adapter.getItem(selectedItemIndex);
		menu.setHeaderTitle(t._name);
		if(t._name.startsWith("Call ")){
			menu.add(0,R.id.menuItemDeleteCall, 0, t._name);
		}
		
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)
				item.getMenuInfo();
		int selectedItemIndex = info.position;
		switch (item.getItemId()){
		case R.id.menuItemDelete:
			adapter.remove(adapter.getItem(selectedItemIndex));
			break;
		case R.id.menuItemDeleteCall:
			Task t=adapter.getItem(selectedItemIndex);
			String phone=t._name.replace("Call ", "tel:");
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
//    		int year=data.getIntExtra("year",2000);
//    		int month=data.getIntExtra("month",1);
//    		int day=data.getIntExtra("day",1);
    		Date taskDate=(Date) data.getSerializableExtra("dueDate");

    		adapter.add(new Task(taskName, taskDate));
    	}
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menuItemAdd:
    		
    		Intent intent = new Intent(this, AddNewTodoItemActivity.class);
    		startActivityForResult(intent, 1337);
    		
//    		EditText taskName=(EditText)findViewById(R.id.edtNewItem);
//    		adapter.add(new Task(String.valueOf(taskName.getText())));
    		break;
    	
//    	case R.id.menuItemDelete:
//    		  ListView listTasks = 
//      		(ListView)findViewById(R.id.lstTodoItems);
//    		  Task t=(Task) listTasks.getItemAtPosition(listTasks.getSelectedItemPosition());
//    		adapter.remove(t);
// 
//    		break;
    	}
    	return true;
    	
    }

}

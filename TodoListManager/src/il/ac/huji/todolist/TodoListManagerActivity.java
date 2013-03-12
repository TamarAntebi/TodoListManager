package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class TodoListManagerActivity extends Activity {
	ArrayAdapter<Task> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_todo_list_manager);
		  ListView listTasks = 
	        		(ListView)findViewById(R.id.lstTodoItems);
	        List<Task> tasks = new ArrayList<Task>();
	        
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
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menuItemAdd:
    		EditText taskName=(EditText)findViewById(R.id.edtNewItem);
    		adapter.add(new Task(String.valueOf(taskName.getText())));
    		break;
    	
    	case R.id.menuItemDelete:
    		  ListView listTasks = 
      		(ListView)findViewById(R.id.lstTodoItems);
    		  Task t=(Task) listTasks.getItemAtPosition(listTasks.getSelectedItemPosition());
    		adapter.remove(t);
 
    		break;
    	}
    	return true;
    	
    }

}

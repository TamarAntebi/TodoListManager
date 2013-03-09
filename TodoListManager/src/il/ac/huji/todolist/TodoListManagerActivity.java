package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class TodoListManagerActivity extends Activity {
	ArrayAdapter<Task> adapter;
	Task toDel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_todo_list_manager);
		 final ListView listTasks = 
	        		(ListView)findViewById(R.id.lstTodoItems);
		 listTasks.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View view,
		                int position, long id) {

		       Object o = listTasks.getItemAtPosition(position);
		       toDel=(Task) o;
		       }
		    });
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
    		adapter.remove(toDel);
 
    		break;
    	}
    	return true;
    	
    }

}

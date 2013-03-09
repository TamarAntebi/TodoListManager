package il.ac.huji.todolist;



import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskDisplayAdapter extends ArrayAdapter<Task> {
	public TaskDisplayAdapter(
			TodoListManagerActivity activity, List<Task> courses) {
		super(activity, android.R.layout.simple_list_item_1, courses);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Task course = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.row, null);
		TextView txtName = (TextView)view.findViewById(R.id.txtName);
		txtName.setText(course._name);
		if(position%2==0){
			txtName.setTextColor(Color.RED);
		}
		else{
			txtName.setTextColor(Color.BLUE);
		}
		return view;
	}
}
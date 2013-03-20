package il.ac.huji.todolist;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

		Task task = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.tasklayout, null);
		TextView txtName = (TextView)view.findViewById(R.id.txtTodoTitle);
		txtName.setText(task._name);
		TextView txtDate = (TextView)view.findViewById(R.id.txtTodoDueDate);
		String today;
		if(task._date==null){
			today="No due date";
			txtDate.setText(today);
		}
		else{
			SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
			today = formatter.format(task._date);

			txtDate.setText(today);

			Date d=new Date();
			boolean red=false;
			
			if(d.after(task._date)){
				red=true;
			}
//			else if(d.getYear()==task._date.getYear()){
//				if(d.getMonth()>task._date.getMonth()){
//					red=true;
//				}
//				else if(d.getMonth()==task._date.getMonth()){
//					if(d.getDay()>task._date.getDay()){
//						red=true;
//					}
//				}
//			}

			if(red==true){
				txtDate.setTextColor(Color.RED);
				txtName.setTextColor(Color.RED);
			}
		}

		return view;
	}
}
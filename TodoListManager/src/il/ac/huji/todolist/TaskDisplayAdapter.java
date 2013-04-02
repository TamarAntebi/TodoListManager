package il.ac.huji.todolist;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskDisplayAdapter extends SimpleCursorAdapter {
	private Context _cntxt;

	public TaskDisplayAdapter(Context cntxt,
			Cursor cursor,  String[] from, int[] to) {

		super(cntxt,R.layout.tasklayout, cursor, from, to);

		_cntxt=cntxt;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Cursor cursor = (Cursor) getItem(position);
		cursor.moveToPosition(position);
		LayoutInflater inflater = (LayoutInflater) _cntxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.tasklayout, null);
		TextView txtName = (TextView)view.findViewById(R.id.txtTodoTitle);
		txtName.setText(cursor.getString(1));

		TextView txtDate = (TextView)view.findViewById(R.id.txtTodoDueDate);
		String today;
		Long date=cursor.getLong(2);
		if(date==-1){
			today="No due date";
			txtDate.setText(today);
		}

		else{
			Date d=new Date(date);
			SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
			today = formatter.format(d);

			txtDate.setText(today);

			Date todayD=new Date();
			boolean red=false;

			if(todayD.after(d)){
				red=true;
			}

			if(red==true){
				txtDate.setTextColor(Color.RED);
				txtName.setTextColor(Color.RED);
			}
		}

		return view;
}
	}
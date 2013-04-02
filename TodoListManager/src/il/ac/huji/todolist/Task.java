package il.ac.huji.todolist;

import java.util.Date;

public class Task implements ITodoItem{
	private String _name;
	private Date _date;
	public Task(String name,Date date) {
		_name=name;
		_date=date;
		
	}
	@Override
	public String getTitle() {
		return _name;
	}
	@Override
	public Date getDueDate() {
		return _date;
	}
}

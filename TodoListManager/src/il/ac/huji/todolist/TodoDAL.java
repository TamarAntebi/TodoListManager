package il.ac.huji.todolist;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

public class TodoDAL {
	Context _ctxt;
	private SQLiteDatabase db;
	private Cursor cursor;
	private TaskDisplayAdapter adapter;
	boolean delete=true;
	protected boolean update;
	public TodoDAL(Context context) { 
		_ctxt=context;
		Parse.initialize(_ctxt, "OYwh9gQOASbvVSHZXBxEVUn4BReESL9UlaE1PFVZ", "hAId94nymDkw7Q5AKTH5JUwDDEnHNM3Iurk526Xw");
		SqlHalper helper = new SqlHalper(_ctxt);
		db = helper.getWritableDatabase();
		ParseUser.enableAutomaticUser();
		

	}
	public boolean insert(ITodoItem todoItem) { 
		ParseObject parseObject = new ParseObject("todo");
		parseObject.put("title", todoItem.getTitle());
		parseObject.put("due", todoItem.getDueDate().getTime());
		parseObject.saveInBackground();
		ContentValues values = new ContentValues();
		values.put("title", todoItem.getTitle());
		if(!(todoItem.getDueDate().equals(null))){
			values.put("due", todoItem.getDueDate().getTime());
		}
		else{
			values.put("due", -1);
		}
		long succ=db.insert("todo", null, values);
		if(succ==-1){
			return false;
		}
		return true;

	}
	public boolean update( ITodoItem todoItem) { 
		final ITodoItem t=todoItem;
		ParseQuery query = new ParseQuery("todo");
		query.whereEqualTo("title", todoItem.getTitle());
		update=true;
		query.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException arg1) {
				if (arg1!=null){
					arg1.printStackTrace();
				}
				else{
					if(objects.isEmpty()){
						update=false;
						return ;
					}
					for(ParseObject obj : objects){
						obj.put("due",t.getDueDate().getTime());
						try {
							obj.save();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						update=true;
					}
				}
			}
		});
		ContentValues values = new ContentValues();
		if(!(todoItem.getDueDate().equals(null))){
			values.put("due", todoItem.getDueDate().getTime());
		}
		else{
			values.put("due", -1);
		}
		int succ = db.update("todo",values , "title=?", new String[]{todoItem.getTitle()});
		if(succ==-1){
			return false;
		}
		return update;

	}
	public boolean delete(ITodoItem todoItem) {
	
		
		ParseQuery query = new ParseQuery("todo");
		query.whereEqualTo("title", todoItem.getTitle());
		delete=true;
		query.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> objects, ParseException arg1) {
				if (arg1!=null){
					arg1.printStackTrace();
				}
				else{
					if(objects.isEmpty()){
						delete=false;
						return;
					}
					for(ParseObject obj : objects){
						try{
							obj.delete();
							
						}catch(ParseException e){
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		int succ = db.delete("todo","title=?",new String[]{todoItem.getTitle()});
		if(succ==-1){
			return false;
		}
		return delete;
		

	}
	public List<ITodoItem> all() { 
		ParseQuery query = new ParseQuery("todo");
		List<ITodoItem> list = new ArrayList<ITodoItem>();
			
		try {
			for (ParseObject i :query.find()){
				list.add(new Task(i.getString("title"),new Date(i.getInt("due"))));
				
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return list;
	}
}

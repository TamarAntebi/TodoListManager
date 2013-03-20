package il.ac.huji.todolist;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTodoItemActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText edtCourseName = (EditText)findViewById(R.id.edtNewItem);
				String taskName = edtCourseName.getText().toString();
				DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
				//				Date taskDate=new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
				if (taskName == null || "".equals(taskName)) {
					setResult(RESULT_CANCELED);
					finish();
				} else {
					Intent resultIntent = new Intent();
					resultIntent.putExtra("title", taskName);
					Date taskDate=new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
					resultIntent.putExtra("dueDate",taskDate );

					setResult(RESULT_OK, resultIntent);
					finish();
				}
			}
		});
		findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

}

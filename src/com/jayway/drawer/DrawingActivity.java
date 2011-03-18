package com.jayway.drawer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jayway.drawer.model.Drawing;
import com.jayway.drawer.model.DrawingRepository;

public class DrawingActivity extends Activity {

	public static final String DRAWING_EXTRA = "DRAWING_EXTRA";

	private static final int NAME_DIALOG = 1000;

	private DrawingView drawingView;
	private Drawing drawing;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawing_activity);
		drawingView = (DrawingView) findViewById(R.id.drawing);
		drawing = (Drawing) getIntent().getSerializableExtra(DRAWING_EXTRA);
		if (drawing == null) {
			drawing = new Drawing("");
		} else if (drawing.getLines() == null){
			DrawingRepository.getInstance(getApplicationContext()).getDrawing(drawing.getId());
		}
		drawingView.setDrawing(drawing);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Drawing Name");
		builder.setMessage("Please give your drawing a name:");
		final EditText input = new EditText(this);
		builder.setView(input);
		builder.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				drawing.setName(input.getText().toString());
				saveDrawing(null);
			}
		});
		builder.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		return builder.create();
	}

	public void saveDrawing(View view) {
		if (TextUtils.isEmpty(drawing.getName())) {
			showDialog(NAME_DIALOG);
		} else {
			DrawingRepository.getInstance(getApplicationContext()).saveDrawing(drawing);
			Toast.makeText(this, "Saving", Toast.LENGTH_SHORT).show();
		}
	}
}
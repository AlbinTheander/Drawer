package com.jayway.drawer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.jayway.drawer.model.Drawing;
import com.jayway.drawer.model.DrawingRepository;
import com.jayway.drawer.model.Gallery;

public class GalleryActivity extends ListActivity implements
		OnItemClickListener {

	private ArrayAdapter<Drawing> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_list);
		DrawingRepository repository = DrawingRepository.getInstance(getApplicationContext());
		Gallery gallery = repository.getGallery();
		adapter = new ArrayAdapter<Drawing>(this,
				android.R.layout.simple_list_item_1, gallery.getDrawings());
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		registerForContextMenu(getListView());
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle(getListAdapter().getItem(info.position).toString());
		menu.add("Delete Drawing");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().equals("Delete Drawing")) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();		
			Drawing drawing = adapter.getItem(info.position);
			DrawingRepository.getInstance(getApplicationContext()).deleteDrawing(drawing);
			adapter.notifyDataSetChanged();
			return true;
		}
		return false;
	}

	public void createDrawing(View view) {
		openDrawing(null);
	}

	@Override
	public void onItemClick(AdapterView<?> listView, View itemView,
			int position, long id) {
		Drawing drawing = adapter.getItem(position);
		openDrawing(drawing);
	}

	private void openDrawing(Drawing drawing) {
		Intent intent = new Intent(this, DrawingActivity.class);
		intent.putExtra(DrawingActivity.DRAWING_EXTRA, drawing);
		startActivity(intent);
	}

}

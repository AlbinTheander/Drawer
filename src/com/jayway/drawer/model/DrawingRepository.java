package com.jayway.drawer.model;

import com.jayway.drawer.R;

import android.content.Context;

public abstract class DrawingRepository {

	private static DrawingRepository instance;

	public static DrawingRepository getInstance(Context context) {
		if (instance == null) {
//			instance = new InMemoryRepository();
			instance = new NetworkRepository(context.getString(R.string.drawer_base_url));
		}
		return instance;
	}

	public abstract void deleteDrawing(Drawing drawing);

	public abstract void saveDrawing(Drawing drawing);

	public abstract Drawing getDrawing(int id);

	public abstract Gallery getGallery();

}
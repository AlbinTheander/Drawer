package com.jayway.drawer.model;

import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class InMemoryRepository extends DrawingRepository {
	
	private Gallery gallery;
	
	InMemoryRepository() {
	}
	
	@Override
	public Gallery getGallery() {
		if (gallery == null) {
			gallery = new Gallery();
		}
		return gallery;
	}
	
	@Override
	public Drawing getDrawing(int id) {
		List<Drawing> drawings = gallery.getDrawings();
		for(Drawing d: drawings) {
			if (d.getId() == id) {
				return d;
			}
		}
		return null;
	}
	
	@Override
	public void saveDrawing(Drawing drawing) {
		Gallery gallery = getGallery();
		if (gallery.contains(drawing)) {
			gallery.updateDrawing(drawing);
		} else {
			gallery.addDrawing(drawing);
		}
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		System.out.println(gson.toJson(gallery));
	}

	@Override
	public void deleteDrawing(Drawing drawing) {
		gallery.deleteDrawing(drawing);
	}

}

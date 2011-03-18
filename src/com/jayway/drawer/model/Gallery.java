package com.jayway.drawer.model;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

	private List<Drawing> drawings = new ArrayList<Drawing>();

	public void addDrawing(Drawing drawing) {
		drawings.add(drawing);
	}

	public List<Drawing> getDrawings() {
		return drawings;
	}

	public boolean contains(Drawing drawing) {
		return drawings.contains(drawing);
	}

	public void updateDrawing(Drawing drawing) {
		int index = drawings.indexOf(drawing);
		if (index >= 0) {
			drawings.set(index, drawing);
		}
	}

	public void deleteDrawing(Drawing drawing) {
		drawings.remove(drawing);
	}

}

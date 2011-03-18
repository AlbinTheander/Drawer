package com.jayway.drawer.model;

import java.io.Serializable;

public class Line implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public float x1, y1, x2, y2;
	
	@SuppressWarnings("unused")
	private Line() {
		
	}

	public Line(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public float[] getPoints(float xScale, float yScale) {
		return new float[] {x1*xScale, y1*yScale, x2*xScale, y2*yScale}; 
	}
	
	public float[] getPoints() {
		return getPoints(1, 1);
	}

	public void normalize(float xScale, float yScale) {
		x1 /= xScale;
		y1 /= yScale;
		x2 /= xScale;
		y2 /= yScale;
	}
	
	

}

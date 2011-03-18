package com.jayway.drawer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Drawing implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;

	private String name;
	
	private List<Line> lines = new ArrayList<Line>();
	
	@SuppressWarnings("unused")
	private Drawing() {
		
	}
	
	public Drawing(String name) {
		this.name = name;
		id = (int) (Math.random() * Integer.MAX_VALUE);
	}
	
	public void addLine(Line line) {
		lines.add(line);
	}
	
	public Iterable<Line> getLines() {
		return lines;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drawing other = (Drawing) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}

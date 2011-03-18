package com.jayway.drawer;

import com.jayway.drawer.model.Drawing;
import com.jayway.drawer.model.Line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	private Drawing drawing;
	private Line currentLine;

	public DrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawingView(Context context) {
		super(context);
	}
	
	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}
	
	public Drawing getDrawing() {
		return drawing;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (drawing == null)
			return;
		float xScale = getWidth() / 100.0f;
		float yScale = getHeight() / 100.0f;
		canvas.drawRGB(0xff, 0xff, 0xff);
		Paint blackLine = new Paint();
		blackLine.setColor(Color.BLACK);
		blackLine.setStrokeWidth(10);
		blackLine.setStrokeCap(Paint.Cap.ROUND);
		for (Line line: drawing.getLines()) {
			canvas.drawLines(line.getPoints(xScale, yScale), blackLine);
		}
		if (currentLine != null) {
			canvas.drawLines(currentLine.getPoints(), blackLine);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			currentLine = new Line(x, y, x, y);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			currentLine.x2 = x;
			currentLine.y2 = y;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			currentLine.normalize(getWidth() / 100f, getHeight() / 100f);
			drawing.addLine(currentLine);
			currentLine = null;
		}
		this.invalidate();
		return true;
	}

}

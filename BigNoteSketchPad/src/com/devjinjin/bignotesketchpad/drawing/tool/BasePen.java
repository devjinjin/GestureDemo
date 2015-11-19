package com.devjinjin.bignotesketchpad.drawing.tool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public abstract class BasePen {
	
	protected Paint mDrawingPaint;
	protected Path mPath;
	protected static final float TOUCH_TOLERANCE = 1;
	protected float mX, mY;
	
	public abstract Paint getPaint();
	public abstract Path getPath();
	public abstract void setPenColor(int pPenColor);
	public abstract int getThickness();
	public abstract void setThickness(int pThickness);
	public abstract boolean drawTouch(Canvas pCanvas, MotionEvent event);
//	public abstract void changeThinkness(int pThinkness);
}

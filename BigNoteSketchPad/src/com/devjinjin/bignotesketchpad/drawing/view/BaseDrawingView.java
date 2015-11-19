package com.devjinjin.bignotesketchpad.drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.devjinjin.bignotesketchpad.drawing.tool.BasePenAction;
import com.devjinjin.bignotesketchpad.drawing.tool.Eraser;

public abstract class BaseDrawingView extends View {
	// 공통
	protected Canvas mCanvas;
	protected Bitmap mBitmap;
	protected int width;
	protected int height;
	protected Path mPath;
	protected Context context;

	// 그리기
	protected BasePenAction mPen = null;

	// 지우기
	protected boolean isErase = false;
	protected Eraser mEraser = null;

	public BaseDrawingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public BaseDrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public BaseDrawingView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public abstract void init(); // 초기값 설정
	// public abstract boolean onTouchEventBySync(MotionEvent motionEvent);

	public abstract void startDrawing();

	public abstract void startEraser();

	public abstract void setDefaultCanvas();

	public abstract void setPenColor(int pPenColor);

	public abstract void setEraserSize(int pSize);

	public abstract void setThickness(int pPenThickness);

	public abstract int getThickness();

	public abstract int getPenColor();

	public abstract void setBackgroundImage(int mDrawable);
	public abstract int getBackgroundImage();
	
	public abstract boolean isEraseMode();

	public abstract int getPenStyle();

	public abstract void setPenStyle(int penStyle);
}

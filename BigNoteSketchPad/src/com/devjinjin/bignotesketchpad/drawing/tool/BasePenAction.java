package com.devjinjin.bignotesketchpad.drawing.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class BasePenAction extends Object {

	private HighLightPen mHightLightPen;
	private InkPen mInkPen;
	private NormalPen mNoramlPen;
	private Pencil mPencil;
	private int mCurrentType = 0;
	public static int DEFALUT_TICKNESS = 12;
	public static int DEFALUT_COLOR = Color.BLACK;
	public int mCurrentTickness = DEFALUT_TICKNESS;
	public int mCurrentColor = DEFALUT_COLOR;

	public BasePenAction(Context pContext) {

		DisplayMetrics metrics = pContext.getResources().getDisplayMetrics();
		float mDensity = (metrics.xdpi + metrics.ydpi) / 2f;
		mNoramlPen = new NormalPen();
		mInkPen = new InkPen(mDensity);
		mPencil = new Pencil();
		mHightLightPen = new HighLightPen();
	}

	public int getCurrentType() {
		return mCurrentType;
	}

	public void setCurrentType(int pCurrentType) {
		mCurrentType = pCurrentType;
	}

	public boolean drawTouch(Canvas pCanvas, MotionEvent event) {
		getCurrentPen().drawTouch(pCanvas, event);
		return true;
	}

	public Paint getCurrentPaint() {
		Paint paint = getCurrentPen().getPaint();
		return paint;
	}

	public Path getCurrentPath() {
		Path path = getCurrentPen().getPath();
		return path;
	}

	public int getCurrentThickness() {
		getCurrentPen().getThickness();
		return mCurrentTickness;
	}

	public void setThickness(int pThickness) {
		mCurrentTickness = pThickness;
		getCurrentPen().setThickness(mCurrentTickness);
	}

	public int getCurrentColor() {
		return mCurrentColor;

	}

	public void setPenColor(int pPenColor) {
		mCurrentColor = pPenColor;
		mNoramlPen.setPenColor(mCurrentColor);
		mInkPen.setPenColor(mCurrentColor);
		mPencil.setPenColor(mCurrentColor);
		mHightLightPen.setPenColor(mCurrentColor);
	}

	public BasePen getCurrentPen() {
		BasePen mPen = null;
		switch (mCurrentType) {
		case 0:
			mPen = mNoramlPen;
			break;
		case 1:
			mPen = mInkPen;
			break;
		case 2:
			mPen = mPencil;
			break;
		case 3:
			mPen = mHightLightPen;
			break;
		default:
			break;
		}

		if (mPen == null) {
			mPen = mNoramlPen;
		}
		return mPen;
	}
}

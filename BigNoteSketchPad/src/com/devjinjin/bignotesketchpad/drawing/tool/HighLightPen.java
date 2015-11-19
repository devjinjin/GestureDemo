package com.devjinjin.bignotesketchpad.drawing.tool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class HighLightPen extends BasePen {

	public final int DEFALUT_TICKNESS = 30;
	
	public HighLightPen() {
		mPath = new Path();
		// ±×¸®±â
		mDrawingPaint = new Paint();
		mDrawingPaint.setAntiAlias(true);
		mDrawingPaint.setDither(true);
		// getDrawingPaint().setAlpha(100);
		int red = Color.red(BasePenAction.DEFALUT_COLOR);
		int green = Color.green(BasePenAction.DEFALUT_COLOR);
		int blue = Color.blue(BasePenAction.DEFALUT_COLOR);
		mDrawingPaint.setColor(Color.argb(80, red, green, blue));
		mDrawingPaint.setStyle(Paint.Style.STROKE);
		mDrawingPaint.setStrokeJoin(Paint.Join.MITER);
		mDrawingPaint.setStrokeCap(Paint.Cap.SQUARE);
	
//		mDrawingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		mDrawingPaint.setStrokeWidth(DEFALUT_TICKNESS);

	}
	

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);

		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up(Canvas mCanvas) {
		if (mPath != null) {
			mPath.lineTo(mX, mY);

			mCanvas.drawPath(mPath, mDrawingPaint);
			mPath.reset();
		}
	}
	
//	@Override
//	public void changeThinkness(int pThinkness) {
//		setThickness(pThinkness);
//		mDrawingPaint.setStrokeWidth(pThinkness);
//	}
	
	@Override
	public boolean drawTouch(Canvas pCanvas, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			break;
		case MotionEvent.ACTION_UP:
			touch_up(pCanvas);
			break;
		}
		return true;
	}
	
	@Override
	public Path getPath(){
		return mPath;
	}
	
	
	@Override
	public void setThickness(int pThickness) {
		// TODO Auto-generated method stub
		mDrawingPaint.setStrokeWidth(pThickness);
	}
	
	@Override
	public int getThickness() {
		// TODO Auto-generated method stub
		return (int) mDrawingPaint.getStrokeWidth();
	}

	@Override
	public Paint getPaint() {
		return mDrawingPaint;
	}
	
	@Override
	public void setPenColor(int pPenColor) {
		int red = Color.red(pPenColor);
		int green = Color.green(pPenColor);
		int blue = Color.blue(pPenColor);
		int mPenColor = Color.argb(80, red, green, blue);
		mDrawingPaint.setColor(mPenColor);
	}
}

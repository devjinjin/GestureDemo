package com.devjinjin.bignotesketchpad.drawing.tool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.util.Log;
import android.view.MotionEvent;

public class NormalPen extends BasePen {

	public final int DEFALUT_TICKNESS = 12;

	public NormalPen() {
		mPath = new Path();
		// ±×¸®±â
		mDrawingPaint = new Paint();
		mDrawingPaint.setAntiAlias(true);
		mDrawingPaint.setDither(true);
		mDrawingPaint.setColor(Color.BLACK);
		mDrawingPaint.setStyle(Paint.Style.STROKE);
		mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
		mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
		mDrawingPaint.setStrokeWidth(DEFALUT_TICKNESS);
		
//		//////////////////////////////////DashPathEffect////////////////////////////////
//		float[] intervals = new float[] { 10.0f, 30.0f };
//		float phase = 0;
//		float radius = 50.0f;		
//		DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase); // phase
//		//////////////////////////////////DashPathEffect////////////////////////////////
//		
//		//////////////////////////////////CornerPathEffect////////////////////////////////
//		CornerPathEffect cornerPathEffect = new CornerPathEffect(radius);
//		//////////////////////////////////CornerPathEffect////////////////////////////////
//		
//		//////////////////////////////////ComposePathEffect////////////////////////////////
//		ComposePathEffect composePathEffect3 = new ComposePathEffect(
//				cornerPathEffect, dashPathEffect);
//		//////////////////////////////////ComposePathEffect////////////////////////////////
//		
//		
//		//////////////////////////////////DiscretePathEffect////////////////////////////////
//		DiscretePathEffect mDiscretePathEffect = new DiscretePathEffect(30.0f,
//				10.0f);
//		//////////////////////////////////DiscretePathEffect////////////////////////////////
//		
//		//////////////////////////////////PathDashPathEffect////////////////////////////////
//		Path pathShape = new Path();
//		pathShape.addCircle(10, 10, 10, Direction.CCW);
//		float advance = 30.0f;
//		phase = 20.0f;
//		PathDashPathEffect.Style style = PathDashPathEffect.Style.ROTATE;
//
//		PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(
//				pathShape, advance, phase, style);
//		//////////////////////////////////PathDashPathEffect////////////////////////////////
//		
//		SumPathEffect mSumPathEffect = new SumPathEffect(pathDashPathEffect, composePathEffect3)	;
		
//		mDrawingPaint.setPathEffect(composePathEffect3);

		// mDrawingPaint.setXfermode(new
		// PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

	}

	@Override
	public Paint getPaint() {
		return mDrawingPaint;
	}

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		Log.e("test", "jylee touch_move");
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);

		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up(Canvas mCanvas) {
		Log.e("test", "jylee touch_up");
		if (mPath != null) {
			mPath.lineTo(mX, mY);

			mCanvas.drawPath(mPath, mDrawingPaint);
			mPath.reset();
		}
	}

	// @Override
	// public void changeThinkness(int pThinkness) {
	// setThickness(pThinkness);
	// mDrawingPaint.setStrokeWidth(pThinkness);
	// }

	@Override
	public boolean drawTouch(Canvas pCanvas, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("test", "jylee touch_start");
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
	public Path getPath() {
		return mPath;
	}

	@Override
	public void setPenColor(int pPenColor) {
		mDrawingPaint.setColor(pPenColor);
	}
}

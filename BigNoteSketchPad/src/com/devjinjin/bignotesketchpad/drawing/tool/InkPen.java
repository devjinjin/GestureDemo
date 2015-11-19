package com.devjinjin.bignotesketchpad.drawing.tool;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class InkPen extends BasePen {

	// points
	private ArrayList<InkObject> mPointQueue = new ArrayList<InkObject>();
	private ArrayList<InkObject> mPointRecycle = new ArrayList<InkObject>();

	private float mDensity;
	private float mSmoothingRatio;
	private float mMaxStrokeWidth;
	private float mMinStrokeWidth;
	private static final float THRESHOLD_VELOCITY = 7f; // in/s
	private static final float THRESHOLD_ACCELERATION = 3f; // in/s^2
	private static final float FILTER_RATIO_MIN = 0.22f;
	private static final float FILTER_RATIO_ACCEL_MOD = 0.1f;
	// public static final float DEFAULT_MIN_STROKE_WIDTH = 1.0f;
	public static final float DEFAULT_SMOOTHING_RATIO = 0.75f;
	public final int DEFALUT_TICKNESS = 25;


	public InkPen(float pDensity) {
		mMaxStrokeWidth = DEFALUT_TICKNESS;
		mMinStrokeWidth = 2 / DEFALUT_TICKNESS;
		mSmoothingRatio = Math.max(Math.min(DEFAULT_SMOOTHING_RATIO, 1f), 0f);
		mDensity = pDensity;
		// ±×¸®±â
		mDrawingPaint = new Paint();
		mDrawingPaint.setAntiAlias(true);
		mDrawingPaint.setDither(true);
		mDrawingPaint.setColor(BasePenAction.DEFALUT_COLOR);
		mDrawingPaint.setStyle(Paint.Style.STROKE);
		mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
		mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
		// mDrawingPaint.setXfermode(new
		// PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		mDrawingPaint.setStrokeWidth(DEFALUT_TICKNESS);
	}
	
	@Override
	public boolean drawTouch(Canvas pCanvas, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			addPoint(
					pCanvas,
					getRecycledPoint(event.getX(), event.getY(),
							event.getEventTime()));

			break;
		case MotionEvent.ACTION_MOVE:

			if (!mPointQueue.get(mPointQueue.size() - 1).equals(event.getX(),
					event.getY())) {
				addPoint(
						pCanvas,
						getRecycledPoint(event.getX(), event.getY(),
								event.getEventTime()));
			}

			break;
		case MotionEvent.ACTION_UP:
			if (mPointQueue.size() == 1) {
				draw(pCanvas, mPointQueue.get(0));

			} else if (mPointQueue.size() == 2) {
				mPointQueue.get(1).findControlPoints(mPointQueue.get(0), null);
				draw(pCanvas, mPointQueue.get(0), mPointQueue.get(1));
			}

			// recycle remaining points
			mPointRecycle.addAll(mPointQueue);
			mPointQueue.clear();

			break;
		}
		return true;
	}

	private void addPoint(Canvas pCanvas, InkObject p) {
		mPointQueue.add(p);

		int queueSize = mPointQueue.size();
		if (queueSize == 1) {
			// compute starting velocity
			int recycleSize = mPointRecycle.size();
			p.velocity = (recycleSize > 0) ? mPointRecycle.get(recycleSize - 1)
					.velocityTo(p) / 2f : 0f;

			// compute starting stroke width
			mDrawingPaint.setStrokeWidth(computeStrokeWidth(p.velocity));
		}
		if (queueSize == 2) {
			InkObject p0 = mPointQueue.get(0);

			// compute velocity for new point
			p.velocity = p0.velocityTo(p);

			// re-compute velocity for 1st point (predictive velocity)
			p0.velocity = p0.velocity + p.velocity / 2f;

			// find control points for first point
			p0.findControlPoints(null, p);

			// update starting stroke width
			mDrawingPaint.setStrokeWidth(computeStrokeWidth(p0.velocity));
		} else if (queueSize == 3) {
			InkObject p0 = mPointQueue.get(0);
			InkObject p1 = mPointQueue.get(1);

			// find control points for second point
			p1.findControlPoints(p0, p);

			// compute velocity for new point
			p.velocity = p1.velocityTo(p);

			// draw geometry between first 2 points
			draw(pCanvas, p0, p1);

			// recycle 1st point
			mPointRecycle.add(mPointQueue.remove(0));
		}
	}

	private InkObject getRecycledPoint(float x, float y, long time) {
		if (mPointRecycle.size() == 0) {
			InkObject pen = new InkObject();

			pen.setType(x, y, time, mDensity, mSmoothingRatio);
			return pen;
		}

		return mPointRecycle.remove(0).reset(x, y, time);
	}

	private float computeStrokeWidth(float velocity) {
		return mMaxStrokeWidth - (mMaxStrokeWidth - mMinStrokeWidth)
				* Math.min(velocity / THRESHOLD_VELOCITY, 1f);
	}

	private void draw(Canvas pCanvas, InkObject p) {
		mDrawingPaint.setStyle(Paint.Style.FILL);
		// draw dot
		pCanvas.drawCircle(p.x, p.y, mDrawingPaint.getStrokeWidth() / 2f,
				mDrawingPaint);
	}

	private void draw(Canvas pCanvas, InkObject p1, InkObject p2) {
		mDrawingPaint.setStyle(Paint.Style.STROKE);

		// adjust low-pass ratio from changing acceleration
		// using comfortable range of 0.2 -> 0.3 approx.
		float acceleration = Math.abs((p2.velocity - p1.velocity)
				/ (p2.time - p1.time)); // in/s^2
		float filterRatio = Math.min(FILTER_RATIO_MIN + FILTER_RATIO_ACCEL_MOD
				* acceleration / THRESHOLD_ACCELERATION, 1f);

		// compute new stroke width
		float desiredWidth = computeStrokeWidth(p2.velocity);
		float startWidth = mDrawingPaint.getStrokeWidth();

		float endWidth = filterRatio * desiredWidth + (1f - filterRatio)
				* startWidth;
		float deltaWidth = endWidth - startWidth;

		// interpolate bezier curve
		// if (hasFlags(FLAG_INTERPOLATION)) {

		// compute # of steps to interpolate in the bezier curve
		int steps = (int) (Math.sqrt(Math.pow(p2.x - p1.x, 2)
				+ Math.pow(p2.y - p1.y, 2)) / 5);

		// computational setup for differentials used to interpolate the
		// bezier curve
		float u = 1f / (steps + 1);
		float uu = u * u;
		float uuu = u * u * u;

		float pre1 = 3f * u;
		float pre2 = 3f * uu;
		float pre3 = 6f * uu;
		float pre4 = 6f * uuu;

		float tmp1x = p1.x - p1.c2x * 2f + p2.c1x;
		float tmp1y = p1.y - p1.c2y * 2f + p2.c1y;
		float tmp2x = (p1.c2x - p2.c1x) * 3f - p1.x + p2.x;
		float tmp2y = (p1.c2y - p2.c1y) * 3f - p1.y + p2.y;

		float dx = (p1.c2x - p1.x) * pre1 + tmp1x * pre2 + tmp2x * uuu;
		float dy = (p1.c2y - p1.y) * pre1 + tmp1y * pre2 + tmp2y * uuu;
		float ddx = tmp1x * pre3 + tmp2x * pre4;
		float ddy = tmp1y * pre3 + tmp2y * pre4;
		float dddx = tmp2x * pre4;
		float dddy = tmp2y * pre4;

		float x1 = p1.x;
		float y1 = p1.y;
		float x2, y2;

		// iterate over each step and draw the curve
		int i = 0;
		while (i++ < steps) {
			x2 = x1 + dx;
			y2 = y1 + dy;

			mDrawingPaint.setStrokeWidth(startWidth + deltaWidth * i / steps);
			pCanvas.drawLine(x1, y1, x2, y2, mDrawingPaint);

			x1 = x2;
			y1 = y2;
			dx += ddx;
			dy += ddy;
			ddx += dddx;
			ddy += dddy;
		}

		mDrawingPaint.setStrokeWidth(endWidth);
		pCanvas.drawLine(x1, y1, p2.x, p2.y, mDrawingPaint);
	}
	
	@Override
	public void setThickness(int pThickness) {
		// TODO Auto-generated method stub
		mMaxStrokeWidth = pThickness;
		if (mMaxStrokeWidth > 2) {
			int value = pThickness / 10;
			value = Math.max(1, value);

			mMinStrokeWidth = value;
		} else {
			mMinStrokeWidth = 1;
		}

		mDrawingPaint.setStrokeWidth(pThickness);
	}
	@Override
	public int getThickness() {
		// TODO Auto-generated method stub
		return (int) mMaxStrokeWidth;
	}

	@Override
	public void setPenColor(int pPenColor) {
		mDrawingPaint.setColor(pPenColor);
	}

	@Override
	public Paint getPaint() {
		return mDrawingPaint;
	}

	@Override
	public Path getPath() {
		return mPath;
	}

//	@Override
//	public void changeThinkness(int pThinkness) {
//		// TODO Auto-generated method stub
//		
//	}
}

package com.devjinjin.bignotesketchpad.drawing.view;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.devjinjin.bignotesketchpad.drawing.tool.BasePenAction;
import com.devjinjin.bignotesketchpad.drawing.tool.Eraser;

public class DrawingView extends BaseDrawingView {

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 1;
	private String mCurrentPath = "";
	private int mDrawble = 0;

	public DrawingView(Context pContext, AttributeSet attrs, int defStyle) {
		super(pContext, attrs, defStyle);

		context = pContext;
		init();
	}

	public DrawingView(Context pContext, AttributeSet attrs) {
		super(pContext, attrs);
		context = pContext;
		init();
	}

	public DrawingView(Context pContext) {
		super(pContext);
		context = pContext;
		init();
	}

	@Override
	public final void init() {
		mPath = new Path();
		mPen = new BasePenAction(context);
		mEraser = new Eraser();
//		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isErase) {
			return drawTouch(event);
		} else {
			if (mPen != null) {
				boolean result = mPen.drawTouch(mCanvas, event);
				invalidate();
				return result;
			}
		}
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isErase) {
			if (mBitmap != null && !mBitmap.isRecycled()) {
				canvas.drawBitmap(mBitmap, 0, 0, null);
			}
		} else {
			if (mPen != null) {
				if (mPen.getCurrentType() == 1) {
					canvas.drawBitmap(mBitmap, 0, 0, null);
				} else {
					if (mBitmap != null && !mBitmap.isRecycled()) {
						Path path = mPen.getCurrentPath();
						Paint paint = mPen.getCurrentPaint();
						if (path != null && paint != null) {
						
							canvas.drawBitmap(mBitmap, 0, 0, null);		
							canvas.drawPath(path, paint);
						}
					}
				}
			}
		
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;

		if (mBitmap == null) {
			mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
		}

	}

	@Override
	public void setEraserSize(int pEraseSize) {
		// TODO Auto-generated method stub
		mEraser.setEraseSize(pEraseSize);
	}

	@Override
	public int getPenStyle() {
		if (mPen != null) {
			return mPen.getCurrentType();
		}
		return 0;
	}

	@Override
	public void setPenStyle(int penType) {
		if (mPen != null) {
			mPen.setCurrentType(penType);
		}
	}

	@Override
	public void setBackgroundImage(int pDrawable) {
		mDrawble = pDrawable;
		this.setBackgroundResource(pDrawable);
	}

	@Override
	public int getBackgroundImage() {
		// TODO Auto-generated method stub
		return mDrawble;
	}

	@Override
	public boolean isEraseMode() {
		return isErase;
	}

	@Override
	public void setPenColor(int pPenColor) {

		if (mPen != null) {
			mPen.setPenColor(pPenColor);

		}
	}

	@Override
	public void setThickness(int pPenThickness) {
		if (mPen != null) {
			mPen.setThickness(pPenThickness);
		}
	}

	@Override
	public int getThickness() {
		return mPen.getCurrentThickness();
	}

	@Override
	public int getPenColor() {

		if (mPen != null) {
			return mPen.getCurrentColor();
		}

		return 0;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	public void startEraser() {
		isErase = true;
	}

	@Override
	public void startDrawing() {
		isErase = false;
	}

	@Override
	public void setDefaultCanvas() {

		if (mBitmap != null && !mBitmap.isRecycled()) {
			mBitmap.recycle();
			mBitmap = null;
			mBitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			invalidate();
		}
	}

	public void setBitmaPath(File pFile) {
		if (pFile != null) {
			if (mBitmap != null) {
				mBitmap.recycle();
				mBitmap = null;
			}

			mCurrentPath = pFile.getAbsolutePath();
			// Paint paint = new Paint();

			Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPath);
			mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

			mCanvas = new Canvas(mBitmap);
			// paint.setColor(getBackgroundColor());
			// paint.setStyle(Paint.Style.FILL);
			// mCanvas.drawRect(new Rect(0, 0, width, height), paint);
			invalidate();
		}

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

		if (isErase) {
			mCanvas.drawPath(mPath, mEraser.mErasePaint);
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		if (isErase) {
			mCanvas.drawPath(mPath, mEraser.mErasePaint);
		} else {
			mCanvas.drawPath(mPath, mPen.getCurrentPaint());
		}
		mPath.reset();
	}

	public boolean drawTouch(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			touch_start(x, y);
			invalidate();

			break;
		case MotionEvent.ACTION_MOVE:

			touch_move(x, y);
			invalidate();

			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();

			break;
		}
		return true;
	}

	public Bitmap getFullBitmap() {

		Bitmap captureView = Bitmap.createBitmap(this.getMeasuredWidth(),

		this.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

		Canvas screenShotCanvas = new Canvas(captureView);

		draw(screenShotCanvas);

		return captureView;
	}

	public Bitmap getDrawingBitmap() {

		return mBitmap;
	}

}

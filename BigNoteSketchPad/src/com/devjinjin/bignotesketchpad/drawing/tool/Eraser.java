package com.devjinjin.bignotesketchpad.drawing.tool;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class Eraser {

	private int mEraseSize = 50;
	public Paint mErasePaint;
	
	public Eraser(){
		mErasePaint = new Paint();
		mErasePaint.setAntiAlias(true);
		mErasePaint.setDither(true);
		mErasePaint.setColor(Color.WHITE);
		mErasePaint.setStyle(Paint.Style.STROKE);
		mErasePaint.setStrokeJoin(Paint.Join.ROUND);
		mErasePaint.setStrokeCap(Paint.Cap.ROUND);	
		mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		mErasePaint.setStrokeWidth(mEraseSize);

	}

	public int getEraseSize() {
		return mEraseSize;
	}

	public void setEraseSize(int mEraseSize) {
		this.mEraseSize = mEraseSize;
		mErasePaint.setStrokeWidth(mEraseSize);
	}

}

package com.devjinjin.bignotesketchpad.drawing.tool;

public class InkObject {
	public float x, y, c1x, c1y, c2x, c2y, velocity;
	public long time;
	protected float mDensity;
	protected float mSmoothingRatio;

	public boolean equals(InkObject p) {
		return equals(p.x, p.y);
	}

	public boolean equals(float x, float y) {
		return this.x == x && this.y == y;
	}

	public InkObject reset(float x, float y, long time) {
		this.x = x;
		this.y = y;
		this.time = time;
		velocity = 0f;

		c1x = x;
		c1y = y;
		c2x = x;
		c2y = y;

		return this;
	}

	public float distanceTo(InkObject p) {
		float dx = p.x - x;
		float dy = p.y - y;

		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public void setType(float x, float y, long time, float pDensity,
			float pSmoothingRatio) {
		mDensity = pDensity;
		mSmoothingRatio = pSmoothingRatio;
		reset(x, y, time);
	}

	public float velocityTo(InkObject p) {
		return (1000f * distanceTo(p)) / (Math.abs(p.time - time) * mDensity); // in/s
	}

	public float getSmoothingRatio() {
		return mSmoothingRatio;
	}

	public void setSmoothingRatio(float ratio) {
		mSmoothingRatio = Math.max(Math.min(ratio, 1f), 0f);
	}

	public void findControlPoints(InkObject prev, InkObject next) {
		if (prev == null && next == null) {
			return;
		}

		float r = getSmoothingRatio();

		// if start of a stroke, c2 control points half-way between this and
		// next point
		if (prev == null) {
			c2x = x + r * (next.x - x) / 2f;
			c2y = y + r * (next.y - y) / 2f;
			return;
		}

		// if end of a stroke, c1 control points half-way between this and
		// prev point
		if (next == null) {
			c1x = x + r * (prev.x - x) / 2f;
			c1y = y + r * (prev.y - y) / 2f;
			return;
		}

		// init control points
		c1x = (x + prev.x) / 2f;
		c1y = (y + prev.y) / 2f;
		c2x = (x + next.x) / 2f;
		c2y = (y + next.y) / 2f;

		// calculate control offsets
		float len1 = distanceTo(prev);
		float len2 = distanceTo(next);
		float k = len1 / (len1 + len2);
		float xM = c1x + (c2x - c1x) * k;
		float yM = c1y + (c2y - c1y) * k;
		float dx = x - xM;
		float dy = y - yM;

		// inverse smoothing ratio
		r = 1f - r;

		// translate control points
		c1x += dx + r * (xM - c1x);
		c1y += dy + r * (yM - c1y);
		c2x += dx + r * (xM - c2x);
		c2y += dy + r * (yM - c2y);
	}
}

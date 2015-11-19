/*
 * Copyright (C) 2010 Cyril Mottier (http://www.cyrilmottier.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.devjinjin.bignotesketchpad.drawing;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.devjinjin.bignotesketchpad.R;

/**
 * A {@link ColorQuickActionMenu} is an implementation of a {@link CustomPopup}
 * that displays {@link QuickAction}s in a grid manner. This is usually used to
 * create a shortcut to jump between different type of information on screen.
 * 
 * @author Benjamin Fellous
 * @author Cyril Mottier
 */
public class ColorQuickActionMenu extends CustomPopup {

	// private HorizontalListView mGridView;
	private FrameLayout mAddLayout;
	private OnQuickActionClickListener mListener;
	private ColorPickerView mPickerView;
	private ImageButton mBtnRed;
	private ImageButton mBtnOrange;
	private ImageButton mBtnYellow;
	private ImageButton mBtnGreen;
	private ImageButton mBtnBlue;

	private ImageButton mBtnNavy;
	private ImageButton mBtnPurple;
	private ImageButton mBtnBlack;
	private ImageButton mBtnWhite;
	private ImageButton mBtnPicker;

	public interface OnQuickActionClickListener {
		void onQuickActionClicked(int color);

		void onDismissNotification();
	}

	@Override
	public void dismiss() {
		mListener.onDismissNotification();
		super.dismiss();
	}

	public void setCurrentColor(int pColor) {
		mPickerView.setCurrentColor(pColor);
	}

	public ColorQuickActionMenu(Context context, int pColor) {
		super(context);

		setContentView(R.layout.layout_color_action);

		final View v = getContentView();

		mBtnRed = (ImageButton) v.findViewById(R.id.ibRedColor);
		mBtnOrange = (ImageButton) v.findViewById(R.id.ibOrangeColor);
		mBtnYellow = (ImageButton) v.findViewById(R.id.ibYellowColor);
		mBtnGreen = (ImageButton) v.findViewById(R.id.ibGreenColor);
		mBtnBlue = (ImageButton) v.findViewById(R.id.ibBlueColor);

		mBtnNavy = (ImageButton) v.findViewById(R.id.ibNavyColor);
		mBtnPurple = (ImageButton) v.findViewById(R.id.ibPurpleColor);
		mBtnBlack = (ImageButton) v.findViewById(R.id.ibBlackColor);
		mBtnWhite = (ImageButton) v.findViewById(R.id.ibWhiteColor);
		mBtnPicker = (ImageButton) v.findViewById(R.id.ibPickerColor);

		mBtnRed.setOnClickListener(mClickListener);
		mBtnOrange.setOnClickListener(mClickListener);
		mBtnYellow.setOnClickListener(mClickListener);
		mBtnGreen.setOnClickListener(mClickListener);
		mBtnBlue.setOnClickListener(mClickListener);
		mBtnNavy.setOnClickListener(mClickListener);
		mBtnPurple.setOnClickListener(mClickListener);
		mBtnBlack.setOnClickListener(mClickListener);
		mBtnWhite.setOnClickListener(mClickListener);
		mBtnPicker.setOnClickListener(mClickListener);

		mAddLayout = (FrameLayout) v.findViewById(R.id.flAddLayout);
		mPickerView = new ColorPickerView(context, pColor);

		mAddLayout.addView(mPickerView);

		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				mPickerView.getWidth(), mPickerView.getHeight());
		layoutParams.gravity = Gravity.CENTER;
		mPickerView.setLayoutParams(layoutParams);

		// mGridView = (HorizontalListView) v.findViewById(R.id.listview);
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_red_selector,
		// R.string.color_Red));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_orange_selector,
		// R.string.color_Orange));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_yellow_selector,
		// R.string.color_Yellow));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_green_selector,
		// R.string.color_Green));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_blue_selector,
		// R.string.color_Blue));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_navy_selector,
		// R.string.color_Navi));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_purple_selector,
		// R.string.color_Magenta));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_white_selector,
		// R.string.color_White));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_black_selector,
		// R.string.color_Black));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.color_action_picker_selector,
		// R.string.color_PickerColor));

		// final LayoutInflater inflater = LayoutInflater.from(getContext());
		// textView = (TextView)
		// inflater.inflate(R.layout.gd_quick_action_grid_item, mGridView,
		// false);
	}

	public final OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int color = 0;
			switch (v.getId()) {
			case R.id.ibRedColor:
				color = Color.argb(255, 228, 51, 56);
				break;
			case R.id.ibOrangeColor:
				color = Color.argb(255, 253, 89, 60);
				break;
			case R.id.ibYellowColor:
				color = Color.argb(255, 255, 202, 0);
				break;
			case R.id.ibGreenColor:
				color = Color.argb(255, 0, 168, 86);
				break;
			case R.id.ibBlueColor:
				color = Color.argb(255, 0, 155, 211);
				break;
			case R.id.ibNavyColor:
				color = Color.argb(255, 3, 104, 179);
				break;
			case R.id.ibPurpleColor:
				color = Color.argb(255, 97, 54, 130);
				break;
			case R.id.ibWhiteColor:
				color = Color.WHITE;
				break;
			case R.id.ibBlackColor:
				color = Color.BLACK;
				break;
			case R.id.ibPickerColor:
				if (mAddLayout.getVisibility() == View.GONE) {
					mAddLayout.setVisibility(View.VISIBLE);
				} else {
					mAddLayout.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
			if (v.getId() != R.id.ibPickerColor) {
				mListener.onQuickActionClicked(color);
				mAddLayout.setVisibility(View.GONE);
				if (getDismissOnClick()) {
					dismiss();
				}
			}

		}
	};

	@Override
	protected void populateQuickActions(final List<QuickAction> quickActions) {

		// mGridView.setAdapter(new BaseAdapter() {
		//
		// public View getView(int position, View view, ViewGroup parent) {
		//
		// ImageView imageView = (ImageView) view;
		//
		// if (view == null) {
		// final LayoutInflater inflater = LayoutInflater
		// .from(getContext());
		// imageView = (ImageView) inflater.inflate(
		// R.layout.quick_action_item, mGridView,
		// false);
		// }
		//
		// QuickAction quickAction = quickActions.get(position);
		// imageView.setImageDrawable(quickAction.mDrawable);
		//
		//
		// return imageView;
		//
		// }
		//
		// public long getItemId(int position) {
		// return position;
		// }
		//
		// public Object getItem(int position) {
		// return null;
		// }
		//
		// public int getCount() {
		// return quickActions.size();
		// }
		// });
		//
		// mGridView.setOnItemClickListener(mInternalItemClickListener);
	}

	@Override
	protected void onMeasureAndLayout(Rect anchorRect, View contentView) {

		contentView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		contentView.measure(MeasureSpec.makeMeasureSpec(getScreenWidth(),
				MeasureSpec.EXACTLY), LayoutParams.WRAP_CONTENT);

		int rootHeight = contentView.getMeasuredHeight();
		int rootWidth = contentView.getMeasuredWidth();

		int dyTop = anchorRect.top;
		int dyBottom = getScreenHeight() - anchorRect.bottom;

		boolean onTop = (dyTop > dyBottom);
		int popupY = (onTop) ? anchorRect.top - rootHeight : anchorRect.bottom;

		setWidgetSpecs(popupY, onTop);
	}

	//
	// private OnItemClickListener mInternalItemClickListener = new
	// OnItemClickListener() {
	//
	// public void onItemClick(AdapterView<?> adapterView, View view,
	// int position, long id) {
	// if (position == 9) {
	// if (mAddLayout.getVisibility() == View.GONE) {
	// mAddLayout.setVisibility(View.VISIBLE);
	// } else {
	// mAddLayout.setVisibility(View.GONE);
	// }
	// } else {
	// int color = 0;
	// switch (position) {
	// case 0:
	// color = Color.argb(255, 228, 51, 56);
	// break;
	// case 1:
	// color = Color.argb(255, 253, 89, 60);
	// break;
	// case 2:
	// color = Color.argb(255, 255, 202, 0);
	// break;
	// case 3:
	// color = Color.argb(255, 0, 168, 86);
	// break;
	// case 4:
	// color = Color.argb(255, 0, 155, 211);
	// break;
	// case 5:
	// color = Color.argb(255, 3, 104, 179);
	// break;
	// case 6:
	// color = Color.argb(255, 97, 54, 130);
	// break;
	// case 7:
	// color = Color.WHITE;
	// break;
	// case 8:
	// color = Color.BLACK;
	// break;
	//
	// default:
	// break;
	// }
	// mOnQuickActionClickListener.onQuickActionClicked(color);
	// mAddLayout.setVisibility(View.GONE);
	// if (getDismissOnClick()) {
	// dismiss();
	// }
	// }
	// }
	// };

	public class ColorPickerView extends View {

		private Paint mPaint;
		private Paint mCenterPaint;
		private final int[] mColors;

		ColorPickerView(Context c, int color) {
			super(c);

			mColors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
					0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
			Shader s = new SweepGradient(0, 0, mColors, null);

			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setShader(s);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(32);

			mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mCenterPaint.setColor(color);
			mCenterPaint.setStrokeWidth(5);
		}

		private boolean mTrackingCenter;
		private boolean mHighlightCenter;

		public void setCurrentColor(int color) {
			mCenterPaint.setColor(color);
			invalidate();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f;

			canvas.translate(CENTER_X, CENTER_X);

			canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
			canvas.drawCircle(0, 0, CENTER_RADIUS, mCenterPaint);

			if (mTrackingCenter) {
				int c = mCenterPaint.getColor();
				mCenterPaint.setStyle(Paint.Style.STROKE);

				if (mHighlightCenter) {
					mCenterPaint.setAlpha(0xFF);
				} else {
					mCenterPaint.setAlpha(0x80);
				}
				canvas.drawCircle(0, 0,
						CENTER_RADIUS + mCenterPaint.getStrokeWidth(),
						mCenterPaint);

				mCenterPaint.setStyle(Paint.Style.FILL);
				mCenterPaint.setColor(c);
			}
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);

		}

		private static final int CENTER_X = 100;
		private static final int CENTER_Y = 100;
		private static final int CENTER_RADIUS = 32;

		private int ave(int s, int d, float p) {
			return s + java.lang.Math.round(p * (d - s));
		}

		private int interpColor(int colors[], float unit) {
			if (unit <= 0) {
				return colors[0];
			}
			if (unit >= 1) {
				return colors[colors.length - 1];
			}

			float p = unit * (colors.length - 1);
			int i = (int) p;
			p -= i;

			int c0 = colors[i];
			int c1 = colors[i + 1];
			int a = ave(Color.alpha(c0), Color.alpha(c1), p);
			int r = ave(Color.red(c0), Color.red(c1), p);
			int g = ave(Color.green(c0), Color.green(c1), p);
			int b = ave(Color.blue(c0), Color.blue(c1), p);

			return Color.argb(a, r, g, b);
		}

		private static final float PI = 3.1415926f;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX() - CENTER_X;
			float y = event.getY() - CENTER_Y;
			boolean inCenter = java.lang.Math.sqrt(x * x + y * y) <= CENTER_RADIUS;

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mTrackingCenter = inCenter;
				if (inCenter) {
					mHighlightCenter = true;
					invalidate();
					break;
				}
			case MotionEvent.ACTION_MOVE:
				if (mTrackingCenter) {
					if (mHighlightCenter != inCenter) {
						mHighlightCenter = inCenter;
						invalidate();
					}
				} else {
					float angle = (float) java.lang.Math.atan2(y, x);
					float unit = angle / (2 * PI);
					if (unit < 0) {
						unit += 1;
					}
					mCenterPaint.setColor(interpColor(mColors, unit));
					invalidate();
				}
				break;
			case MotionEvent.ACTION_UP:
				if (mTrackingCenter) {
					if (inCenter) {
						mListener
								.onQuickActionClicked(mCenterPaint.getColor());
						if (getDismissOnClick()) {
							mAddLayout.setVisibility(View.GONE);
							dismiss();
						}
					}
					mTrackingCenter = false;
					invalidate();
				}
				break;
			}
			return true;
		}
	}

	public void setOnQuickActionClickListener(
			OnQuickActionClickListener listener) {
		mListener = listener;
	}

}

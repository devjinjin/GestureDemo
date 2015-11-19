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
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.devjinjin.bignotesketchpad.R;

/**
 * A {@link PenQuickActionMenu} is an implementation of a {@link CustomPopup}
 * that displays {@link QuickAction}s in a grid manner. This is usually used to
 * create a shortcut to jump between different type of information on screen.
 * 
 * @author Benjamin Fellous
 * @author Cyril Mottier
 */
public class PenQuickActionMenu extends CustomPopup {

	// private HorizontalListView mGridView;
	private SeekBar mPenSeekBar;
	private OnPenQuickActionClickListener mListener;
	private ImageButton mNormalPen;
	private ImageButton mBrush;
	private ImageButton mPencil;
	private ImageButton mHighLighter;

	public interface OnPenQuickActionClickListener {
		void onPenQuickActionClicked(int position);

		void onDismissNotification();

		void onPenSizeChanged(int pSize);
	}

	@Override
	public void dismiss() {
		mListener.onDismissNotification();
		super.dismiss();
	}

	public PenQuickActionMenu(Context context) {
		super(context);

		setContentView(R.layout.layout_pen_action);

		final View v = getContentView();
		mNormalPen = (ImageButton) v.findViewById(R.id.ibNormalPen);
		mBrush = (ImageButton) v.findViewById(R.id.ibBrush);
		mPencil = (ImageButton) v.findViewById(R.id.ibPencil);
		mHighLighter = (ImageButton) v.findViewById(R.id.ibHighLighter);

		mNormalPen.setOnClickListener(mClickListener);
		mBrush.setOnClickListener(mClickListener);
		mPencil.setOnClickListener(mClickListener);
		mHighLighter.setOnClickListener(mClickListener);
		// mGridView = (HorizontalListView) v.findViewById(R.id.listview);
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.pen_action_pen_selector,
		// R.string.pen_noraml));
		//
		// addQuickAction(new QuickAction(context,
		// R.drawable.pen_action_brush_selector,
		// R.string.pen_calligraphic));

		mPenSeekBar = (SeekBar) v.findViewById(R.id.sbPenSize);
		mPenSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() != 0) {
					mListener.onPenSizeChanged(seekBar.getProgress());
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (progress <= 0) {
					seekBar.setProgress(1);
				}
			}
		});

	}

	public void setCurrentPenSize(int pSize) {
		mPenSeekBar.setProgress(pSize);
	}

	public void setCurrentPenType(int pType) {
		mNormalPen.setSelected(false);
		mBrush.setSelected(false);
		mPencil.setSelected(false);
		mHighLighter.setSelected(false);
		mPenSeekBar.setEnabled(true);
		
		switch (pType) {
		case 0:
			mNormalPen.setSelected(true);
			mPenSeekBar.setEnabled(true);
			break;
		case 1:
			mBrush.setSelected(true);
			mPenSeekBar.setEnabled(true);
			break;
		case 2:
			mPencil.setSelected(true);
			mPenSeekBar.setEnabled(false);
			break;
		case 3:
			mHighLighter.setSelected(true);
			mPenSeekBar.setEnabled(true);
			break;
		default:
			break;
		}

	}

	@Override
	protected void populateQuickActions(final List<QuickAction> quickActions) {

		// mGridView.setAdapter(new BaseAdapter() {
		//
		// public View getView(int position, View view, ViewGroup parent) {
		//
		// ImageView imageView = (ImageView) view;
		//
		// if (view == null) {
		// final LayoutInflater inflater = LayoutInflater.from(getContext());
		// imageView = (ImageView) inflater.inflate(R.layout.quick_action_item,
		// mGridView, false);
		// }
		//
		// QuickAction quickAction = quickActions.get(position);
		// imageView.setImageDrawable(quickAction.mDrawable);
		//
		// return imageView;
		//
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

		int dyTop = anchorRect.top;
		int dyBottom = getScreenHeight() - anchorRect.bottom;

		boolean onTop = (dyTop > dyBottom);
		int popupY = (onTop) ? anchorRect.top - rootHeight : anchorRect.bottom;

		setWidgetSpecs(popupY, onTop);
	}

	// private OnItemClickListener mInternalItemClickListener = new
	// OnItemClickListener() {
	// public void onItemClick(AdapterView<?> adapterView, View view,
	// int position, long id) {
	// mListener.onPenQuickActionClicked(position);
	// if (getDismissOnClick()) {
	// dismiss();
	// }
	// }
	// };

	public final OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ibNormalPen:
				mListener.onPenQuickActionClicked(0);
				setCurrentPenType(0);
				break;
			case R.id.ibBrush:
				mListener.onPenQuickActionClicked(1);
				setCurrentPenType(1);
				break;
			case R.id.ibPencil:
				mListener.onPenQuickActionClicked(2);
				setCurrentPenType(2);
				break;

			case R.id.ibHighLighter:
				mListener.onPenQuickActionClicked(3);
				setCurrentPenType(3);
				break;
			default:
				if (getDismissOnClick()) {
					dismiss();
				}
				break;
			}
		}
	};

	public void setOnPenQuickctionClickListener(
			OnPenQuickActionClickListener listener) {
		mListener = listener;
	}

}

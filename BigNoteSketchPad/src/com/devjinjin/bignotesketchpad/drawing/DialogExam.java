package com.devjinjin.bignotesketchpad.drawing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.devjinjin.bignotesketchpad.MainActivity;
import com.devjinjin.bignotesketchpad.R;

public class DialogExam extends Dialog {
	Activity mActivity;
	PenStylePickerView mPickerView;

	public interface OnStyleChangedListener {
		void penStyleChanged(int penStyle);

		void penSizeChanged(int size);
	}

	private OnStyleChangedListener mListener;
	private int mInitialSize;

	private static class PenStylePickerView extends FrameLayout implements
			View.OnClickListener, SeekBar.OnSeekBarChangeListener {

		private OnStyleChangedListener mListener;
		private ImageButton mCalligraphicBtn;
		private ImageButton mSignPenBtn;
		private ImageButton mPenCilBtn;
		private SeekBar mPenSizeSeekBar;

		PenStylePickerView(Context context, OnStyleChangedListener l,
				int mInitialSize) {
			super(context);
			mListener = l;
			View mView = ((MainActivity) context).getLayoutInflater().inflate(
					R.layout.pickerview_style, null);

			mCalligraphicBtn = (ImageButton) mView
					.findViewById(R.id.ibCalligraphic);
			mSignPenBtn = (ImageButton) mView.findViewById(R.id.ibSignPen);
			mPenCilBtn = (ImageButton) mView.findViewById(R.id.ibPenCil);

			mPenSizeSeekBar = (SeekBar) mView.findViewById(R.id.sbSize);

			mCalligraphicBtn.setOnClickListener(this);

			mSignPenBtn.setOnClickListener(this);
			mPenCilBtn.setOnClickListener(this);

			mPenSizeSeekBar.setOnSeekBarChangeListener(this);
			mPenSizeSeekBar.setProgress(mInitialSize);
			this.addView(mView);

		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.ibCalligraphic:
				mListener.penStyleChanged(0);
				break;

			case R.id.ibSignPen:
				mListener.penStyleChanged(1);
				break;
			case R.id.ibPenCil:
				mListener.penStyleChanged(2);
				break;

			default:
				break;
			}
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (progress <= 0) {
				seekBar.setProgress(1);
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			if (seekBar.getProgress() != 0) {
				mListener.penSizeChanged(seekBar.getProgress());
			}
		}

	}

	public DialogExam(Activity context, OnStyleChangedListener listener,
			int initialSize) {
		super(context);
		mActivity = context;
		mListener = listener;
		mInitialSize = initialSize;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		OnStyleChangedListener l = new OnStyleChangedListener() {

			@Override
			public void penStyleChanged(int penStyle) {
				mListener.penStyleChanged(penStyle);
				dismiss();
			}

			@Override
			public void penSizeChanged(int size) {
				// TODO Auto-generated method stub
				if (size != 0) {
					mListener.penSizeChanged(size);
				}
			}

		};

		setContentView(new PenStylePickerView(mActivity, l, mInitialSize));
		setTitle("Select a PenType");
	}
}

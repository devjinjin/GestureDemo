package com.example.gesturedemo;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends Toast {

	Context mContext;

	public CustomToast(Context context) {
		super(context);
		mContext = context;
	}

	public void showToast(int duration, String pText) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.toast_custom, null);
		TextView mText = (TextView) v.findViewById(R.id.tvToast);
		mText.setText(pText);
		show(this, v, duration);
	}

	private void show(Toast toast, View v, int duration) {
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(duration);
		toast.setView(v);
		toast.show();

//		new CountDownTimer(4000, 1000) {
//			public void onTick(long millisUntilFinished) {
//				show();
//			}
//
//			public void onFinish() {
//				show();
//			}
//		}.start();
	}

}

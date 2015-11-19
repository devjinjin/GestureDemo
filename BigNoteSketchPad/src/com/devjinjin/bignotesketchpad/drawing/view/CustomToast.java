package com.devjinjin.bignotesketchpad.drawing.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.devjinjin.bignotesketchpad.R;

public class CustomToast extends Toast {

	Context mContext;

	public CustomToast(Context context) {
		super(context);
		mContext = context;
	}

	public void showToast(int duration) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.toast_custom, null);

		show(this, v, duration);
	}

	private void show(Toast toast, View v, int duration) {
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(duration);
		toast.setView(v);
		toast.show();
	}

}

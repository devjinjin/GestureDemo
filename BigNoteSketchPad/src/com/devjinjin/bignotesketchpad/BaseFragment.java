package com.devjinjin.bignotesketchpad;

import android.app.Fragment;
import android.content.Context;

public abstract class BaseFragment extends Fragment {
	@Override
	public void onResume() {
		updateActionBar();
		super.onResume();
	}

	public abstract void updateActionBar();
	
	
}

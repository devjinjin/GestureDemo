package com.devjinjin.bignotesketchpad;

import android.app.Fragment;

public abstract class BaseFragment extends Fragment {
	@Override
	public void onResume() {
		updateActionBar();
		super.onResume();
	}

	public abstract void updateActionBar();
	
	
}

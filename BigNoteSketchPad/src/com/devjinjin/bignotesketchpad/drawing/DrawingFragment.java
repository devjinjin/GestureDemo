package com.devjinjin.bignotesketchpad.drawing;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.Toast;

import com.devjinjin.bignotesketchpad.BaseFragment;
import com.devjinjin.bignotesketchpad.R;
import com.devjinjin.bignotesketchpad.data.DataItem;
import com.devjinjin.bignotesketchpad.data.DataItemManager;
import com.devjinjin.bignotesketchpad.drawing.ColorQuickActionMenu.OnQuickActionClickListener;
import com.devjinjin.bignotesketchpad.drawing.EraseQuickActionMenu.OnEraseQuickActionClickListener;
import com.devjinjin.bignotesketchpad.drawing.PenQuickActionMenu.OnPenQuickActionClickListener;
import com.devjinjin.bignotesketchpad.drawing.view.CustomToast;
import com.devjinjin.bignotesketchpad.drawing.view.DrawingView;

public class DrawingFragment extends BaseFragment implements
		View.OnClickListener {

	enum DRAWING_CURREN_MODE {
		DRAWING_MODE, ERASE_MODE
	}

	DRAWING_CURREN_MODE mCurrentMode = DRAWING_CURREN_MODE.DRAWING_MODE;
	DataItem mItem;
	DrawingView mDrawingView;

	ImageButton mMenuPen;
	ImageButton mMenuColor;
	ImageButton mMenuEraser;
	ImageButton mMenuSave;
	ColorQuickActionMenu colorMenuPopup = null;
	PenQuickActionMenu penMenuPopup = null;
	EraseQuickActionMenu eraseMenuPopup = null;

	public DrawingFragment(DataItem item) {
		setHasOptionsMenu(true);
		mItem = item;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		updateActionBar();

		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case android.R.id.home:
			getActivity().onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_draw, container,
				false);
		mDrawingView = (DrawingView) rootView.findViewById(R.id.cDrawingView);

		int mDrawable = mItem.getDrawable();
		mDrawingView.setBackgroundImage(mDrawable);
		mDrawingView.setBitmaPath(mItem.getFirstDrawingItem());

		colorMenuPopup = new ColorQuickActionMenu(getActivity(), Color.BLACK);
		colorMenuPopup
				.setOnQuickActionClickListener(new OnQuickActionClickListener() {

					@Override
					public void onQuickActionClicked(int color) {
						// TODO Auto-generated method stub
						colorChanged(color);
					}

					@Override
					public void onDismissNotification() {
						// TODO Auto-generated method stub
						mDrawingView.requestFocus();

					}
				});

		penMenuPopup = new PenQuickActionMenu(getActivity());
		penMenuPopup
				.setOnPenQuickctionClickListener(new OnPenQuickActionClickListener() {

					@Override
					public void onPenSizeChanged(int pSize) {
						// TODO Auto-generated method stub
						penSizeChanged(pSize);
					}

					@Override
					public void onPenQuickActionClicked(int position) {
						penStyleChanged(position);
					}

					@Override
					public void onDismissNotification() {
						// TODO Auto-generated method stub
						mDrawingView.requestFocus();

					}
				});

		eraseMenuPopup = new EraseQuickActionMenu(getActivity());
		eraseMenuPopup
				.setOnEraseQuickActionClickListener(new OnEraseQuickActionClickListener() {

					@Override
					public void onEraseQuickActionClicked(int pSize) {
						// TODO Auto-generated method stub
						eraseSizeChanged(pSize);
					}

					@Override
					public void onEraseClearAll() {
						// TODO Auto-generated method stub
						mDrawingView.setDefaultCanvas();
						mDrawingView.startDrawing();
					}

					@Override
					public void onDismissNotification() {
						// TODO Auto-generated method stub
						mDrawingView.requestFocus();

					}
				});

		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.ibColor:
			colorMenuPopup.setCurrentColor(mDrawingView.getPenColor());
			colorMenuPopup.show(v);

			break;
		case R.id.ibPen:
			penMenuPopup.setCurrentPenType(mDrawingView.getPenStyle());
			penMenuPopup.setCurrentPenSize(mDrawingView.getThickness());
			penMenuPopup.show(v);
			break;
		case R.id.ibEraser:
			eraseMenuPopup.show(v);

			break;
		case R.id.ibSave:
			saveDrawingImage();
			saveImageMediaStore();
			break;
		default:
			break;
		}
	}

	// private void selectModeChange(DRAWING_CURREN_MODE pMode){
	// mCurrentMode = pMode;
	//
	// if(mCurrentMode == DRAWING_CURREN_MODE.DRAWING_MODE){
	// mMenuPen.setSelected(true);
	// mMenuEraser.setSelected(false);
	// }else{
	// mMenuPen.setSelected(false);
	// mMenuEraser.setSelected(true);
	// }
	//
	// }

	public void colorChanged(int color) {
		mDrawingView.startDrawing();
		mDrawingView.setPenColor(color);

	}

	public void eraseSizeChanged(int size) {
		if (size == 0) {
			mDrawingView.startDrawing();
		} else {
			mDrawingView.setEraserSize(size);
			mDrawingView.startEraser();
		}
	}

	public void penStyleChanged(int penType) {
		// TODO Auto-generated method stub
		mDrawingView.startDrawing();
		mDrawingView.setPenStyle(penType);

		int size = mDrawingView.getThickness();

		penMenuPopup.setCurrentPenSize(size);

	}

	public void penSizeChanged(int size) {
		// TODO Auto-generated method stub
		mDrawingView.setThickness(size);
	}

	@Override
	public void updateActionBar() {
		final ViewGroup actionBarLayout = (ViewGroup) getActivity()
				.getLayoutInflater().inflate(R.layout.actionbar_custom, null);

		// inflater.inflate(R.menu.drawing_color, menu);
		getActivity().getActionBar().setDisplayShowHomeEnabled(true);
		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		getActivity().getActionBar().setDisplayShowCustomEnabled(true);
		getActivity().getActionBar().setHomeButtonEnabled(true);

		getActivity().getActionBar().setLogo(R.drawable.back_action_selector);
		getActivity().getActionBar().setHomeButtonEnabled(true);

		ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		getActivity().getActionBar().setCustomView(actionBarLayout, layout);

		// View view = getActivity().getActionBar().getCustomView();

		mMenuColor = (ImageButton) actionBarLayout.findViewById(R.id.ibColor);
		mMenuPen = (ImageButton) actionBarLayout.findViewById(R.id.ibPen);
		mMenuEraser = (ImageButton) actionBarLayout.findViewById(R.id.ibEraser);
		mMenuSave = (ImageButton) actionBarLayout.findViewById(R.id.ibSave);

		mMenuPen.setOnClickListener(this);
		mMenuColor.setOnClickListener(this);
		mMenuEraser.setOnClickListener(this);
		mMenuSave.setOnClickListener(this);
	}

	public void saveDrawingImage() {
		if (DataItemManager.getInstance(getActivity()).saveDataItem(mItem,
				mDrawingView.getFullBitmap(), mDrawingView.getDrawingBitmap(),
				0)) {
			DataItemManager.getInstance(getActivity()).readDataFolder();
		}
	}

	public void saveImageMediaStore() {
		String name = String.valueOf(System.currentTimeMillis());
		DataItemManager.getInstance(getActivity())
				.saveImageMediaStore(mDrawingView.getFullBitmap(), name);
		 CustomToast toast = new CustomToast(getActivity());
		 toast.showToast(Toast.LENGTH_SHORT);
//		if (path != null) {
//			Toast.makeText(getActivity(), path, Toast.LENGTH_LONG).show();
//		}
	}
}

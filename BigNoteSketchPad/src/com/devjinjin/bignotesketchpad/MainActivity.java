package com.devjinjin.bignotesketchpad;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.devjinjin.bignotesketchpad.data.DataItem;
import com.devjinjin.bignotesketchpad.data.DataItemManager;
import com.devjinjin.bignotesketchpad.drawing.DrawingFragment;
import com.devjinjin.bignotesketchpad.main.MainFragment;
import com.devjinjin.bignotesketchpad.main.MainFragment.IItemClickListenter;
import com.devjinjin.bignotesketchpad.paper.PaperFragment;
import com.devjinjin.bignotesketchpad.paper.PaperFragment.IPaperClickListenter;
import com.devjinjin.bignotesketchpad.paper.PaperItem;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchFolder();
		if (savedInstanceState == null) {
			getFragmentManager()
					.beginTransaction()
					.add(R.id.container,
							new MainFragment(new IItemClickListenter() {

								@Override
								public void onItemClick(DataItem item) {
									if (item == null
											|| item.mFoloderName == null
											|| item.mFoloderName.length() == 0) {
										selecPaperMenu();
									} else {
										moveDrawingMenu(item);
									}
								}
							}), "main").commit();
		}
	}

	@Override
	public void onBackPressed() {
		Fragment drawingFragment = getFragmentManager().findFragmentByTag(
				"drawing");
		if (drawingFragment != null) {
			Fragment paperFragment = getFragmentManager().findFragmentByTag(
					"paper");
			if (paperFragment != null) {
				((DrawingFragment) drawingFragment).saveDrawingImage();
				getFragmentManager().popBackStack();
			} else {
				((DrawingFragment) drawingFragment).saveDrawingImage();
			}
		}
		super.onBackPressed();
	}

	private void selecPaperMenu() {
		PaperFragment paperFragment = new PaperFragment(
				new IPaperClickListenter() {

					@Override
					public void onPaperClick(PaperItem item) {
						int drawable = item.getDrawable();
						DataItem dataItem = DataItemManager.getInstance(
								MainActivity.this).newDataItem(drawable);
						moveDrawingMenu(dataItem);
					}
				});

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.container, paperFragment, "paper");
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private void moveDrawingMenu(DataItem item) {

		DrawingFragment drawingFragment = new DrawingFragment(item);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.container, drawingFragment, "drawing");
		transaction.addToBackStack(null);
		transaction.commit();

	}

	private void searchFolder() {

		DataItemManager.getInstance(this).readDataFolder();

	}
}

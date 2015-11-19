package com.devjinjin.bignotesketchpad.main;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.devjinjin.bignotesketchpad.BaseFragment;
import com.devjinjin.bignotesketchpad.R;
import com.devjinjin.bignotesketchpad.data.DataItem;
import com.devjinjin.bignotesketchpad.data.DataItemManager;

public class MainFragment extends BaseFragment implements
		AdapterView.OnItemClickListener, OnCheckedChangeListener {
	private static final String TAG = MainFragment.class.getSimpleName();
	private GridView mGridView;
	private GridAdapter mAdapter;
	private boolean isDeleteMode = false;
	private IItemClickListenter mListener;
	private ArrayList<DataItem> deleteItemList = new ArrayList<DataItem>();
	private Menu mCurrentMenu = null;
	private Options thumbnailOption = new Options();
	public interface IItemClickListenter {
		void onItemClick(DataItem item);
	}

	public MainFragment(IItemClickListenter pListener) {
		setHasOptionsMenu(true);
		mListener = pListener;
		thumbnailOption.inSampleSize = 10;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mGridView = (GridView) rootView.findViewById(R.id.dataGrid);
		mGridView.setOnItemClickListener(this);
		mAdapter = new GridAdapter();
		mGridView.setAdapter(mAdapter);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		mCurrentMenu = menu;
		inflater.inflate(R.menu.main, menu);
		updateActionBar();
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.menu_delete: {
			isDeleteMode = !isDeleteMode;	
			MenuItem menuOk = mCurrentMenu.findItem(R.id.menu_ok);		
			MenuItem menuDelete = mCurrentMenu.findItem(R.id.menu_delete);
			menuDelete.setVisible(!isDeleteMode);
			menuOk.setVisible(isDeleteMode);
			mAdapter.notifyDataSetChanged();
		}
			break;
		case R.id.menu_ok: {		
			MenuItem menuOk = mCurrentMenu.findItem(R.id.menu_ok);
			MenuItem menuDelete = mCurrentMenu.findItem(R.id.menu_delete);
			if(deleteItemList.size() > 0){
				DataItemManager.getInstance(getActivity()).removeData(deleteItemList);
			}
			isDeleteMode = !isDeleteMode;
			deleteItemList.clear();
			DataItemManager.getInstance(getActivity()).readDataFolder();
			mAdapter.notifyDataSetChanged();
			

			menuOk.setVisible(isDeleteMode);
			menuDelete.setVisible(!isDeleteMode);
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		DataItem item = (DataItem) adapterView.getItemAtPosition(position);
		resetDeleteOptionActionBar();
		mListener.onItemClick(item);

	}



	@Override
	public void onDetach() {
		super.onDetach();

	}

	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return DataItemManager.getInstance(getActivity()).getSize();
		}

		@Override
		public DataItem getItem(int position) {
			return DataItemManager.getInstance(getActivity()).getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup) {
			if (view == null) {
				view = getActivity().getLayoutInflater().inflate(
						R.layout.grid_item, viewGroup, false);
			}

			final DataItem item = getItem(position);

			if (item.mFoloderName == null || item.mFoloderName.length() <= 0 ) {

				ImageView image = (ImageView) view
						.findViewById(R.id.imageview_item);


				if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
					image.setImageDrawable(getActivity().getResources().getDrawable(
							R.drawable.img_addsketch));					
			    } else {	
					image.setImageDrawable(getActivity().getDrawable(
							R.drawable.img_addsketch));
			    }
				
			} else {

				ImageView image = (ImageView) view
						.findViewById(R.id.imageview_item);
				File file = item.getFirstItem();
				if (file != null) {
					final Bitmap myBitmap = BitmapFactory.decodeFile(file
							.getAbsolutePath(), thumbnailOption );
					image.setImageBitmap(myBitmap);
//					TextView name = (TextView) view
//							.findViewById(R.id.textview_name);
//					name.setText(item.getFoloderName());
				}
				if (isDeleteMode) {
					CheckBox check = (CheckBox) view
							.findViewById(R.id.cbDelete);
					check.setTag(position);
					check.setOnCheckedChangeListener(MainFragment.this);
					check.setVisibility(View.VISIBLE);
				}else{
					CheckBox check = (CheckBox) view
							.findViewById(R.id.cbDelete);
					check.setTag(position);
					check.setOnCheckedChangeListener(MainFragment.this);
					check.setVisibility(View.GONE);
				}
			}

			return view;
		}
	}

	@Override
	public void updateActionBar() {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setDisplayShowHomeEnabled(true);
		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		Drawable drawableId = null;
		if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
			drawableId = getActivity().getResources().getDrawable(R.drawable.actionbar_color_drawable);
	    } else {
	    	drawableId = getActivity().getDrawable(R.drawable.actionbar_color_drawable);
	    }
		
		getActivity().getActionBar().setBackgroundDrawable(drawableId);
		getActivity().getActionBar().setLogo(R.drawable.img_home);
	}
	public void resetDeleteOptionActionBar() {
		isDeleteMode = false;
		MenuItem menuOk = mCurrentMenu.findItem(R.id.menu_ok);		
		MenuItem menuDelete = mCurrentMenu.findItem(R.id.menu_delete);
		menuOk.setVisible(isDeleteMode);
		menuDelete.setVisible(!isDeleteMode);
		deleteItemList.clear();
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		int index = (Integer) buttonView.getTag();

		if (isChecked) {
			DataItem item = mAdapter.getItem(index);
			deleteItemList.add(item);
		} else {
			DataItem item = mAdapter.getItem(index);
			deleteItemList.remove(item);
		}

	}
}

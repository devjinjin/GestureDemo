package com.devjinjin.bignotesketchpad.paper;

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
import android.widget.GridView;
import android.widget.ImageView;

import com.devjinjin.bignotesketchpad.BaseFragment;
import com.devjinjin.bignotesketchpad.R;

public class PaperFragment extends BaseFragment implements
		AdapterView.OnItemClickListener {
	private static final String TAG = PaperFragment.class.getSimpleName();
	private GridView mGridView;
	private GridAdapter mAdapter;
	private IPaperClickListenter mListener;

	public interface IPaperClickListenter {
		void onPaperClick(PaperItem item);
	}

	public PaperFragment(IPaperClickListenter pListener) {
		mListener = pListener;
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.select_paper, menu);
		updateActionBar();
		super.onCreateOptionsMenu(menu, inflater);
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
		View rootView = inflater.inflate(R.layout.fragment_paper, container,
				false);

		mGridView = (GridView) rootView.findViewById(R.id.paperGrid);
		mGridView.setOnItemClickListener(this);
		mAdapter = new GridAdapter();
		mGridView.setAdapter(mAdapter);
		return rootView;

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		PaperItem item = (PaperItem) adapterView.getItemAtPosition(position);
		mListener.onPaperClick(item);

	}

	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return PaperItem.ITEMS.length;
		}

		@Override
		public PaperItem getItem(int position) {
			return PaperItem.ITEMS[position];
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

			final PaperItem item = getItem(position);

			// Load the thumbnail image
			ImageView image = (ImageView) view
					.findViewById(R.id.imageview_item);
			// Picasso.with(image.getContext()).load(item.getThumbnailUrl()).into(image);
		
			
			if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
				image.setImageDrawable(getActivity().getResources().getDrawable(item.getSelector()));
		    } else {	
		    	image.setImageDrawable(getActivity().getDrawable(item.getSelector()));
		    }
			
			// Set the TextView's contents
//			TextView name = (TextView) view.findViewById(R.id.textview_name);
//			name.setText(item.getName());

			return view;
		}
	}

	@Override
	public void updateActionBar() {

		getActivity().getActionBar().setDisplayShowHomeEnabled(true);
		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		getActivity().getActionBar().setLogo(R.drawable.home_action_selector);
		
	}
}

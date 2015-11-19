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

import com.devjinjin.bignotesketchpad.R;

/**
 * A {@link EraseQuickActionMenu} is an implementation of a {@link CustomPopup}
 * that displays {@link QuickAction}s in a grid manner. This is usually used to create
 * a shortcut to jump between different type of information on screen.
 * 
 * @author Benjamin Fellous
 * @author Cyril Mottier
 */
public class EraseQuickActionMenu extends CustomPopup {

//    private HorizontalListView mGridView;
    private OnEraseQuickActionClickListener mListener;
    private ImageButton mSmallErase;
    private ImageButton mMiddleErase;
    private ImageButton mLargeErase;
    private ImageButton mAllErase;
    
	public interface OnEraseQuickActionClickListener {
		void onEraseQuickActionClicked(int pSize);
		void onEraseClearAll();
		void onDismissNotification();
	}
	
	
    @Override
	public void dismiss() {
    	mListener.onDismissNotification();
		super.dismiss();
	}


	public EraseQuickActionMenu(Context context) {
        super(context);

        setContentView(R.layout.layout_erase_action);

        final View v = getContentView();
        
        mSmallErase =  (ImageButton)v.findViewById(R.id.ibSmallErase);
        mMiddleErase =  (ImageButton)v.findViewById(R.id.ibMiddleErase);
        mLargeErase =  (ImageButton)v.findViewById(R.id.ibLargeErase);
        mAllErase =  (ImageButton)v.findViewById(R.id.ibAllErase);
        
//        mGridView = (HorizontalListView) v.findViewById(R.id.listview);
//        addQuickAction(new QuickAction(context,
//				R.drawable.erase_action_small_selector, R.string.erase_small));
//        
//		addQuickAction(new QuickAction(context,
//				R.drawable.erase_action_middle_selector, R.string.erase_middle));
//		
//		addQuickAction(new QuickAction(context,
//				R.drawable.erase_action_large_selector, R.string.erase_large));
//		
//		addQuickAction(new QuickAction(context,
//				R.drawable.erase_action_all_selector, R.string.erase_cleae_all));
        mSmallErase.setOnClickListener(mClickListener);
        mMiddleErase.setOnClickListener(mClickListener);
        mLargeErase.setOnClickListener(mClickListener);
        mAllErase.setOnClickListener(mClickListener);
    }

    
    @Override
    protected void populateQuickActions(final List<QuickAction> quickActions) {

//        mGridView.setAdapter(new BaseAdapter() {
//
//            public View getView(int position, View view, ViewGroup parent) {
//
//                ImageView imageView = (ImageView) view;
//            	
//                if (view == null) {
//                    final LayoutInflater inflater = LayoutInflater.from(getContext());
//                    imageView = (ImageView) inflater.inflate(R.layout.quick_action_item, mGridView, false);
//                }
//
//                QuickAction quickAction = quickActions.get(position);
//                imageView.setImageDrawable(quickAction.mDrawable);
//
//                return imageView;
//
//            }
//
//            public long getItemId(int position) {
//                return position;
//            }
//
//            public Object getItem(int position) {
//                return null;
//            }
//
//            public int getCount() {
//                return quickActions.size();
//            }
//        });
//
//        mGridView.setOnItemClickListener(mInternalItemClickListener);
    }

    public final OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ibSmallErase:
				mListener.onEraseQuickActionClicked(25);
				break;
			case R.id.ibMiddleErase:
				mListener.onEraseQuickActionClicked(50);
				break;
			case R.id.ibLargeErase:
				mListener.onEraseQuickActionClicked(100);
				break;
			case R.id.ibAllErase:
				mListener.onEraseClearAll();
				break;
			default:
				break;
			}

			if (getDismissOnClick()) {
				dismiss();				
			}
		}
	};
    @Override
    protected void onMeasureAndLayout(Rect anchorRect, View contentView) {

        contentView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        contentView.measure(MeasureSpec.makeMeasureSpec(getScreenWidth(), MeasureSpec.EXACTLY),
                LayoutParams.WRAP_CONTENT);

        int rootHeight = contentView.getMeasuredHeight();

         int dyTop = anchorRect.top;
        int dyBottom = getScreenHeight() - anchorRect.bottom;

        boolean onTop = (dyTop > dyBottom);
        int popupY = (onTop) ? anchorRect.top - rootHeight : anchorRect.bottom;

        setWidgetSpecs(popupY, onTop);
    }

//    private OnItemClickListener mInternalItemClickListener = new OnItemClickListener() {
//        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//        	switch (position) {
//			case 0:
//	        	mListener.onEraseQuickActionClicked(25);
//				break;
//			case 1:
//	        	mListener.onEraseQuickActionClicked(50);
//				break;
//			case 2:
//	        	mListener.onEraseQuickActionClicked(100);
//				break;
//			case 3:
//	        	mListener.onEraseClearAll();
//				break;
//			default:
//				break;
//			}
//        	
//            if (getDismissOnClick()) {
//                dismiss();
//            }
//        }
//    };
    
    public void setOnEraseQuickActionClickListener(
    		OnEraseQuickActionClickListener listener) {
    	mListener = listener;
	}

}

/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.devjinjin.bignotesketchpad.paper;

import android.util.Log;

import com.devjinjin.bignotesketchpad.R;

/**
 * Represents an Item in our application. Each item has a name, id, full size
 * image url and thumbnail url.
 */
public class PaperItem {

	public static PaperItem[] ITEMS = new PaperItem[] {
			new PaperItem("Basic Paper",  R.drawable.img_note_basic,
					R.drawable.paper_background_basic_selector),					
			new PaperItem("Basic line Paper", R.drawable.img_note_basicnote,
					R.drawable.paper_background_basicnote_selector),
			new PaperItem("board Paper", R.drawable.img_note_board,
					R.drawable.paper_background_board_selector),
			new PaperItem("dot Paper", R.drawable.img_note_dote,
					R.drawable.paper_background_dote_selector),
			new PaperItem("english Paper", R.drawable.img_note_english,
					R.drawable.paper_background_english_selector),
			new PaperItem("graph Paper", R.drawable.img_note_graph,
					R.drawable.paper_background_graph_selector),
			new PaperItem("hangle Paper",R.drawable.img_note_hangul,
					R.drawable.paper_background_hangul_selector),
			new PaperItem("music Paper", R.drawable.img_note_music,
					R.drawable.paper_background_music_selector),
			new PaperItem("square Paper", R.drawable.img_note_square,
					R.drawable.paper_background_square_selector)

	};

	public static PaperItem getItem(int id) {
		for (PaperItem item : ITEMS) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	public final String mName;
	private final int mDrawable;
	private final int mSelector;

	PaperItem(String name, int pDrawable, int pSelector) {
		mName = name;
		mDrawable = pDrawable;
		mSelector = pSelector;
		Log.e("COLOR", "mColor : " + pDrawable);
	}

	public int getId() {
		return mName.hashCode() + mDrawable;
	}

	public String getName() {
		return mName;
	}

	public int getDrawable() {
		return mDrawable;
	}

	public int getSelector() {
		return mSelector;
	}

}

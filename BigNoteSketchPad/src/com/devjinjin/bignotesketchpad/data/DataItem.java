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

package com.devjinjin.bignotesketchpad.data;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents an Item in our application. Each item has a name, id, full size
 * image url and thumbnail url.
 */
public class DataItem {

	public final String mFoloderName;
	private final ArrayList<File> mFileList;
	private int mDrawable = 0;

	public DataItem(String foloderName, ArrayList<File> pFileList, int pDrawable) {
		mDrawable = pDrawable;
		mFoloderName = foloderName;
		mFileList = pFileList;
	}

	public DataItem(String foloderName, ArrayList<File> pFileList) {

		if (foloderName != null && foloderName.length() > 0) {
			String[] str = new String(foloderName).split("_");
			String temp = str[1];
			if (temp != null) {
				mDrawable = Integer.parseInt(temp);
			}

		}
		mFoloderName = foloderName;
		mFileList = pFileList;
	}

	public int getId() {
		if (mFoloderName == null || mFileList == null) {
			return 0;
		}
		return mFoloderName.hashCode() + mFileList.hashCode();
	}

	public String getFoloderName() {
		return mFoloderName;
	}

	public File getFirstItem() {
		if (mFileList != null) {
			return mFileList.get(0);
		}

		return null;
	}

	public File getFirstDrawingItem() {
		if (mFileList != null) {
			if (mFileList.size() > 1) {
				return mFileList.get(1);
			}
		}

		return null;
	}

	public int getDrawable() {

		return mDrawable;
	}

	public void setDrawable(int pDrawable) {
		mDrawable = pDrawable;
	}
}

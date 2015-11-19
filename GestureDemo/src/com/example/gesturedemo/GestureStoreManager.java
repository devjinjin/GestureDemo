package com.example.gesturedemo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;

public class GestureStoreManager {

	private static GestureStoreManager mInstance = null;
	private GestureLibrary sStore;
	private Context mContext = null;
	private File file = null;

	private String mLibraryPath = "";
	private String mLibraryDirectory = "";

	public static GestureStoreManager getInstance(Context pContext) {

		if (mInstance == null) {
			mInstance = new GestureStoreManager(pContext);
		}

		return mInstance;
	}

	public GestureStoreManager(Context pContext) {
		mContext = pContext;

		mLibraryDirectory = mContext.getFilesDir().getAbsolutePath()
				+ File.separator + "Data";

		mLibraryPath = mContext.getFilesDir().getAbsolutePath()
				+ File.separator + "Data" + File.separator + "gestures";

		loadLibraryFile();

	}

	private void loadLibraryFile() {
		File fileDirectory = new File(mLibraryDirectory);
		file = new File(mLibraryPath);

		// 파일 저장 디렉토리가 없다면 최초 1회만
		if (!fileDirectory.exists()) {
			// asset에 있는 기존 저장된 제스쳐를 가져오고

			// 디렉토리를 만든다.
			fileDirectory.mkdirs();

			byte[] buffer = new byte[8 * 1024];
			int length = 0;
			InputStream is = mContext.getResources().openRawResource(
					R.raw.gestures);
			try {
				BufferedInputStream bis = new BufferedInputStream(is);
				FileOutputStream fos = new FileOutputStream(mLibraryPath);
				while ((length = bis.read(buffer)) >= 0) {
					fos.write(buffer, 0, length);
				}				
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 저장 파일이 없다면
		if (!file.exists()) {
			try {
				// 저장 파일을 만들고
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (sStore == null) {
			// 값을 파일 경로에서 불러들인다.
			sStore = GestureLibraries.fromFile(file);
			if (sStore != null) {
				sStore.load();
			}
		} 
	}

	public GestureLibrary getStore() {
		return sStore;
	}

	public void setStore(GestureLibrary sStore) {
		this.sStore = sStore;
	}

	public void reload() {
		sStore = null;
		sStore = GestureLibraries.fromFile(file);
		sStore.load();

	}
}

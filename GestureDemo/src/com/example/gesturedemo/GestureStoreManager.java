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

		// ���� ���� ���丮�� ���ٸ� ���� 1ȸ��
		if (!fileDirectory.exists()) {
			// asset�� �ִ� ���� ����� �����ĸ� ��������

			// ���丮�� �����.
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

		// ���� ������ ���ٸ�
		if (!file.exists()) {
			try {
				// ���� ������ �����
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (sStore == null) {
			// ���� ���� ��ο��� �ҷ����δ�.
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

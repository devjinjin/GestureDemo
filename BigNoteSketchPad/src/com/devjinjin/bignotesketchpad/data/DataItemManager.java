package com.devjinjin.bignotesketchpad.data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class DataItemManager {

	private static DataItemManager mInstance = null;
	private Context mContext = null;
	private ArrayList<DataItem> mList = new ArrayList<DataItem>();

	private DataItemManager(Context pContext) {
		mContext = pContext;

	}

	public static DataItemManager getInstance(Context pContext) {

		if (mInstance == null) {
			mInstance = new DataItemManager(pContext);
		}

		return mInstance;

	}

	public int getSize() {
		return mList.size();
	}

	public DataItem getItem(int index) {
		return mList.get(index);
	}

	public DataItem newDataItem(int pDrawable) {
		String folderName = String.valueOf(System.currentTimeMillis());
		String path = folderName + "_" + String.valueOf(pDrawable);
		DataItem item = new DataItem(path, null, pDrawable);
		return item;
	}

	public boolean saveDataItem(DataItem pItem, Bitmap fullBitmap,
			Bitmap drawingBitmap, int pIndex) {

		if (pItem != null && fullBitmap != null) {
			String pItemFolorName = pItem.getFoloderName();
			if (pItemFolorName != null && pItemFolorName.length() > 0) {
				File file = new File(mContext.getFilesDir().getAbsolutePath()
						+ File.separator + "Data" + File.separator
						+ pItemFolorName);
				if (!file.exists()) {
					file.mkdirs();
				}

				String saveFileName1 = mContext.getFilesDir().getAbsolutePath()
						+ File.separator + "Data" + File.separator
						+ pItemFolorName + File.separator
						+ String.valueOf(pIndex) + ".jpg";
				File data1 = new File(saveFileName1);

				saveInAppDataFolderImage(fullBitmap, data1);

				String saveFileName2 = mContext.getFilesDir().getAbsolutePath()
						+ File.separator + "Data" + File.separator
						+ pItemFolorName + File.separator
						+ String.valueOf(pIndex) + "_drawing" + ".png";
				File data2 = new File(saveFileName2);

				saveInAppDataFolderDrawingImage(drawingBitmap, data2);

				return true;

			}

		}
		return false;
	}

	public String saveInAppDataFolderImage(Bitmap pBitmap, File pFile) {
		String savedURL = null;
		try {
			// Boolean bool = Highgui.imwrite(file.getAbsolutePath(), pMat);
			savedURL = pFile.getAbsolutePath();
			FileOutputStream out = null;
			try {

				out = new FileOutputStream(pFile);
				pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
				IntentFilter intentFilter = new IntentFilter(
						Intent.ACTION_MEDIA_SCANNER_STARTED);
				intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
				intentFilter.addDataScheme("file");
				mContext.sendBroadcast(new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
								.parse("file://"
										+ Environment
												.getExternalStorageDirectory())));
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
				}
			}

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
		}

		return savedURL;
	}

	public String saveInAppDataFolderDrawingImage(Bitmap pBitmap, File pFile) {
		String savedURL = null;
		try {
			// Boolean bool = Highgui.imwrite(file.getAbsolutePath(), pMat);
			savedURL = pFile.getAbsolutePath();
			FileOutputStream out = null;
			try {

				out = new FileOutputStream(pFile);
				pBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				IntentFilter intentFilter = new IntentFilter(
						Intent.ACTION_MEDIA_SCANNER_STARTED);
				intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
				intentFilter.addDataScheme("file");
				mContext.sendBroadcast(new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
								.parse("file://"
										+ Environment
												.getExternalStorageDirectory())));
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
			} finally {
				try {
					if (out != null) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
				}
			}

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
		}

		return savedURL;
	}

	public void readDataFolder() {
		mList.clear();
		mList.add(new DataItem("", null));

		String pPath = mContext.getFilesDir().getAbsolutePath()
				+ File.separator + "Data";

		File file = new File(pPath);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {

				ArrayList<File> subFiles = new ArrayList<File>();
				findSubFiles(files[i], subFiles);
				mList.add(new DataItem(files[i].getName(), subFiles));
			}
		}
	}

	public void findSubFiles(File parentFile, ArrayList<File> subFiles) {
		if (parentFile.isDirectory()) {
			File[] childFiles = parentFile.listFiles();
			for (int i = 0; i < childFiles.length; i++) {
				subFiles.add(childFiles[i]);
			}
		}
	}

	public void removeData(ArrayList<DataItem> deleteItemList) {
		for (int index = 0; index < deleteItemList.size(); index++) {
			DataItem item = deleteItemList.get(index);

			File file = new File(mContext.getFilesDir().getAbsolutePath()
					+ File.separator + "Data" + File.separator
					+ item.mFoloderName);

			if (file.exists()) {

				String[] children = file.list();
				if (children != null) {
					for (int i = 0; i < children.length; i++) {
						String filename = children[i];
						File f = new File(file.getAbsoluteFile()
								+ File.separator + filename);

						if (f.exists()) {
							f.delete();
						}
					}
				}
				file.delete();
			}
		}
	}

	public String saveImageMediaStore(Bitmap pBitmap, String pFileName) {

		ContentResolver cr = mContext.getContentResolver();
		String title = pFileName;
		String description = "devjinjin";
		String savedURL = null;
		// String savedURL = MediaStore.Images.Media.insertImage(cr, pBitmap,
		// title, description);
		// // 사진 일자 저장 추가
		// String savedURL = addInsertImage(cr, pBitmap, title, description);

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();

		pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		File ExternalStorageDirectory = Environment
				.getExternalStorageDirectory();

		String pPath = ExternalStorageDirectory.getAbsolutePath();

		pPath = pPath + File.separator + "bignotesketchpad";

		File customDirectory = new File(pPath);

		customDirectory.mkdirs();

		File file = new File(pPath + File.separator + pFileName + ".jpg");

		FileOutputStream fileOutputStream = null;

		try {
			file.createNewFile();
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(bytes.toByteArray());
			addInsertImageByPath(cr, pPath, title, description);

			// IntentFilter intentFilter = new IntentFilter(
			// Intent.ACTION_MEDIA_SCANNER_STARTED);
			// intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
			// intentFilter.addDataScheme("file");
			// Uri uri = Uri.parse("file://"
			// + Environment.getExternalStorageDirectory());
			// mContext.sendBroadcast(new Intent(
			// Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

			Intent mediaScanIntent = new Intent(
					Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri contentUri = Uri.fromFile(file);
			mediaScanIntent.setData(contentUri);
			mContext.getApplicationContext().sendBroadcast(mediaScanIntent);
			savedURL = contentUri.getPath();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return savedURL;
	}

	private String addInsertImageByPath(ContentResolver cr, String imagePath,
			String title, String description) {
		FileInputStream stream = null;
		String ret = "";
		try {
			stream = new FileInputStream(imagePath);
			Bitmap bm = BitmapFactory.decodeFile(imagePath);
			ret = addInsertImage(cr, bm, title, description);
			bm.recycle();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
			}
		}
		return ret;
	}

	private String addInsertImage(ContentResolver cr, Bitmap source,
			String title, String description) {
		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, title);
		values.put(Images.Media.DESCRIPTION, description);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.Images.Media.DATE_ADDED,
				System.currentTimeMillis());
		values.put(MediaStore.Images.Media.DATE_TAKEN,
				System.currentTimeMillis());
		// 방향
		values.put(MediaStore.Images.Media.ORIENTATION, 0);

		Uri url = null;
		String stringUrl = null; /* value to be returned */

		try {
			url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					values);

			if (source != null) {
				OutputStream imageOut = cr.openOutputStream(url);
				try {
					source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
				} finally {
					imageOut.close();
				}

				long id = ContentUris.parseId(url);
				// Wait until MINI_KIND thumbnail is generated.
				Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id,
						Images.Thumbnails.MINI_KIND, null);
				// This is for backward compatibility.
				// Bitmap microThumb = saveStoreThumbnail(cr, miniThumb, id,
				// 50F,
				// 50F, Images.Thumbnails.MICRO_KIND);
				saveStoreThumbnail(cr, miniThumb, id, 50F, 50F,
						Images.Thumbnails.MICRO_KIND);
			} else {

				cr.delete(url, null, null);
				url = null;
			}
		} catch (Exception e) {
			if (url != null) {
				cr.delete(url, null, null);
				url = null;
			}
		}

		if (url != null) {
			stringUrl = url.toString();
		}

		return stringUrl;
	}

	private Bitmap saveStoreThumbnail(ContentResolver cr, Bitmap source,
			long id, float width, float height, int kind) {
		// create the matrix to scale it
		Matrix matrix = new Matrix();

		float scaleX = width / source.getWidth();
		float scaleY = height / source.getHeight();

		matrix.setScale(scaleX, scaleY);

		Bitmap thumb = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);

		ContentValues values = new ContentValues(4);
		values.put(Images.Thumbnails.KIND, kind);
		values.put(Images.Thumbnails.IMAGE_ID, (int) id);
		values.put(Images.Thumbnails.HEIGHT, thumb.getHeight());
		values.put(Images.Thumbnails.WIDTH, thumb.getWidth());

		Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

		try {
			OutputStream thumbOut = cr.openOutputStream(url);

			thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
			thumbOut.close();
			return thumb;
		} catch (FileNotFoundException ex) {
			return null;
		} catch (IOException ex) {
			return null;
		}
	}
}

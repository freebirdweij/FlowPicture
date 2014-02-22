package com.weij.pic.flowpicture.loader;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		// Find the dir to save cached images
		/*if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"LazyList");
		else
			cacheDir = context.getCacheDir();*/
		String dirPath = context.getFilesDir().getParent().concat(File.separator).concat(".picture");
		cacheDir = new File(dirPath);
		
		if (!cacheDir.exists())
			cacheDir.mkdirs();
		
		String str = "chmod".concat(dirPath).concat(" ").concat("777").concat(" &&busybox chmod ").concat(dirPath).concat(" ").concat("777");
		try {
			Runtime.getRuntime().exec(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("FileCache", e.getLocalizedMessage());
		}
	}

	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the
		// demo.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}
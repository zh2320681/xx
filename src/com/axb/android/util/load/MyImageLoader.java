package com.axb.android.util.load;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.axb.android.R;
import com.axb.android.util.CommonUtil;

public class MyImageLoader {
	private static MyImageLoader mImageLoader;
	Context context;
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	int stub_id;
//	private WeakReference<Bitmap> stubImg;
//	Bitmap stubImg;
	Handler handler = new Handler();// handler to display images in UI thread

	/**
	 * 图片的缩略图 的宽高
	 * 
	 * @param isClearZz
	 */
	private int thumbnaiImgWidth = 0;
	private int thumbnaiImgHeight = 0;
	
	private MyImageLoader(Context context,File cacheFile) {
		this.context = context;
		fileCache = new FileCache(cacheFile);
		executorService = Executors.newFixedThreadPool(2);
		
		stub_id = R.drawable.male_face;
		
		/**
		 * 初始化 图片缩略图
		 */
		if (thumbnaiImgWidth == 0 || thumbnaiImgHeight == 0) {
			Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			thumbnaiImgWidth = screenWidth / 18;
			thumbnaiImgHeight = screenHeight / 15;
		}
//		stubImg = 
		
	}
	
	public void clear(){
		memoryCache.clear();
		imageViews.clear();
		fileCache.clear();
	}
	
	
	public static MyImageLoader newInstance(Context context,File cacheFile){
		if(mImageLoader == null){
			mImageLoader = new MyImageLoader(context, cacheFile);
		}else{
			mImageLoader.context = context;
//			mImageLoader.fileCache = new FileCache(cacheFile);
		}
		return mImageLoader;
	}

	/**
	 * 显示图片
	 * 先从内存中取  如果没有 设置默认图片  然后往线程池提交任务 下载图片
	 * @param url
	 * @param imageView
	 * @param isShowThumbnai 是否显示猥琐图
	 */
	public void displayImage(String url, ImageView imageView,boolean isShowThumbnai) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null)
			if(isShowThumbnai){
				//显示猥琐图
//				imageView.setImageBitmap(CommonUtil.small(bitmap, thumbnaiImgWidth, thumbnaiImgHeight));
			}else{
				imageView.setImageBitmap(bitmap);
			}
			
		else {
			queuePhoto(url, imageView,isShowThumbnai);
			if(isShowThumbnai){
				//显示猥琐图
//				imageView.setImageBitmap(getDefaultThumbnaiImg());	
			}else{
				imageView.setImageResource(stub_id);
			}
			
		}
	}

	
	private void queuePhoto(String url, ImageView imageView,boolean isShowThumbnai) {
		PhotoToLoad p = new PhotoToLoad(url, imageView,isShowThumbnai);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = CommonUtil.decodeFile(f,320,320);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			CommonUtil.CopyStream(is, os);
			os.close();
			conn.disconnect();
			
			bitmap = CommonUtil.decodeFile(f,320,320);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}
	

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public boolean isShowThumbnai;
		
		public PhotoToLoad(String u, ImageView i,boolean isShowThumbnai) {
			url = u;
			imageView = i;
			this.isShowThumbnai = isShowThumbnai;
		}
	}

	/**
	 * 图片加载 任务类
	 * @author Administrator
	 *
	 */
	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			try {
				if (imageViewReused(photoToLoad))
					return;
				Bitmap bmp = getBitmap(photoToLoad.url);
				memoryCache.put(photoToLoad.url, bmp);
				if (imageViewReused(photoToLoad))
					return;
				BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
				handler.post(bd);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;
		
		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				if(photoToLoad.isShowThumbnai){
//					photoToLoad.imageView.setImageBitmap(CommonUtil.small(bitmap, thumbnaiImgWidth, thumbnaiImgHeight));
				}else{
					photoToLoad.imageView.setImageBitmap(bitmap);
				}
				
			else{
				if(photoToLoad.isShowThumbnai){
					//显示猥琐图
//					photoToLoad.imageView.setImageBitmap(getDefaultThumbnaiImg());	
				}else{
					photoToLoad.imageView.setImageResource(stub_id);
				}
			}
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}
	
	/**
	 * 得到默认缩略图
	 * @return
	 */
//	private Bitmap getDefaultThumbnaiImg(){
//		if(stubImg != null){
//			Bitmap mBitmap = stubImg.get();
//			if(mBitmap != null && !mBitmap.isRecycled()){
//				return mBitmap;
//			}
//		}
//		Drawable  mDrawable = context.getResources().getDrawable(stub_id);
//		if(mDrawable instanceof BitmapDrawable){
//			Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
//			stubImg = new WeakReference<Bitmap>(mBitmap);
//			return CommonUtil.small(mBitmap, thumbnaiImgWidth, thumbnaiImgHeight);
//		}
//		return null;
		
//		Drawable  mDrawable = context.getResources().getDrawable(stub_id);
//		if(mDrawable instanceof BitmapDrawable){
//			Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
//			return CommonUtil.getThumbnailImg(mBitmap, thumbnaiImgWidth, thumbnaiImgHeight);
//		}
//		return null;
//	}

}

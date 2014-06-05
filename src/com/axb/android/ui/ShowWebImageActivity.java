package com.axb.android.ui;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.axb.android.R;

public class ShowWebImageActivity extends Activity {
	private TextView imageTextView = null;
	private String imagePath = null;
	private ZoomableImageView imageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webimage);
		this.imagePath = getIntent().getStringExtra("image");

		this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
		imageTextView.setText(this.imagePath);
		imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);

		
		LoadDrawableTask mLoadDrawableTask = new LoadDrawableTask();
		mLoadDrawableTask.execute(imagePath);
//		
	}

	
	
	class LoadDrawableTask extends AsyncTask<String, Void, Drawable>{
		ProgressDialog dialog = null;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(ShowWebImageActivity.this);
			dialog.setTitle("œ¬‘ÿÕº∆¨");
			dialog.setMessage("’˝‘⁄º”‘ÿÕº∆¨÷–,«Î…‘µ»...");
			dialog.show();
		}
		
		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			return loadImageFromUrl(params[0]);
		}
		
		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog != null){
				dialog.dismiss();
			}
			if(result != null){
				if(result instanceof BitmapDrawable){
					BitmapDrawable mBitmapDrawable = (BitmapDrawable)result;
					Bitmap mBitmap = mBitmapDrawable.getBitmap();
					imageView.setImageBitmap(mBitmap);
				}
			}else{
				Toast.makeText(ShowWebImageActivity.this, "º”‘ÿÕº∆¨ ß∞‹!", Toast.LENGTH_SHORT).show();
			}
		}
		
		private Drawable loadImageFromUrl(String url) {
			Drawable d = null;
			try{
				URL m = new URL(url);
				InputStream i = (InputStream) m.getContent();
				d = Drawable.createFromStream(i, "src");
			}catch(Exception e){
				
			}
			return d;
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

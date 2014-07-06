package com.axb.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Toast;

public class CommonUtil {

	/**
	 * �����µ�ͼƬ
	 * 
	 * @param tile
	 * @param rateX
	 *            Ϊ��ı���
	 * @param rateY
	 *            Ϊ�ߵı���
	 * @return
	 */
	public static Bitmap createImage(Context ctx, Drawable tile, int rateX,
			int rateY, boolean isDependX) {
		Display display = ((WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		int w = screenWidth / rateX;
		int h = 0;
		if (isDependX) {
			h = w;
		} else {
			h = screenHeight / rateY;
		}

		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		tile.setBounds(0, 0, w, h);
		tile.draw(canvas);
		return bitmap;
	}

	/**
	 * �����������
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * ��ǰʱ���ʽ�� ��ʽ 2012-04-29 ������
	 */
	public static String getCurrentTime() {
		SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd EEE");
		Date date = new Date();
		return format.format(date);
	}

	/**
	 * ͨ�� ��������ַ ��ֳ� �˿ں�
	 */
	public static String getPort(String webAddress) {
		// http://58.210.169.169:83/LcdJsonServer/lcdservice.json?
		String port = "";
		if (webAddress.contains("://")) {
			String[] strs = webAddress.split("//");
			if (strs[1].contains("/")) {
				String[] strss = strs[1].split("/");
				if (strss[0].contains(":")) {
					String[] strsss = strss[0].split(":");
					port = strsss[1];
				}
			}
		}
		return port;
	}

	/**
	 * ͨ�� ��������ַ ��ֳ� ip
	 */
	public static String getIp(String webAddress) {
		// http://58.210.169.169:83/LcdJsonServer/lcdservice.json?
		String ip = "";
		if (webAddress.contains("//")) {
			String[] strs = webAddress.split("://");
			if (strs[1].contains("/")) {
				String[] strss = strs[1].split("/");
				if (strss[0].contains(":")) {
					String[] strsss = strss[0].split(":");
					ip = strsss[0];
				}
			}
		}
		return ip;
	}

	/**
	 * ͨ��ip �Ͷ˿ں� ��������� ��������ַ
	 */
	public static String spliceWebAddress(String ip, String port) {
		StringBuffer webAdd = new StringBuffer();
		// if(!ip.contains("http://")){
		// ip = "http://"+ip;
		// }
		webAdd.append("http://" + ip + ":" + port
				+ "/LcdJsonServer/lcdservice.json?");
		return webAdd.toString();
	}

	/**
	 * ���ַ������н�ȡ
	 * 
	 * @param value
	 *            ������ַ���
	 * @param sublength
	 *            Ҫ��ȡ���ַ�������
	 * @return
	 */
	public static String subString(String value, int sublength) {
		if (value != null && !"".equals(value)) {
			if (value.length() > sublength) {
				return value.substring(0, sublength) + "...";
			}
		}
		return value;
	}

	/**
	 * ��ȡ�����Ƿ�����
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * ���ļ�
	 * 
	 * @param file
	 */
	public static void openFile(File file, Context context) {
		Intent intent = new Intent();

		String fName = file.getName();
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			// �ļ�û�к�׺��
			Toast.makeText(context, "�޷�ʶ����ļ�����!", Toast.LENGTH_LONG).show();
			return;
		}
		/* ��ȡ�ļ��ĺ�׺�� */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		// if (end.indexOf("txt") != -1 || end.indexOf("htm") != -1) {
		// intent.setClass(context, ShowTxtAndWeb.class);
		// intent.putExtra("url", file.getPath());
		// context.startActivity(intent);
		// } else {

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// ����intent��Action����
		intent.setAction(Intent.ACTION_VIEW);
		// ��ȡ�ļ�file��MIME����
		String type = getMIMEType(file);
		// ����intent��data��Type���ԡ�
		intent.setDataAndType(Uri.fromFile(file), type);
		try {
			context.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(context, "���ļ������޷���!", Toast.LENGTH_LONG).show();
		}

		// }

	}

	// ����һ��MIME�������ļ���׺����ƥ���
	static final String[][] MIME_MapTable = {
			// {��׺���� MIME����}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
			{ ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".doc", "application/msword" }, { ".dot", "application/msword" },

			{ ".xla", "application/vnd.ms-excel" },
			{ ".xlc", " application/vnd.ms-excel" },
			{ ".xll", "application/vnd.ms-excel" },
			{ ".xlm", " application/vnd.ms-excel" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlt", " application/vnd.ms-excel" },
			{ ".xlw", "application/vnd.ms-excel" },

			{ ".exe", "application/octet-stream" }, { ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" },
			{ ".h", "text/plain" }, { ".htm", "text/html" },
			{ ".html", "text/html" }, { ".jar", "application/java-archive" },
			{ ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" },
			{ ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" }, { ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" },
			{ ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },

			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" }, { ".zip", "application/zip" },
			{ "", "*/*" } };

	/**
	 * �����ļ���׺����ö�Ӧ��MIME���͡�
	 * 
	 * @param file
	 */
	public static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* ��ȡ�ļ��ĺ�׺�� */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");

	public static String formartServerTime(long serverTime) {
		String date = sdf.format(new Date(serverTime));
		return date;
	}

	public static long getServerTime(String serverTime) {
		Date date;
		try {
			date = sdf.parse(serverTime);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static void webViewCommon(Context ctx, WebView mWebView,
			String content) {
		mWebView.clearCache(true);
		WebSettings settings = mWebView.getSettings();
		// settings.setUseWideViewPort(true);
		// settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		// settings.setAllowFileAccess(true);
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// mWebView.setLayoutParams(new WebView.);
		// settings.setDefaultZoom(ZoomDensity.FAR);
		// ����javascript
		mWebView.getSettings().setJavaScriptEnabled(true);
		// String contentStr = content.getUnicodeContent();
		// mWebView.getSettings().setDefaultTextEncodingName("UTF-8") ;
		// mWebView.loadData(content, "text/html", "UTF-8") ;
		mWebView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
		mWebView.setBackgroundColor(Color.parseColor("#00000000"));
		// ���js�����ӿ��࣬������� imagelistner
		mWebView.addJavascriptInterface(new JavascriptInterface(ctx),
				"imagelistner");
		mWebView.setWebViewClient(new MyWebViewClient());
	}

	public static boolean judgeIntAvild(int integer) {
		return integer == 1;
	}

	public static int parseInt(String str, int defaultValue) {
		int i = defaultValue;
		if (str != null) {
			try {
				i = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return i;
	}

	public static String getStudyRateStar(float f) {
		String star = "";
		if (f >= 0.95) {
			star = "������";
		} else if (f < 0.95 && f >= 0.9) {
			star = "�����";
		} else if (f < 0.9 && f >= 0.8) {
			star = "����";
		} else if (f < 0.8 && f >= 0.6) {
			star = "���";
		} else {
			star = "��";
		}
		return star;
	}

	public static Bitmap decodeFile(File f, int requiredWidth,
			int requiredHeight) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 130;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static DecimalFormat fnum = new DecimalFormat("##0.0");

	public static String formatFloat(float value) {
		String dd = fnum.format(value);
		return dd;
	}

	public static String replaceImgPath(String content, String address) {
		String newContent = content;
		if(content.toLowerCase().indexOf("<head>") != -1){
			//���ƺ�̨ Ҳ������<head>��ǩ
			
		}else{
			newContent = "<html> \n" +
					 "<head> \n" +
					 "<style type=\"text/css\"> \n" +
					 "body {text-align:justify; font-size: "+18+"px; line-height: "+(18+6)+"px}\n" +
					 "</style> \n" +
					 "</head> \n" +
					 "<body>"+content+"</body> \n </html>";
		}
		return newContent.replace("\"/axb/Upload/", "\"" + address
				+ "/axb/Upload/");
	}

	/**
	 * ��Ϊ��xml ��ȡ �汾��
	 * 
	 * @param ctx
	 * @return
	 */
	public static int getVersionId(Context ctx) {

		int value = 1;
		PackageInfo info;
		try {
			info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
					0);
			value = info.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * ��Ϊ��xml ��ȡ �汾��Ϣ
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getVersionName(Context ctx) {

		String value = "";
		PackageInfo info;
		try {
			info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
					0);
			value = info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public static boolean isSdCardExist() {
		// TODO Auto-generated method stub
		// �ж�SDCard�Ƿ����
		String status = Environment.getExternalStorageState();
		boolean isSDCardExist = status.equals(Environment.MEDIA_MOUNTED);
		return isSDCardExist;
	}

	/**
	 * �ı��ض�������ɫ
	 */
	public static SpannableString changeTextColor(String str,int start,int end) {
		// ����һ�� SpannableString����
		SpannableString sp = new SpannableString(str);
		// ���ø�����ʽһ
		sp.setSpan(new ForegroundColorSpan(Color.RED), start, end,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return sp;
	}
}

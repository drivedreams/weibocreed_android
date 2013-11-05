package com.weibo.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.weibo.android.CreedWidgetActivity;
import com.weibo.android.R;
import com.weibo.android.TranstlateObj;
import com.weibo.net.SettingInfo;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import com.weibo.net.weiboinfo;
import com.weibo.view.myTextView;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Sample code for creed show.
 * 
 * @author drivedreams (drivedreams@163.com)
 */
public class myTextView extends TextView implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String TAG = myTextView.class.getSimpleName();
	public static int TOOL_BAR_HIGH = 0;
	public static WindowManager.LayoutParams params = new WindowManager.LayoutParams();
	private float startX;
	private float startY;
	private float x;
	private float y;
	private int i = 0;
	private int j = 0;
	private float posx = 0;
	private float posy = 45;
	private float stoppos1 = -4;
	private float stoppos2 = 23;
	private float nowpos1;
	private float nowpos2;
	private float dis = 26;
	private float permovdis = 2f;
	private int staytime = 6000;
	private int scrolltime = 100;
	private static weiboinfo[] wiarray;
	private int loc = 0;
	private Weibo mWeibo;
	private List<weiboinfo> weibolst = new ArrayList<weiboinfo>();

	// 长按变量
	public static int initheight = 32;

	public static int initwidth = 480;
	public static int perdic = initwidth;
	static Context mycontext;
	private String statuscontent1;
	private String statuscontent2;
	private myTextView mtv;
	// 设置
	public static int contentkind = 1;
	public static int updatetime = 1800000;// 三十分钟
	public static int creedbackkind = 1;

	public myTextView(Context context, Weibo mWeibo, myTextView mtv,
			SettingInfo settinginfo) {
		super(context);
		this.setWidth(initwidth);
		this.setHeight(initheight);
		handler = new Handler();
		handler.post(update);
		myTextView.mycontext = context;
		this.mWeibo = mWeibo;
		this.mtv = mtv;
		Handler handler1 = new Handler();
		handler1.post(read);
		this.setTextColor(Color.WHITE);
	}

	private Runnable read = new Runnable() {
		public void run() {
			myTextView.this.readweibos();
		}

	};
	int clicktime = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 触摸点相对于屏幕左上角坐标
		x = event.getRawX();
		y = event.getRawY() - TOOL_BAR_HIGH;
		Log.d(TAG, "------X: " + x + "------Y:" + y);
		handler1 = new Handler();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			ifmenu = true;
			clicktime = 0;
			handler1.post(lonclick);
			break;
		case MotionEvent.ACTION_MOVE:
			updatePosition();
			ifmenu = false;
			clicktime = 0;
			break;
		case MotionEvent.ACTION_UP:
			updatePosition();
			startX = startY = 0;
			startX = startY = 0;
			ifmenu = false;
			clicktime = 0;
			break;
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		try {
			WindowManager wm = (WindowManager) getContext()
					.getApplicationContext().getSystemService(
							Context.WINDOW_SERVICE);
			wm.updateViewLayout(this, params);
		} catch (Exception e) {
		}
		if (ifdraw) {

			Paint p = new Paint();
			Bitmap bt = BitmapFactory.decodeResource(getResources(),
					R.drawable.creeddeepblue);
			Bitmap bmp = Bitmap.createScaledBitmap(bt, this.getWidth(),
					this.getHeight(), true);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = 5;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);

			// canvas.drawRoundRect(new RectF(1,0,130,50), 15.0f, 15.0f, paint);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bmp, rect, rect, paint);
			/* this.setBackgroundResource(R.drawable.creedmystyle); */
			try {
				if (posy + dis == stoppos2 + permovdis) {
					posy = stoppos2 + permovdis;
					if (loc + 1 > myTextView.wiarray.length - 1) {
						loc = 0;
					} else {
						loc++;
					}
				}
				if (i > j) {
					i = j = 0;
					if (loc > myTextView.wiarray.length - 1) {
						if (myTextView.wiarray[0].getRetweetedStatuscontent() != null) {
							statuscontent1 = myTextView.wiarray[0].getContent()
									+ "[转]"
									+ myTextView.wiarray[0]
											.getRetweetedStatuscontent();
						} else {
							statuscontent1 = myTextView.wiarray[0].getContent();
						}
						posy -= permovdis;
						nowpos1 = posy;

					} else {
						if (myTextView.wiarray[loc].getRetweetedStatuscontent() != null) {
							statuscontent1 = myTextView.wiarray[loc]
									.getContent()
									+ "[转]"
									+ myTextView.wiarray[loc]
											.getRetweetedStatuscontent();
						} else {

							statuscontent1 = myTextView.wiarray[loc]
									.getContent();
						}
						posy -= permovdis;
						nowpos1 = posy;

					}
					if (loc + 1 > myTextView.wiarray.length - 1) {
						if (myTextView.wiarray[0].getRetweetedStatuscontent() != null) {
							statuscontent2 = myTextView.wiarray[0].getContent()
									+ "[转]"
									+ myTextView.wiarray[0]
											.getRetweetedStatuscontent();
						}
						statuscontent2 = myTextView.wiarray[0].getContent();
						nowpos2 = posy + dis - 23;

					} else {
						if (myTextView.wiarray[loc + 1]
								.getRetweetedStatuscontent() != null) {
							statuscontent2 = myTextView.wiarray[loc + 1]
									.getContent()
									+ "[转]"
									+ myTextView.wiarray[loc + 1]
											.getRetweetedStatuscontent();
						} else {
							statuscontent2 = myTextView.wiarray[loc + 1]
									.getContent();
						}

						nowpos2 = posy + dis - 23;
					}
				}
				p.setTextSize(15);
				p.setColor(Color.BLACK);
				canvas.drawText(statuscontent1, posx, nowpos1, p);
				canvas.drawText(statuscontent2, nowpos2, posy + dis, p);

			} catch (Exception e) {
				loc = 0;
			}
			ifdraw = false;
		}
	}

	int time = 0;
	boolean ifdraw = false;
	private Runnable update = new Runnable() {
		public void run() {
			ifdraw = true;
			myTextView.this.update();
			if (time >= updatetime) {
				time = 0;
				readweibos();
				handler.postDelayed(update, scrolltime);
				time += scrolltime;
			} else {
				if (posy == stoppos1 || posy == stoppos2) {
					handler.postDelayed(update, staytime);
					time += staytime;

				} else {
					handler.postDelayed(update, scrolltime);
					time += scrolltime;
				}
			}
			i++;

		}
	};

	private void update() {
		postInvalidate(); // 更新界面
	}

	private Handler handler;

	// 更新浮动窗口位置参数
	private void updatePosition() {
		// View的当前位置
		params.x = (int) (x - startX);
		params.y = (int) (y - startY);
		WindowManager wm = (WindowManager) getContext().getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		wm.updateViewLayout(this, params);
	}

	private void readweibos() {
		String jsonstr = "";
		try {
			jsonstr = getFriendTimeline(mWeibo);
			readlst rj = new readlst();
			weibolst = rj.getjson(jsonstr);
			wiarray = new weiboinfo[weibolst.size()];
			weibolst.toArray(wiarray);
		} catch (MalformedURLException e) {
			this.setText("微博更新失败请检查网络是否正常");
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			this.setText("微博更新失败请检查网络是否正常");
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (WeiboException e) {
			this.setText("微博更新失败请检查网络是否正常");
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	// 获取微博json ------xml?
	private String getFriendTimeline(Weibo weibo) throws MalformedURLException,
			IOException, WeiboException {
		String url = Weibo.SERVER + "statuses/friends_timeline.json";
		switch (contentkind) {
		case 0:
			url = Weibo.SERVER + "statuses/public_timeline.json";// 获取最新的公共微博
			break;
		case 1:
			url = Weibo.SERVER + "statuses/friends_timeline.json";// 获取当前登录用户及其所关注用户的最新微博
			break;
		case 2:
			url = Weibo.SERVER + "statuses/user_timeline.json";// 获取用户发布的微博
			break;
		case 3:
			url = Weibo.SERVER + "statuses/mentions.json";// 获取@当前用户的最新微博
			break;
		case 4:
			url = Weibo.SERVER + "statuses/home_timeline.json";// 获取当前登录用户及其所关注用户的最新微博
			break;
		default:
			url = Weibo.SERVER + "statuses/friends_timeline.json";// 获取最新的公共微博
			break;
		}

		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", Weibo.APP_KEY);
		String rlt = weibo.request(this.getContext(), url, bundle, "GET",
				mWeibo.getAccessToken());
		return rlt;
	}

	// 长按 事件
	private Handler handler1;

	boolean ifmenu = false;
	private Runnable lonclick = new Runnable() {
		public void run() {
			if (ifmenu) {
				if (clicktime >= 1) {
					Intent intent = new Intent(mycontext,
							CreedWidgetActivity.class);
					tlo.setMtv(mtv);
					try {
						intent.putExtra("CREES_MTV", tlo);

						PendingIntent pendingintent = PendingIntent
								.getActivity(mycontext, 0, intent, 1);
						try {
							// TODO: Unregister this handler if
							// PendingIntent.FLAG_ONE_SHOT?
							pendingintent.send();
						} catch (CanceledException e) {
						}
						clicktime = 0;
						ifmenu = false;
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}

				} else {
					clicktime++;
					handler1.postDelayed(lonclick, 800);
				}
			}
		}

	};
	private TranstlateObj tlo = new TranstlateObj();

	/*
	 * private Runnable changesize = new Runnable() { public void run() {
	 * 
	 * Intent intent = new Intent(mycontext, CreedWidgetActivity.class);
	 * tlo.setMtv(mtv); intent.putExtra("CREES_MTV", tlo); PendingIntent
	 * pendingintent = PendingIntent.getActivity(mycontext, 0, intent, 1); try {
	 * pendingintent.send(); } catch (CanceledException e) { // TODO
	 * Auto-generated catch block System.out.println(e); } initsize = true; } };
	 */

	public static void recoversize() {
		params.width = initwidth;
		params.height = initheight;
	}

	public static void minisize() {
		params.width = 20;
		params.height = 20;
	}

	public static void midsize() {
		if (perdic >= 60) {
			perdic = perdic - 60;
			if (perdic <= 20) {
				perdic = 20;
			}
		}
		params.width = perdic;
	}

	public static void hide() {
		params.width = 0;
		params.height = 0;
	}

	public static void mysize(int width) {
		params.width = 0;
		params.height = 0;
	}
}
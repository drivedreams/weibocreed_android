/*
 * Copyright 2011 Sina.
 *
 * Licensed under the Apache License and Weibo License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.open.weibo.com
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weibo.android;

import com.weibo.net.AccessToken;
import com.weibo.net.SettingInfo;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.weiboinfo;
import com.weibo.view.myTextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Sample code for testing creed main.
 * 
 * @author drivedreams (drivedreams@163.com)
 */

public class TestActivity extends Activity {

	private Weibo mWeibo = Weibo.getInstance();
	public static myTextView mtv = null;
	private String accesstoken = null;
	private String accesssecret = null;
	EditText weibotext;
	weiboinfo[] wiarray;
	weiboinfo wi;
	MenuItem[] hoddyMenuItems = new MenuItem[3];
	MenuItem maleMenuItem = null;
	Editor sharedata;
	SharedPreferences settings;
	private int contentkind;
	private int updatetime;// ��ʮ����

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// /��ʹ
		this.setContentView(R.layout.timeline);
		sharedata = getSharedPreferences("Settings", 0).edit();
		settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
		contentkind = settings.getInt("contentkind", 1);
		updatetime = settings.getInt("updatetime", 1800000);
		accesstoken = settings.getString("Token", null);
		accesssecret = settings.getString("Secret", null);
		if (accesstoken != null && accesssecret != null) {
			mWeibo.setAccessToken(new AccessToken(accesstoken, accesssecret));
		} else {

			Uri uri = this.getIntent().getData();// �û���Ȩ���õ�����
			String oauth_verifier = uri.getQueryParameter("oauth_verifier");// ��ȡpin��
			mWeibo.addOauthverifier(oauth_verifier);
			try {
				mWeibo.generateAccessToken(this, null);
				accesstoken = mWeibo.getAccessToken().getToken();
				accesssecret = mWeibo.getAccessToken().getSecret();
				Editor sharedata = getSharedPreferences("Settings", 0).edit();
				sharedata.putString("Token", accesstoken);
				sharedata.putString("Secret", accesssecret);
				sharedata.commit();

			} catch (WeiboException e1) {
				e1.printStackTrace();
			}

		}
       
		// �����¼�
		final Button cvsbt = (Button) this.findViewById(R.id.creed_View_Showbt);
		cvsbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {cvsbt.setText("���������С���");
					stayshowmtv();
					show();
					cvsbt.setText("�� ʾ �� ��");
				} catch (Exception e) {
				}
			}
		});
		Button cvhbt = (Button) this.findViewById(R.id.creed_View_Hidebt);
		cvhbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exitmtv();
			}
		});
		Button aboutbt = (Button) this.findViewById(R.id.aboutbt);
		aboutbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		Button optionbt = (Button) this.findViewById(R.id.optiond_bt);
		optionbt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				opentsettingac();// �����û
			}
		});

		Button off = (Button) this.findViewById(R.id.offbt);
		off.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedata.putString("Token", null);
				sharedata.putString("Secret", null);
				sharedata.commit();
				startActivity(new Intent(TestActivity.this,
						AuthorizeActivity.class));
			}
		});

	}

	private void stayshowmtv() {
		if (mtv != null && mtv.isShown()) {
			WindowManager wm = (WindowManager) getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
			wm.removeView(mtv);
		}
	}

	private void exitmtv() {
		if (mtv != null && mtv.isShown()) {
			WindowManager wm = (WindowManager) getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
			wm.removeView(mtv);
		}
	}

	// //�����û
	private void opentsettingac() {

		Intent intent = new Intent(TestActivity.this, OptionsActivity.class);
		startActivityForResult(intent, 0);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // ��ѡ�˵�ѡ��
	 * SubMenu genderMenu = menu.addSubMenu(MAIN_GROUP, MENU_EXIT, 0, "�˳�");
	 * genderMenu.setIcon(R.drawable.quit); maleMenuItem =
	 * genderMenu.add(GENDER_GROUP, STAY_CREED, 0, "����������ʾ");
	 * maleMenuItem.setChecked(true); genderMenu.add(GENDER_GROUP, TOTAl_EXIT,
	 * 0, "��ȫ�˳�"); // ���ò˵���Ϊ��ѡ�˵������� genderMenu.setGroupCheckable(GENDER_GROUP,
	 * true, true); SubMenu optionsMenu = menu.addSubMenu(MAIN_GROUP, MENU_SET,
	 * 0, "����"); optionsMenu.setIcon(R.drawable.seting); return true; //
	 * �ǵ÷���true��������Ч }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
	 * (item.getItemId()) { case STAY_CREED: item.setChecked(true);
	 * stayshowmtv(); System.out.println("����������ʾ"); break; case TOTAl_EXIT:
	 * item.setChecked(true); ActivityManager am = (ActivityManager)
	 * getSystemService(Context.ACTIVITY_SERVICE);
	 * am.restartPackage(getPackageName()); System.out.println("��ȫ�˳�"); case
	 * MENU_SET: opentsettingac(); System.out.println("����"); break; } return
	 * true; }
	 */

	private void show() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		myTextView.TOOL_BAR_HIGH = frame.top;
		WindowManager wm = (WindowManager) getApplicationContext()
				.getSystemService(WINDOW_SERVICE);

		WindowManager.LayoutParams params = myTextView.params;
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
				| WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
		params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		params.width = WindowManager.LayoutParams.FILL_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.alpha = 1f;
		params.format = PixelFormat.RGBA_8888;
		;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
		params.x = 0;
		params.y = 0;
		SettingInfo settinginfo = new SettingInfo();
		settinginfo.setContentkind(contentkind);
		settinginfo.setUpdatetime(updatetime);
		mtv = new myTextView(TestActivity.this, mWeibo, mtv, settinginfo);
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// ���ڵĿ��
		int screenWidth = dm.widthPixels;
		myTextView.initwidth = screenWidth;
		myTextView.updatetime = settings.getInt("updatetime", 300000);
		myTextView.contentkind = settings.getInt("contentkind", 1);
		wm.addView(mtv, params);

	}
}

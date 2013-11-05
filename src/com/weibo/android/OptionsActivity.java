package com.weibo.android;

import com.weibo.view.myTextView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Sample code for testing creed settings.
 * @author  drivedreams (drivedreams@163.com)
 */

public class OptionsActivity extends Activity {
	private String[] areas = new String[] { "最新的公共微博", "当前用户及其关注用户的最新微博",
			"用户发布的微博", "@当前用户的最新微博", "当前登录用户及其所关注用户的最新微博","取消" };
	private String[] updatetimes = new String[] { "五分钟"+"十分钟"+"十五分钟", "二十分钟", "三十分钟",
			"五十分钟", "一小时", "两小时", "三小时", "四小时", "五小时","取消" };

	private boolean[] areaState = new boolean[] { true, false, false, false,
			false, false, false };
	private RadioOnClick radioOnClick = new RadioOnClick(1);
	private RadioupdateOnClick radioupdateOnClick = new RadioupdateOnClick(1);
	private ListView areaCheckListView;
	private ListView areaRadioListView;
	private Button view_content;
	private Button update_time;
	/*SharedPreferences settings = getSharedPreferences("Settings",
			Activity.MODE_PRIVATE);*/
	public int contentkind;
	public int updatetime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.options);

		// 获取信条对象
		/*Intent intent = getIntent();
		TranstlateObj tlo = (TranstlateObj) intent
				.getSerializableExtra("CREES_MTV");
		this.mtv = tlo.getMtv();*/

		// 按钮 单击事件
		view_content = (Button) findViewById(R.id.view_content); // 滚动条显示内容单选按钮设置
		view_content.setOnClickListener(new VcRadioClickListener());
		update_time = (Button) findViewById(R.id.updatetime);// 更新频率设置
		update_time.setOnClickListener(new UpDRadioClickListener1());
	}

	/**
	 * 菜单弹出窗口
	 * 
	 * @author xmz
	 * 
	 */
	class AlertClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(OptionsActivity.this).setTitle("选择区域")
					.setItems(areas, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

							Toast.makeText(OptionsActivity.this,
									"您已经选择了: " + which + ":" + areas[which],
									Toast.LENGTH_LONG).show();
							dialog.dismiss();
						}
					}).show();
		}
	}

	/**
	 * 多选框弹出菜单窗口
	 * 
	 * @author xmz
	 * 
	 */
	class CheckBoxClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			AlertDialog ad = new AlertDialog.Builder(OptionsActivity.this)
					.setTitle("选择区域")
					.setMultiChoiceItems(areas, areaState,
							new DialogInterface.OnMultiChoiceClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton, boolean isChecked) {
									// 点击某个区域
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String s = "您选择了:";
									for (int i = 0; i < areas.length; i++) {
										if (areaCheckListView
												.getCheckedItemPositions().get(
														i)) {
											s += i
													+ ":"
													+ areaCheckListView
															.getAdapter()
															.getItem(i) + "  ";
										} else {
											areaCheckListView
													.getCheckedItemPositions()
													.get(i, false);
										}
									}
									if (areaCheckListView
											.getCheckedItemPositions().size() > 0) {
										Toast.makeText(OptionsActivity.this, s,
												Toast.LENGTH_LONG).show();
									} else {
										// 没有选择
									}
									dialog.dismiss();
								}
							}).setNegativeButton("取消", null).create();
			areaCheckListView = ad.getListView();
			ad.show();
		}
	}

	/**
	 * 单选弹出菜单窗口
	 * 
	 * @author xmz
	 * 
	 */
	class VcRadioClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			SharedPreferences settings = getSharedPreferences("Settings",
					Activity.MODE_PRIVATE);
			settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
			contentkind = settings.getInt("contentkind", 2);
			
			AlertDialog ad = new AlertDialog.Builder(OptionsActivity.this)
					.setTitle("滚动显示内容设置")
					.setSingleChoiceItems(areas, radioOnClick.getIndex(),
							radioOnClick).create();
			areaRadioListView = ad.getListView();
			areaRadioListView.setSelection(contentkind);
			ad.show();
		}
	}

	class UpDRadioClickListener1 implements OnClickListener {
		@Override
		public void onClick(View v) {
			SharedPreferences settings = getSharedPreferences("Settings",
					Activity.MODE_PRIVATE);
			settings = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
			updatetime=settings.getInt("updatetime", 1800000);
			int selectindex=2;
			switch(updatetime){
			case 300000:selectindex=0;break;//5
			case 600000:selectindex=1;break;//10
			case 900000:selectindex=2;break;//15
			case 1200000:selectindex=3;break;//20
			case 1800000:selectindex=4;break;//30
			case 3000000:selectindex=5;break;//50
			case 3600000:selectindex=6;break;//1
			case 7200000:selectindex=7;break;//2
			case 10800000:selectindex=8;break;//3
			case 14400000:selectindex=9;break;//4
			case 18000000:selectindex=10;break;//5
			default:selectindex=4;
			}
			AlertDialog ad = new AlertDialog.Builder(OptionsActivity.this)
					.setTitle("微博更新频率设置")
					.setSingleChoiceItems(updatetimes, radioupdateOnClick.getIndex(),
							radioupdateOnClick).create();
			areaRadioListView = ad.getListView();
			areaRadioListView.setSelection(selectindex);
			ad.show();
		}
	}

	/**
	 * 点击单选框事件
	 * 
	 * @author xmz
	 * 
	 */
	class RadioOnClick implements DialogInterface.OnClickListener {
		private int index;
		private String[] areas = new String[] { "最新的公共微博", " 当前用户及其关注用户的最新微博",
				"用户发布的微博", "@当前用户的最新微博", "当前登录用户及其所关注用户的最新微博" };
		public RadioOnClick(int index) {
			this.index = index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void onClick(DialogInterface dialog, int whichButton) {
			try{
				
			
			setIndex(whichButton);
			Editor sharedata = getSharedPreferences("Settings", 0).edit();
     	    sharedata.putInt("contentkind",whichButton);
 		    sharedata.commit();
 		    myTextView.contentkind=whichButton;
			Toast.makeText(OptionsActivity.this,
					"您已经选择了:" + areas[index], Toast.LENGTH_LONG)
					.show();
			dialog.dismiss();
			}
			catch(Exception e){
				
			}
		}
	}

	class RadioupdateOnClick implements DialogInterface.OnClickListener {
		private int index;
		public RadioupdateOnClick(int index) {
			this.index = index;
			
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
		
		public void onClick(DialogInterface dialog, int whichButton) {
			try{
			setIndex(whichButton);
			int updatetime=300000;
			switch(whichButton){
			    case 0:updatetime= 300000;
			    case 1:updatetime= 600000;
				case 2:updatetime= 900000;
				case 3:updatetime= 1200000;
				case 4:updatetime= 1800000;
				case 5:updatetime= 3000000;
				case 6:updatetime= 3600000;
				case 7:updatetime= 7200000;
				case 8:updatetime= 10800000;
				case 9:updatetime= 14400000;
				case 10:updatetime= 18000000;
				default: updatetime=1800000;
			}
			Editor sharedata = getSharedPreferences("Settings", 0).edit();
	     	    sharedata.putInt("updatetime",updatetime);
	 		    sharedata.commit();
	 		myTextView.updatetime=updatetime;
			Toast.makeText(OptionsActivity.this,
					"您已经选择了： " + updatetimes[index], Toast.LENGTH_LONG)
					.show();
			dialog.dismiss();
		}
		catch(Exception e){
			
		}
		}
	}
}
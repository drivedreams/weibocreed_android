package com.weibo.android;


import com.weibo.view.myTextView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
/**
 * Sample code for testing creed appear change.
 * @author  drivedreams (drivedreams@163.com)
 */
public class CreedWidgetActivity extends Activity {
	private String[] areas = new String[] { "缩小信条","最小化信条", "最大化信条", "隐藏信条", "信条设置","取消"};
	//private ListView areaRadioListView;
	private RadioOnClick radioOnClick = new RadioOnClick(1);
    private myTextView mtv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.creeswidget);
		Intent intent=getIntent();
		TranstlateObj tlo=(TranstlateObj)intent.getSerializableExtra("CREES_MTV");
		this.mtv=tlo.getMtv();
		/*
		 * Intent intent=getIntent(); String datapath=intent.getDataString();
		 * System.out.println(datapath);
		 */
		AlertDialog ad = new AlertDialog.Builder(CreedWidgetActivity.this)
				.setTitle("哈哈你要干什么？")
				.setSingleChoiceItems(areas, 1, radioOnClick).create();
		//areaRadioListView = ad.getListView();
		ad.show();
		
	}
	/**
	 * 点击单选框事件
	 * 
	 * @author xmz
	 * 
	 */
	class RadioOnClick implements DialogInterface.OnClickListener {
		private int index;

		private RadiobackindOnClick radiobackindOnClick = new RadiobackindOnClick(1);
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
			setIndex(whichButton);
			//"打开微博","最小化信条", "还原信条", "隐藏信条", "信条设置"
			switch(index){
			case 0: myTextView.midsize();         
			break;
			case 1: myTextView.minisize();         
				break;
			case 2: myTextView.recoversize();
				break;
			case 3:	myTextView.hide();
				break;
			case 4:opentsettingac();
				break;
			case 5:selectbackind();
				break;
			default:break;
			}
			Toast.makeText(CreedWidgetActivity.this,
					"选择了： " + areas[index], Toast.LENGTH_LONG)
					.show();
			dialog.dismiss();
			finish();
		}
		////打开设置活动
		private void opentsettingac(){
			TranstlateObj tlo=new TranstlateObj();
			tlo.setMtv(mtv);
			Intent intent=new Intent(CreedWidgetActivity.this,
					OptionsActivity.class);
			intent.putExtra("CREES_MTV", tlo);
			startActivityForResult(intent, 0);
		}
		private void selectbackind(){
			AlertDialog ad = new AlertDialog.Builder(CreedWidgetActivity.this)
			.setTitle("您喜欢那种主题")
			.setSingleChoiceItems(areas, 1, radiobackindOnClick).create();
			//areaRadioListView = ad.getListView();
			ad.show();
		}
		class RadiobackindOnClick implements DialogInterface.OnClickListener {
			private int index;

			public RadiobackindOnClick(int index) {
				this.index = index;
			}

			public void setIndex(int index) {
				this.index = index;
			}

			public int getIndex() {
				return index;
			}

			public void onClick(DialogInterface dialog, int whichButton) {
				setIndex(whichButton);
				//"打开微博","最小化信条", "还原信条", "隐藏信条", "信条设置"
				switch(index){
				case 0: myTextView.midsize();         
				break;
				case 1: myTextView.minisize();         
					break;
				case 2: myTextView.recoversize();
					break;
				case 3:	myTextView.hide();
					break;
				case 4:/*opentsettingac();*/
					break;
				case 5:
					break;
				default:break;
				}
				Toast.makeText(CreedWidgetActivity.this,
						"选择了： " + areas[index], Toast.LENGTH_LONG)
						.show();
				dialog.dismiss();
				finish();
			}
		}
	}
		
}

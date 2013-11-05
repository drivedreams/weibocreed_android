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




import com.weibo.net.RequestToken;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Sample code for testing creed login.
 * @author  drivedreams (drivedreams@163.com)
 */


public class AuthorizeActivity extends Activity {
    /** Called when the activity is first created. */
	private Button mLogin;
/*	private TextView mToken;
	private EditText textbox;*/
	
	private static final String URL_ACTIVITY_CALLBACK = "weiboandroidsdk://TimeLineActivity";
	//private static final String FROM = "xweibo";
	
	private static final String CONSUMER_KEY = "3109573820";
	private static final String CONSUMER_SECRET = "72574e9c7f0a9a901f86cb5631e0dcbb";
	private boolean ifneedlogin=false; 
	 public static final int MENU_EXITAPPLICATION = Menu.FIRST; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences settings = getSharedPreferences("Settings",
                Activity.MODE_PRIVATE);
        final TextView alertmsg=(TextView)this.findViewById(R.id.alertmsg);
       if(settings.getString("Token", null)==null||settings.getString("Secret",null)==null){
    	   ifneedlogin=false;
       }
       else{
    	   ifneedlogin=true;
       }
        mLogin = (Button)this.findViewById(R.id.btnLogin);
        mLogin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == mLogin){
					
					Weibo weibo = Weibo.getInstance();
					weibo.setupConsumerConfig(CONSUMER_KEY, CONSUMER_SECRET);

					if(!ifneedlogin){
					try {
						/*AccessToken at = weibo.getXauthAccessToken(TextActivity.this, Weibo.APP_KEY, Weibo.APP_SECRET, 
								"", "");
						mToken.setText(at.getToken());*/
						/*alertmsg.setText("");
						RequestToken requestToken = weibo.getRequestToken(AuthorizeActivity.this, Weibo.APP_KEY, 
								Weibo.APP_SECRET, AuthorizeActivity.URL_ACTIVITY_CALLBACK);
						
						Uri uri = Uri.parse(Weibo.URL_AUTHENTICATION + "?display=wap2.0&oauth_token=" + 
								requestToken.getToken() + "&from=" + AuthorizeActivity.FROM);
						try{
							ComponentName name = new ComponentName("com.android.browser", "com.android.browser.BrowserActivity");
							Intent intent=new Intent(Intent.ACTION_VIEW, uri);
							intent.setComponent(name);
						    startActivity(intent);
						}
						catch(Exception e){
							alertmsg.setTextColor(Color.RED);
							alertmsg.setText("未能开始授权请确认你已经链接网络");
						}*/
						alertmsg.setText("授权页加载中……");
						RequestToken oauth_token = weibo.getRequestToken(AuthorizeActivity.this, Weibo.APP_KEY, 
								Weibo.APP_SECRET, AuthorizeActivity.URL_ACTIVITY_CALLBACK);
						//Url类似：http://api.t.sina.com.cn/oauth/authenticate?oauth_token=72574e9c7f0a9a901f86cb5631e0dcbb&from=xweibo
						String Url="http://api.t.sina.com.cn/oauth/authenticate?oauth_token="+oauth_token.getToken()+"&from=xweibo";
						Intent intent = new Intent(AuthorizeActivity.this, WebViewActivity.class);
						Bundle b=new Bundle();
						b.putString("url", Url);
						intent.putExtras(b);
						startActivity(intent);
						alertmsg.setText("");
					}catch (WeiboException e){
						alertmsg.setText("未能开始授权请确认你已经链接网络");
					}}
					 else{
					 startActivity(new Intent(AuthorizeActivity.this, TestActivity.class));
				 }
				}
			}
        });
    }
    public void onResume(){
    	super.onResume();
    }
}
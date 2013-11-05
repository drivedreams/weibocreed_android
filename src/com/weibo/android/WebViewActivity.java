package com.weibo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class WebViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.oauth_webview_layout);
		int   screenDensity   = getResources().getDisplayMetrics(). densityDpi ;
		WebSettings.ZoomDensity   zoomDensity   = WebSettings.ZoomDensity. MEDIUM ;
		switch (screenDensity){
		case   DisplayMetrics.DENSITY_LOW :
		    zoomDensity = WebSettings.ZoomDensity.CLOSE;
		      break ;
		case   DisplayMetrics.DENSITY_MEDIUM :
		    zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		      break ;
		case   DisplayMetrics.DENSITY_HIGH :
		    zoomDensity = WebSettings.ZoomDensity.FAR;
		      break ;
		}
		
		WebView wv = (WebView) findViewById(R.id.web);
		wv.setWebChromeClient(new WebChromeClient() {
              public void onProgressChanged(WebView view, int progress) {
                //Activity��Webview���ݼ��س̶Ⱦ����������Ľ��ȴ�С
               //�����ص�100%��ʱ�� �������Զ���ʧ
            	  WebViewActivity.this.setProgress(progress * 100);
       }
    });  
		wv.getSettings().setDefaultZoom(zoomDensity) ;
		Intent i = this.getIntent();
		if (!i.equals(null)) {
			Bundle b = i.getExtras();
			if (b != null) {
				if (b.containsKey("url")) {
					String url = b.getString("url");

					// ���к���Ҫһ��Ҫ�У���Ȼ��ҳ����֤��ť����Ч
					wv.getSettings().setJavaScriptEnabled(true);
					wv.getSettings().setSupportZoom(true);
					// wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
					wv.getSettings().setBuiltInZoomControls(true);
					wv.loadUrl(url);
				}
			}
		}
	    	
		//System.out.println("ssssssssssssss3");
		//wv.addJavascriptInterface(new JavaScriptInterface(), "Methods");
		/*System.out.println("ssssssssssssss4");
		WebViewClient wvc = new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				System.out.println("ssssssssssssss5");
				view.loadUrl("javascript:window.Methods.getHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
				System.out.println("ssssssssssssss6");
				super.onPageFinished(view, url);
			}
		};
		wv.setWebViewClient(wvc);*/
	}
}

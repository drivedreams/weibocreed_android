package com.weibo.android;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class JavaScriptInterface {
	public void getHTML(String html) {
		String pin = getPin(html);
		// ����ͻ�ȡ����������Ҫ��pin��
		// ���pin�����oauth_verifierֵ,������һ����ȡAccess Token��Access Secret��
		Log.e("pin", pin);
	}

	public String getPin(String html) {
		String ret = "";
		String regEx = "[0-9]{6}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		boolean result = m.find();
		if (result) {
			ret = m.group(0);
		}
		return ret;
	}
}

package com.weibo.android;

import java.io.Serializable;

import com.weibo.view.myTextView;

public class TranstlateObj implements Serializable{
	/**
	 * 
	 * @author  drivedreams (drivedreams@163.com)
	 */
	private static final long serialVersionUID = -5599292650719953198L;
	private myTextView mtv;
private TestActivity ta;
	public myTextView getMtv() {
		return mtv;
	}

	public TestActivity getTa() {
		return ta;
	}

	public void setTa(TestActivity ta) {
		this.ta = ta;
	}

	public void setMtv(myTextView mtv) {
		this.mtv = mtv;
	}
	
}

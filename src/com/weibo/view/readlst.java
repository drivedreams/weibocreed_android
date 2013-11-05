package com.weibo.view;

import java.util.ArrayList;
import java.util.List;


import com.weibo.net.Status;
import com.weibo.net.WeiboException;
import com.weibo.net.weiboinfo;
/**
 * Sample code for testing weibo jsonread.
 * @author  drivedreams (drivedreams@163.com)
 */
public class readlst {
public List<weiboinfo> getjson(String jsonstr){
	List<Status> statusls = null;
	List<weiboinfo> wifls=new ArrayList<weiboinfo>();
	try {
		statusls=Status.constructStatuses(jsonstr);
	} catch (WeiboException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	for(Status status:statusls){
		weiboinfo wi=new weiboinfo();
		wi.setContent(status.getText());
		wi.setWeiboId(status.getId());
		if(status.getRetweetDetails()!=null){
			wi.setRetweetedStatuscontent(status.getRetweetDetails().getRetweetingtext());
			wi.setRetweetedStatuscontentId(status.getRetweetDetails().getRetweetId());
		}
		wi.setuId(status.getUser().getId());
		wifls.add(wi);
	}
	return wifls;
}
}

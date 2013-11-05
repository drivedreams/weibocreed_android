package com.weibo.net;

import org.json.JSONException;
import org.json.JSONObject;



public class RetweetDetails {
	private long retweetId;
    private String retweetedAt;
    private User retweetingUser;
    private String retweetingtext;
	 public long getRetweetId() {
		return retweetId;
	}
	public void setRetweetId(long retweetId) {
		this.retweetId = retweetId;
	}
	public String getRetweetedAt() {
		return retweetedAt;
	}
	public void setRetweetedAt(String retweetedAt) {
		this.retweetedAt = retweetedAt;
	}
	public User getRetweetingUser() {
		return retweetingUser;
	}
	public void setRetweetingUser(User retweetingUser) {
		this.retweetingUser = retweetingUser;
	}
	 RetweetDetails(JSONObject json) throws WeiboException {
	        super();
	        init(json);
	    }
	    
	    

	    private void init(JSONObject json) throws WeiboException{
	    	try {
	    		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//…Ë÷√∏Ò Ω\
	    		//retweetedAt= format.format(json.getString("created_at"));
	    		retweetId = json.getLong("id");
	    		retweetingUser=new User(json.getJSONObject("user"));
	    		retweetingtext=json.getString("text");
	        } catch (JSONException jsone) {
	            throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
	        }
		}
		public void setRetweetingtext(String retweetingtext) {
			this.retweetingtext = retweetingtext;
		}
		public String getRetweetingtext() {
			return retweetingtext;
		}

}

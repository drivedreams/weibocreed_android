package com.weibo.net;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Status {
	   private Date createdAt;
	   private long id;
	    private String text;
	    private String source;
	    private boolean isTruncated;
	    private long inReplyToStatusId;
	    private int inReplyToUserId;
	    private boolean isFavorited;
	    private String inReplyToScreenName;
	    private double latitude = -1;
	    private double longitude = -1;
	    private String thumbnail_pic;
	    private String bmiddle_pic;
	    private String original_pic;
	    private RetweetDetails retweetDetails=null;
		private User user;
	    public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isTruncated() {
		return isTruncated;
	}
	public void setTruncated(boolean isTruncated) {
		this.isTruncated = isTruncated;
	}
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}
	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}
	public int getInReplyToUserId() {
		return inReplyToUserId;
	}
	public void setInReplyToUserId(int inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}
	public boolean isFavorited() {
		return isFavorited;
	}
	public void setFavorited(boolean isFavorited) {
		this.isFavorited = isFavorited;
	}
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}
	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getThumbnail_pic() {
		return thumbnail_pic;
	}
	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}
	public String getBmiddle_pic() {
		return bmiddle_pic;
	}
	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}
	public String getOriginal_pic() {
		return original_pic;
	}
	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}
	public RetweetDetails getRetweetDetails() {
		return retweetDetails;
	}
	public void setRetweetDetails(RetweetDetails retweetDetails) {
		this.retweetDetails = retweetDetails;
	}
	 public static List<Status> constructStatuses(String jsonstr) throws WeiboException {
    	 try {
    		 
             JSONArray list =new JSONArray(jsonstr);
             int size = list.length();
             List<Status> statuses = new ArrayList<Status>(size);
             for (int i = 0; i < size; i++) {
                 statuses.add(new Status(list.getJSONObject(i)));
             }
             return statuses;
         } catch (JSONException jsone) {
             throw new WeiboException(jsone);
         }

    }
	public Status(JSONObject json) throws JSONException, WeiboException{
    	id = json.getLong("id");
        text = json.getString("text");
        source = json.getString("source");
        //createdAt = String.format(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");
        isFavorited = getBoolean("favorited", json);
        isTruncated=getBoolean("truncated", json);
       /* inReplyToStatusId = getLong("in_reply_to_status_id", json);
        inReplyToUserId = getInt("in_reply_to_user_id", json);
        inReplyToScreenName=json.getString("in_reply_to_screen_name");
        thumbnail_pic = json.getString("thumbnail_pic");
		bmiddle_pic = json.getString("bmiddle_pic");
		original_pic = json.getString("original_pic");*/
        setUser(new User(json.getJSONObject("user")));
        if(!json.isNull("retweeted_status")){
	    retweetDetails = new RetweetDetails(json.getJSONObject("retweeted_status"));
		}
    }
	protected static int getInt(String key, JSONObject json) throws JSONException  {
        String str = json.getString(key);
        if(null == str || "".equals(str)||"null".equals(str)){
            return -1;
        }
        return Integer.parseInt(str);
    }

    protected static long getLong(String key, JSONObject json) throws JSONException  {
        String str = json.getString(key);
        if(null == str || "".equals(str)||"null".equals(str)){
            return -1;
        }
        return Long.parseLong(str);
    }
    protected static boolean getBoolean(String key, JSONObject json) throws JSONException{
        String str = json.getString(key);
        if(null == str || "".equals(str)||"null".equals(str)){
            return false;
        }
        return Boolean.valueOf(str);
    }
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}

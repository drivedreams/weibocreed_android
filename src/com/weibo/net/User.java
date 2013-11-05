package com.weibo.net;

import org.json.JSONException;
import org.json.JSONObject;


public class User {
	private int id;
    private String name;
    private String screenName;
    private String location;
    private String description;
    private String profileImageUrl;
    private String url;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	User(JSONObject json) throws WeiboException {
        super();
        init(json);
    }
	 private void init(JSONObject json) throws WeiboException {
	    	if(json!=null){
	        try {
	            id = json.getInt("id");
	            name = json.getString("name");
	            screenName = json.getString("screen_name");
	            location = json.getString("location");
	            description = json.getString("description");
	            profileImageUrl = json.getString("profile_image_url");
	            url = json.getString("url");
	           
	        } catch (JSONException jsone) {
	            throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
	        }
	    	}
	    }
}

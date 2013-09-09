package com.staticvillage.timage.utils;

public class CommentData {
	private String appId;
	private int appVersion;
	private float[] orientation;
	
	public CommentData(){
		orientation = new float[]{ 0, 0, 0 };
	}
	
	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public int getAppVersion() {
		return appVersion;
	}
	
	public void setAppVersion(int appVersion) {
		this.appVersion = appVersion;
	}
	
	public float[] getOrientation() {
		return orientation;
	}
	
	public void setOrientation(float[] orientation) {
		if(orientation.length == 3)
			this.orientation = orientation;
	}
}

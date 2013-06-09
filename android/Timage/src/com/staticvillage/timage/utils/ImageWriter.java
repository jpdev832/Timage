package com.staticvillage.timage.utils;

import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;

import android.media.ExifInterface;

/**
 * Exif data writer for Timage compatible images
 * 
 * @author Joel
 */
public class ImageWriter {
	public static final String COMMENT_TAG = "UserComment";
	
	private float[] orientation;
	private float[] location;
	private long timestamp;
	
	public ImageWriter(){
		orientation = new float[]{ 0, 0, 0 };
		location = new float[]{ 0, 0 };
		timestamp = (new Date()).getTime();
	}
	
	/**
	 * Set gyroscope orientation data for image
	 * 
	 * @param orientation
	 * @throws Exception
	 */
	public void setOrientation(float[] orientation) throws Exception{
		if(orientation.length != 3)
			throw new Exception("TODO define exception");
		
		this.orientation = orientation;
	}
	
	/**
	 * Set GPS location for image
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public void setLocation(float latitude, float longitude){
		location[0] = latitude;
		location[1] = longitude;
	}
	
	/**
	 * Set timestamp for image
	 * 
	 * @param timestamp Time/Date image was captured
	 */
	public void setTimestamp(long timestamp){
		this.timestamp = timestamp;
	}
	
	/**
	 * Write attributes to image
	 * 
	 * @param imageFile
	 * @throws IOException
	 */
	public void write(String imageFile) throws IOException{
		ExifInterface exif = new ExifInterface(imageFile);
		exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, String.valueOf(location[0]));
		exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, String.valueOf(location[1]));
		exif.setAttribute(ExifInterface.TAG_DATETIME, String.valueOf(timestamp));
		exif.setAttribute(COMMENT_TAG, orientationToJson("", 0, orientation));
		exif.saveAttributes();
	}
	
	/**
	 * Generate json data for Timage user comment data
	 * 
	 * @param appId	application UID
	 * @param version application version
	 * @param orientation image orientation
	 * @return Timage comment data
	 */
	public static String orientationToJson(String appId, int version, float[] orientation){
		//TODO - define definite json structure
		CommentData comment = new CommentData();
		comment.setAppId(appId);
		comment.setAppVersion(version);
		comment.setOrientation(orientation);
		
		Gson gson = new Gson();
		return gson.toJson(comment);
	}
}

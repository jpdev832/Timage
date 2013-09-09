package com.staticvillage.timage.sensors;

public class CompassData {
	private float azimuth;
	private float roll;
	private float pitch;
	private float heading;
	private float headingAccuracy;
	private float geo_x;
	private float geo_y;
	private float geo_z;
	private float accel_x;
	private float accel_y;
	private float accel_z;
	private float compassDegrees;
	
	public float getAzimuth() {
		return azimuth;
	}
	public void setAzimuth(float azimuth) {
		this.azimuth = azimuth;
	}
	public float getRoll() {
		return roll;
	}
	public void setRoll(float roll) {
		this.roll = roll;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getHeading() {
		return heading;
	}
	public void setHeading(float heading) {
		this.heading = heading;
	}
	public float getHeadingAccuracy() {
		return headingAccuracy;
	}
	public void setHeadingAccuracy(float headingAccuracy) {
		this.headingAccuracy = headingAccuracy;
	}
	public float getGeo_x() {
		return geo_x;
	}
	public void setGeo_x(float geo_x) {
		this.geo_x = geo_x;
	}
	public float getGeo_y() {
		return geo_y;
	}
	public void setGeo_y(float geo_y) {
		this.geo_y = geo_y;
	}
	public float getGeo_z() {
		return geo_z;
	}
	public void setGeo_z(float geo_z) {
		this.geo_z = geo_z;
	}
	public float getAccel_x() {
		return accel_x;
	}
	public void setAccel_x(float accel_x) {
		this.accel_x = accel_x;
	}
	public float getAccel_y() {
		return accel_y;
	}
	public void setAccel_y(float accel_y) {
		this.accel_y = accel_y;
	}
	public float getAccel_z() {
		return accel_z;
	}
	public void setAccel_z(float accel_z) {
		this.accel_z = accel_z;
	}
	public float getCompassDegrees() {
		return compassDegrees;
	}
	public void setCompassDegrees(float compassDegrees) {
		this.compassDegrees = compassDegrees;
	}
}

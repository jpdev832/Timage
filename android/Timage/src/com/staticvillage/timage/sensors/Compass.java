package com.staticvillage.timage.sensors;

import java.util.Date;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Compass implements SensorEventListener {
	public static final int MSG_COMPASS_UPDATE = 7613;
	
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private Handler handler;
	private int delay = 500;
	private long delayTime = 0;
	private double lastPosition = 0;
	private int threshold = 10;
	
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float[] mHeading;
	
	public Compass(Context context, Handler handler){
		sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		this.handler = handler;
	}
	
	public void setDelay(int delay){
		this.delay = delay;
	}
	
	public void setThreshold(int degrees){
		this.threshold = degrees;
	}
	
	public void start(){
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
	}
	
	public void stop(){
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.d("beacon", "Accuracy Changed: "+sensor.getType()+" accuracy: "+accuracy);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			mGravity = lowPass( event.values.clone(), mGravity );
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			mGeomagnetic = lowPass( event.values.clone(), mGeomagnetic );;
		if (mGravity != null && mGeomagnetic != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				
				float azimuth = Float.valueOf(orientation[0]);
				float degrees = (float)Math.toDegrees(azimuth);
				
				if(degrees < 0.0f)
					degrees += 360.0f;
				
				if(new Date().getTime() > delayTime && (Math.abs(lastPosition - degrees)) > threshold)
				{
					delayTime = new Date().getTime() + delay;
					lastPosition = degrees;
					
					CompassData data = new CompassData();
					data.setAccel_x(mGravity[0]);
					data.setAccel_y(mGravity[1]);
					data.setAccel_z(mGravity[2]);
					data.setGeo_x(mGeomagnetic[0]);
					data.setGeo_x(mGeomagnetic[1]);
					data.setGeo_x(mGeomagnetic[2]);
					data.setAzimuth(orientation[0]);
					data.setPitch(orientation[1]);
					data.setRoll(orientation[2]);
					data.setCompassDegrees(degrees);
					
					Message msg = handler.obtainMessage(MSG_COMPASS_UPDATE, data);
					handler.sendMessage(msg);
				}
			}
		}
	}
	
	public static float calculateTrueNorth(float degrees, float lat, float lon, float alt, long milli){
		GeomagneticField geoField = new GeomagneticField(lat, lon, alt, milli);
		
		float ret = degrees + geoField.getDeclination();
		if(ret < 0.0f)
			ret += 360.0f;
		
		return ret;
	}
	
	public static float getOffset(double lat1, double lat2, double lon1, double lon2, float bearing){
		GeomagneticField geoMag = new GeomagneticField((float)lat1, (float)lon1, 0, new Date().getTime());
		bearing += geoMag.getDeclination();
		
	    lat1 = Math.toRadians(lat1);
	    lat2 = Math.toRadians(lat2);
	    lon1 = Math.toRadians(lon1);
	    lon2 = Math.toRadians(lon2);

	    double lonDiff = lon2-lon1;
	    double y = Math.sin(lonDiff) * Math.cos(lat2);
	    double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lonDiff);

	    bearing -= (float)Math.toDegrees((Math.atan2(y, x)) + 360) % 360;
	    if(bearing < 0.0f)
	    	bearing += 360.0f;
	    else if(bearing > 360.0f)
	    	bearing -= 360.0f;
	    
	    return bearing;
	}
	
	/**
	 * time smoothing constant for low-pass filter
	 * 0 <= alpha <= 1 ; a smaller value basically means more smoothing
	 * See: http://en.wikipedia.org/wiki/Low-pass_filter#Discrete-time_realization
	 */
	static final float ALPHA = 0.05f;
	 
	/**
	 * @see http://en.wikipedia.org/wiki/Low-pass_filter#Algorithmic_implementation
	 * @see http://developer.android.com/reference/android/hardware/SensorEvent.html#values
	 */
	protected float[] lowPass( float[] input, float[] output ) {
	    if ( output == null ) return input;
	     
	    for ( int i=0; i<input.length; i++ ) {
	        output[i] = output[i] + ALPHA * (input[i] - output[i]);
	    }
	    return output;
	}
}

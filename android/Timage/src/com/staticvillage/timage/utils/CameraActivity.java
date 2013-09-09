package com.staticvillage.timage.utils;

import com.staticvillage.timage.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
	/**
	 * Get an instance of the camera (Defaults to rear)
	 * @return Camera instance
	 */
	public static Camera getCameraInstance(){
	    Camera camera = null;
	    try {
	        camera = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	    	Log.d("Timage", "Failed to get camera!");
	    }
	    return camera;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_capture);
		
		if(!checkCameraHardware(this))
			finish();
		
		Camera camera = getCameraInstance();
		CameraPreview preview = new CameraPreview(this, camera);
		FrameLayout frame = (FrameLayout)findViewById(R.id.camera_holder);
		frame.addView(preview);
	}

	/**
	 * Check for camera hardware
	 * 
	 * @param context context
	 * @return whether camera is present and accessible
	 */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        return true;
	    } else {
	        return false;
	    }
	}
	
	/**
	 * Capture image/orientation/timestamp data
	 * @return image location. \nReturns NULL if failed
	 */
	public String capture(){
		//capture raw image
		//capture orientation
		//capture date/time
		//store and return location
		return null;
	}
}

package com.staticvillage.timage.utils;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
    private Camera mCamera;
    
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mHolder.getSurface() == null){
			return;	// preview surface does not exist
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
        	// ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d("Timage", "Error starting camera preview: " + e.getMessage());
        }
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("Timage", "Error setting camera preview: " + e.getMessage());
        }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}

package com.ccsg.camera;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.hardware.Camera;

public class CcsgCamera extends Activity {
	
	private static final String TAG = "CcsgCamera";
	
	private Camera CcsgCam = null;
	private CameraPreview mPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_ccsg_camera);
		
		Log.d(TAG,"open camera");
		CcsgCam = Camera.open();
		Log.d(TAG,"open camera end");
		
		mPreview = new CameraPreview(this, CcsgCam);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        
        preview.addView(mPreview);			
	}
	
	public void onDestroy()
	{
		super.onDestroy();
	
		if(CcsgCam != null)
		{
			Log.d(TAG,"release camera");
			CcsgCam.release();
			CcsgCam = null;
			Log.d(TAG,"release camera end");
		}
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ccsg_camera, menu);
		return true;
	}
	
	public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	    private SurfaceHolder mHolder;
	    private Camera mCamera;

	    @SuppressWarnings("deprecation")
		public CameraPreview(Context context, Camera camera) {
	        super(context);
	        mCamera = camera;

	        // Install a SurfaceHolder.Callback so we get notified when the
	        // underlying surface is created and destroyed.
	        mHolder = getHolder();
	        mHolder.addCallback(this);	       
	    }

	    public void surfaceCreated(SurfaceHolder holder) {
	        // The Surface has been created, now tell the camera where to draw the preview.
	    	 try {
	             mCamera.setPreviewDisplay(holder);
	             Log.d(TAG,"StartPreview");
	             mCamera.startPreview();
	             Log.d(TAG,"StartPreview end");
	        } catch (IOException e) {
	            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
	        }     
	    }

	    public void surfaceDestroyed(SurfaceHolder holder) {
	        // empty. Take care of releasing the Camera preview in your activity.
	    }

	    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
	        // If your preview can change or rotate, take care of those events here.
	        // Make sure to stop the preview before resizing or reformatting it.

	        if (mHolder.getSurface() == null){
	          // preview surface does not exist
	          return;
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
	            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
	        }
	    }
	}
}



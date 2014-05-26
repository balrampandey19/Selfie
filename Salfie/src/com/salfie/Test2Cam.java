
package com.salfie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import  android.provider.MediaStore.Files.FileColumns;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
public class Test2Cam extends Activity {
	Camera cameraObject;
	   private ShowCamera showCamera;
	   private ImageView pic;
	   
	   public static Camera isCameraAvailiable(){
	      Camera object = null;
	      try {
	         object = Camera.open(); 
	      }
	      catch (Exception e){
	      }
	      return object; 
	   }

	   private PictureCallback capturedIt = new PictureCallback() {

	   
		@Override
		public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
			// TODO Auto-generated method stub
			File pictureFile = getOutputMediaFile1(FileColumns.MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	           
	        	Toast.makeText(Test2Cam.this,"Error creating media file, check storage permissions",Toast.LENGTH_LONG).show();
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Toast.makeText(Test2Cam.this,"Exception "+e,Toast.LENGTH_LONG).show();
	        } catch (IOException e) {
	        	Toast.makeText(Test2Cam.this,"Exception "+e,Toast.LENGTH_LONG).show();
	        }
		      
		}
		
		
	};
	
	
	
	
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri1(int type){
	      return Uri.fromFile(getOutputMediaFile1(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile1(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	        	
	        	 Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name..
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(type));
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	    	Log.d("MyCameraApp", "Sorry not created");
	        return null;
	    }

	    return mediaFile;
	}
	
	
	
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test2_cam);
		//pic = (ImageView)findViewById(R.id.imageView1);
	      cameraObject = isCameraAvailiable();
	      showCamera = new ShowCamera(this,cameraObject);
	      
	      FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	      preview.addView(showCamera);
	}
	
	public void snapIt(View view){
	      cameraObject.takePicture(null, null, capturedIt);
	      cameraObject.release();
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test2_cam, menu);
		return true;
	}

}

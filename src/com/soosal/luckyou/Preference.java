package com.soosal.luckyou;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Preference {
public static MediaPlayer sound;
 
	SharedPreferences settings;

	static SharedPreferences defaultSettings;

	private static int makeHalfTime;
	 
	public Preference(SharedPreferences set){
		this.settings =  set;
		
	}
	
	public String getValue(String value){
		String x;
		if(value.equals("Luck")){
			     x =  this.settings.getString(value, "0");
		 }else{
				 x =  this.settings.getString(value, "Luck You");
		 }
	return x;
	}
	
	public String getValue2(String value, String def){
		String x;
		x =  this.settings.getString(value, def);
		return x;
	 }
	
	public void setValue(String key, String value){
		SharedPreferences.Editor editor = this.settings.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void playMusic(MediaPlayer sound,Activity a){
		initPrefs(a);
		  if(defaultSettings.getBoolean("pr_sound", true)){
		   Preference.sound = sound;
				sound.start();
				sound.setLooping(false);
		  }
		 
	}
	public static void Vibrate(Activity a,int time){
		  initPrefs(a);
		  if(defaultSettings.getBoolean("pr_vibration", true)){
		    Vibrator v = (Vibrator) a.getSystemService(Context.VIBRATOR_SERVICE);
		    v.vibrate(time);
		  }
		 
	}
	public static String getDefaultDataString(Activity a,String name ){
		initPrefs(a);
		return defaultSettings.getString(name, "Luck You");
	}
	public static void setDefaultDataString(Activity a,String name,String value ){
		initPrefs(a);
		defaultSettings.edit().putString(name, value).commit();//.getString(name, "Luck You");
	}
	private static void initPrefs(Activity a){
		  defaultSettings = PreferenceManager.getDefaultSharedPreferences(a.getBaseContext()); 
			
	}
	public static  void stopMusic(){
		if(sound.isPlaying()){
			sound.stop();
		}
	}
	
	public static void doLate(final int late,final Activity a){
		makeHalfTime = (int) Math.round(late / 2);
		
		final Timer t = new Timer();
		t.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				a.runOnUiThread(new Runnable() {
					 @Override
					 public void run(){
						  if(makeHalfTime >= late){
							  t.cancel();
						  }
						  makeHalfTime += makeHalfTime;
							
					 }
				 });
			}
			
		}, 0, makeHalfTime);
	}
	
	@SuppressWarnings("null")
	public static void progress(int state,Context c,String title,String msg){
		ProgressDialog p = null;
		if(state == 0){
			p.hide();
		}else{
			p = new ProgressDialog(c);
			p.setTitle(title);
			p.setMessage(msg);
			p.show();
		}
	}

	public static void alert(Context c,String title, String msg) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(c);
		alert.setTitle(title);
		alert.setMessage(msg);
		alert.setCancelable(true);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		alert.show();
	
	}
	
	public static void toast(Context c,String message){
		Toast.makeText(c, message, Toast.LENGTH_LONG).show();
		}
}

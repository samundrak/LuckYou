package com.soosal.luckyou;

import java.text.SimpleDateFormat;
 
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
 

public class Splash extends Activity{
SharedPreferences set,set2;
Preference settings,settings2;
MediaPlayer song;
@Override
	protected void onCreate(Bundle savedInstanceState) {
	set = getSharedPreferences("Data",0);
	settings =  new Preference(set);
	set2 = getSharedPreferences("Asked",0);
	settings2 =  new Preference(set2);
	//set3 = PreferenceManager.getDefaultSharedPreferences(this);
	song = MediaPlayer.create(Splash.this, R.raw.splash);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		if(settings.getValue("Date").trim().equals(getToday().trim())){
			 if(Integer.parseInt(settings.getValue("Asked")) == 9 ){
				 startActivity(new Intent(Splash.this, Result.class));
			  }else if(Integer.parseInt(settings.getValue("Asked")) >= 2){
				 startActivity(new Intent(Splash.this, Question.class));
			 }
		    } 
		else{
		Preference.playMusic(song, Splash.this); 
		Thread t = new Thread(){
			public void run(){
				try{
					sleep(5000);
				}
				catch(InterruptedException e){}
				finally{
					
					validation();
				}
			}
		};
		t.start();
		}
	}

	 
private boolean validation(){
	 Intent nextScene = null;
	 if(settings.getValue("UserName").equals("Luck You")){
	   nextScene =  new Intent(Splash.this, Input.class);
	   
	   }
	   else{
	 if(settings.getValue("Date").trim().equals(getToday())){
		 if(Integer.parseInt(settings.getValue("Asked")) >= 9 ){
			 nextScene = new Intent(Splash.this, Result.class);
		 }else{
			 nextScene = new Intent(Splash.this, Question.class);
		 }
	    	
	    }else{
	    	settings.setValue("Date", getToday());
	    	settings.setValue("Asked", "0");
	    	settings.setValue("CorrectAnswer", "0");
	    	settings.setValue("Luck", "0");
	    	settings2.setValue("Asked", "empty");
	    	settings.setValue("ResultAnimation", "0");
	    	nextScene = new Intent(Splash.this, Question.class);
	    }
	 }
	 startActivity(nextScene);
	 return true;
 }
	private String getToday(){
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy",Locale.US);
		String now = df.format(new Date());
		return now;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		song.stop();
		 finish();
	}
	
 
}

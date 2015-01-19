package com.soosal.luckyou;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class Result extends Activity  {

String PREFERENCES_NAME = "Data";

SharedPreferences set;

Preference settings;

int luckValue;

int timerY = 0;

private Timer time;

Typeface font,digitalFont;

TextView r_luckmeter,setLucktitle,setLuck,setUserName,setShowLuck,setCorrectAnswer,details,i_name,i_luck,i_ca;

private MediaPlayer tickSound,shortWhistle;

private static boolean isTimerRunning = false;

private boolean textAnimationOn = false;

// false = not running
// true = yes running

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState); 
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.result);
		
		set  = getSharedPreferences(PREFERENCES_NAME,0);
		
		settings =  new Preference(set);
		
		font = Typeface.createFromAsset(getAssets(), "LOBSTER.OTF");
		
		digitalFont = Typeface.createFromAsset(getAssets(), "digital.ttf");
		
		initVariable();
		
		setData();
		
		if(settings.getValue("ResultAnimation").equals("0")){
		
		Preference.toast(this, "Calculating your luck...");
		
		Preference.playMusic(tickSound, this);
		
		tickSound.setLooping(true);
		
		setInterval();
		
		settings.setValue("ResultAnimation", "1");
		
		}else{
		
		setTextColor(luckValue,r_luckmeter);  
		
		r_luckmeter.setText(Integer.toString(luckValue) + "%");
		
		setShowLuck.setText(Integer.toString(luckValue)  +"%");
		
		}
		
	}
	
  private void initVariable(){
	
	  luckValue = (int) Math.round(Float.parseFloat(settings.getValue("Luck")));
	  
	  this.r_luckmeter = (TextView) findViewById(R.id.r_luckmeter);
	  
	  this.r_luckmeter.setTypeface(digitalFont);
	  
	  this.setLucktitle = (TextView) findViewById(R.id.lucktitle);
	 	
	  this.setUserName = (TextView) findViewById(R.id.userName);
		
	  this.setShowLuck =(TextView) findViewById(R.id.showluck);
		
	  this.setCorrectAnswer = (TextView) findViewById(R.id.correctAnswer);
		
	  this.details = (TextView) findViewById(R.id.details);
		
	  this.i_name = (TextView) findViewById(R.id.i_name);
		
	  this.i_luck = (TextView) findViewById(R.id.i_luck);
		
	  this.i_ca = (TextView) findViewById(R.id.i_ca);
		
	  tickSound = MediaPlayer.create(this, R.raw.tick);
		
	 } 
	 
	 private void setData(){
		
		 setLucktitle.setText("Hello, "+ settings.getValue("UserName").toString());
	 	 
		 setLucktitle.setTypeface(font);
 		 
		 setUserName.setText(settings.getValue("UserName").toString());
	 	 
		 setUserName.setTypeface(font);
	 	 
		 setShowLuck.setTypeface(digitalFont);
	 	
		 setShowLuck.setText("0");
	 	 
		 setCorrectAnswer.setText(settings.getValue("CorrectAnswer").toString());
		 
		 setCorrectAnswer.setTypeface(digitalFont);
		 
		 details.setTypeface(font);
		 
		 i_name.setTypeface(font);
		 
		 i_ca.setTypeface(font);
		 
		 i_luck.setTypeface(font);
	 }
	 
	 @SuppressWarnings("unused")
	private void changeLuckBg(){
		 
		 try{
		 
			 int x = Integer.parseInt(settings.getValue("Luck"));
		 
			 if(x <= 30 && x >= 0){
			 
				 setLuck.setBackgroundResource(R.drawable.bg_badluck);
		 
			 }
		 
			 else if (x >= 30 && x <= 80){
			 
				 setLuck.setBackgroundResource(R.drawable.bg_luck);
		 
			 }else{
			 
				 setLuck.setBackgroundResource(R.drawable.bg_goodluck);
		 
			 }
	 
		 }
		 
		 catch(Exception ex){
		
			 //Toast.makeText(getBaseContext(), ex.getStackTrace().toString(), Toast.LENGTH_LONG).show(); 
		  
		 }
		
	 }
  
	 
@Override
public boolean onCreateOptionsMenu(Menu menu){
     
	MenuInflater mf = getMenuInflater();
    
	mf.inflate(R.menu.r_menu, menu);
    
	return true;
}


@Override 
public boolean onOptionsItemSelected(MenuItem item){

	switch(item.getItemId()){
	
	case R.id.pr_reset:
	
		Preference.progress(1, this, "Resetting data", "Resetting all data ..");
		
		settings.setValue("Asked", "0");
		
		settings.setValue("Date",  "0");
		
		settings.setValue("UserName", "Luck You");
		
		settings.setValue("CorrectAnswer", "0");
		
		settings.setValue("Luck", "0");
		
		settings.setValue("ResultAnimation", "0");
		
		Preference.doLate(3000,this);
		
		new Preference(getSharedPreferences("Asked",0)).setValue("Asked", "empty");
		
		startActivity(new Intent(Result.this,Splash.class));
		
		finish();
		
		//Preference.progress(0, this, "Resetting data", "Resetting all data");
		
		return true;
	 
	case R.id.r_settings:
	
		startActivity(new Intent(Result.this,prefs.class));
		
		return true;
	 
	case R.id.q_refresh:
	
		startActivity(new Intent(Result.this,Result.class));
		
		if(isTimerRunning)
		
		{time.cancel();
	 	
		tickSound.stop();
	 	
		shortWhistle.stop();}
		
		finish();
			
		 return true;
	 	
	default:
	
		return false;
	
	}
}

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	
	super.onPause();
	
	
	
	if(isTimerRunning)
	
	{time.cancel();
	
	tickSound.stop();
	
	shortWhistle.stop();}

}

 
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub

	super.onBackPressed();
	
	 if(isTimerRunning)
	
	 {finish();}
	 
}

@Override
 protected void onStop(){
	 
	super.onStop();
	 
	 if(isTimerRunning)
	
	 {time.cancel();
	 
	 tickSound.stop();
	 
	 shortWhistle.stop();}

}

@Override
protected void onResume(){

	super.onResume();
    
	if(settings.getValue("UserName").equals(Preference.getDefaultDataString(this, "pr_name"))){
    		//do nothing
    
	}else{
    
		Preference.progress(1, this, "Updating", "Updating new name...");
    	
		Preference.doLate(3000,this);
    	
		setLucktitle.setText("Hello, "+ Preference.getDefaultDataString(this, "pr_name"));
    	
		setLucktitle.setTypeface(font);
     	
	}
 }

private void setInterval(){
	
   time = new Timer();
  
   time.scheduleAtFixedRate(new TimerTask(){
	 
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		runOnUiThread(new Runnable() {
          
			@Override
        
			public void run() {
            	 
                 if( timerY >= luckValue){
            
                	 shortWhistle = MediaPlayer.create(Result.this, R.raw.shortwhistle);
                	 
                	 Preference.playMusic(shortWhistle, Result.this);
                	 
                	 Preference.Vibrate(Result.this, 500);
                	 
                	 isTimerRunning  = false;
                	 
                	 r_luckmeter.setTextSize(100);
                	 
                	 tickSound.stop();
            		 
                	 shortWhistle.stop();
                	 
                	 r_luckmeter.setText(luckValue + "%");
                	 
                	 setShowLuck.setText(luckValue+"%");
                	 
                	 time.cancel();
            		
                 }
                 
                 isTimerRunning  = true;
                 
                 if(textAnimationOn){
                
                	 textAnimationOn = false;
                	 
                	 r_luckmeter.setTextSize(100);
                 
                 }else{
                
                	 textAnimationOn = true;
                	 
                	 r_luckmeter.setTextSize(130);
                 
                 }
                 
                 r_luckmeter.setText(timerY + "%");
                 
                 setTextColor(timerY,r_luckmeter);
                 
                 timerY++;
            
			}

		});
	
	}
	   
   
   }, 0, 200);
 
}
private void setTextColor(int timerY,TextView r_luckmeter){

	if(timerY <= 20)
    
	{
    
		r_luckmeter.setTextColor(Color.RED);
     
	}
    
	else if(timerY <= 30)
    
	{
    
		r_luckmeter.setTextColor(Color.rgb(180, 0, 18));
     
	}
    
	else if(timerY <= 40)
    
	{
    
		r_luckmeter.setTextColor(Color.rgb(255, 0, 204));
     
	}
    
	else if(timerY <= 50)
    
	{
    
		r_luckmeter.setTextColor(Color.GREEN);
     
	}
    
	else if(timerY <= 60)
    
	{
    
		r_luckmeter.setTextColor(Color.rgb(216, 255, 0));
     
	}
    
	
	else if(timerY <= 70)
    
	{
    
		r_luckmeter.setTextColor(Color.rgb(238, 255, 122));
     
	}
    
	else if(timerY <= 80)
    
	{
    
		r_luckmeter.setTextColor(Color.rgb(31, 124, 255));
     
	}
    
	else if(timerY <= 100)
    
	{
    
		r_luckmeter.setTextColor(Color.rgb(114, 51, 153));
     
	}
 
}
 

}

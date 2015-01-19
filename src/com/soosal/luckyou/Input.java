package com.soosal.luckyou;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
 
import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
 
import android.widget.Button;
 
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Input extends Activity {
EditText input;
Button submit;
Preference settings;
SharedPreferences set;
Typeface font;
private TextView showInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.input);
		font = Typeface.createFromAsset(getAssets(), "LOBSTER.OTF");
	 
	 	set = getSharedPreferences("Data",0);
	    settings = new Preference(set);  
	    initVar();
	    submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
			 String x =  input.getText().toString().trim().replace(" ", "-");
					if(x.length() <= 4){
						     Toast.makeText(getBaseContext(), "Make sure name is not empty or name is above 4 character", Toast.LENGTH_SHORT).show();
					   }else{
						     
						   Preference.setDefaultDataString(Input.this,"pr_name", x);
						   settings.setValue("UserName", x);
						   settings.setValue("Luck", "0");
						   settings.setValue("ResultAnimation", "0");
						   settings.setValue("CorrectAnswer","0");
						   settings.setValue("Asked", "0");
						   settings.setValue("Date", getToday());
						   new Preference(getSharedPreferences("Asked",0)).getValue2("Asked", "empty");
						   Intent nextScene = new Intent(Input.this,Question.class);
						   startActivity(nextScene);
					  
				  }
				}
				catch(Exception ex){
					
				}
			}
		});
	}

	private  void initVar(){
		showInfo = (TextView) findViewById(R.id.showInfo);
		showInfo.setTypeface(font);
		input = (EditText) findViewById(R.id.getUsername);
		submit =(Button) findViewById(R.id.submit);
		submit.setTypeface(font);
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
		 finish();
	}
}

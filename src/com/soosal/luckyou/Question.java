package com.soosal.luckyou;

import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Question extends Activity implements OnClickListener{
private  final Context context = this;
Button button1, button2 , button3, button4,autoButton;
TextView question,q_showinfo;
LinearLayout optionsLayout,optionsLayout1,optionsLayout2;
ImageView q_possibility;
Typeface font,digitalFont;
MediaPlayer sound;
SetQuestion setQ;
private Asked asked;
int input;
private TextView q_asked;
private TextView q_correct;
ProgressBar progressBar;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.question);
		Algorithm.setActivity(this);
		initvar();
		 
	   }
		 
	private void initvar(){
		
	 	progressBar = (ProgressBar) findViewById(R.id.progressBar);
		font = Typeface.createFromAsset(getAssets(), "LOBSTER.OTF");
		digitalFont = Typeface.createFromAsset(getAssets(), "digital.ttf");
		question = (TextView) findViewById(R.id.showQuestion);
		q_showinfo = (TextView) findViewById(R.id.q_showInfo);
		q_showinfo.setTypeface(font);
		question.setTypeface(font);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		autoButton = (Button) findViewById(R.id.autoAnswer);
		optionsLayout = (LinearLayout) findViewById(R.id.optionLayout);
		sound = MediaPlayer.create(Question.this, R.raw.touch);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		optionsLayout1 = (LinearLayout) findViewById(R.id.optionLayout1);
		optionsLayout2 = (LinearLayout) findViewById(R.id.optionLayout2);
		q_asked = (TextView) findViewById(R.id.q_asked);
		q_asked.setTypeface(digitalFont);
		q_correct= (TextView) findViewById(R.id.q_correct);
		q_correct.setTypeface(digitalFont);
		q_possibility = (ImageView) findViewById(R.id.q_possibility);
	
		 try {
			setQ = new SetQuestion(this.context);
			asked = new Asked(this.context,this,HandlingXMLStuff.TOTAL_QUESTION);
			asked.toAsk();
			Algorithm.setAskedCode(asked.queCode);
			Algorithm.setAskedValues(asked.getAskedValues());
			setView(asked.queCode);

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 possibilityClicked();
		  
	}

	 
	private void possibilityClicked(){
		q_possibility.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			 	Preference.alert(Question.this,"Answer Possibility","Answer to be right in this question has possibility of "+setQ.getPossibility(asked.queCode)+"% ");
			 
			}
		});
	}
	 private void setView(int queCode){
		 
		Preference p =new Preference(getSharedPreferences("Data",0)); 
		int tempAsked = Integer.parseInt(p.getValue("Asked"));
	   
	    if(tempAsked == 0){
	    	findViewById(R.id.q_scroll_layout).setVisibility(ScrollView.GONE);
	    	findViewById(R.id.q_taskbar).setVisibility(FrameLayout.GONE);
	    	findViewById(R.id.q_showinfo_layout).setVisibility(FrameLayout.VISIBLE);
	    	findViewById(R.id.q_showinfo_button).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				 	// TODO Auto-generated method stub
					 findViewById(R.id.q_showinfo_layout).setVisibility(FrameLayout.GONE);
					 findViewById(R.id.q_scroll_layout).setVisibility(ScrollView.VISIBLE);
					 findViewById(R.id.q_taskbar).setVisibility(FrameLayout.VISIBLE);
				 }
			});
	    }
	    tempAsked = tempAsked + 1;
	    q_asked.setText(Integer.toString(tempAsked));
	    q_correct.setText(p.getValue("CorrectAnswer"));
	    progressBar.setMax(HandlingXMLStuff.TOTAL_QUESTION );
	    progressBar.setProgress(tempAsked -1 );
	    
		// TODO Auto-generated method stub
		question.setText(setQ.getQuestion(queCode));
		 if(setQ.getType(queCode).equals("auto")){
		 	 optionsLayout.setVisibility(LinearLayout.VISIBLE);
			 optionsLayout1.setVisibility(LinearLayout.INVISIBLE);
			 optionsLayout2.setVisibility(LinearLayout.INVISIBLE);
			 hideNSheek();
			 autoButton.setText("Generate Answer");
			 autoButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Preference.progress(1, Question.this, "Auto Generate answer", "Generating auto answer");
				 	Preference.doLate(5000,Question.this);
					Algorithm.getAutoLogics();
				}
			});
			 	
				
				  
			}
			 
			
		
		 else  if(setQ.getType(queCode).equals("input")){
			 optionsLayout.setVisibility(LinearLayout.VISIBLE);
			 optionsLayout1.setVisibility(LinearLayout.INVISIBLE);
			 optionsLayout2.setVisibility(LinearLayout.INVISIBLE);
			 hideNSheek();
			 autoButton.setText("Enter Answer");
			 autoButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					takeInput();
				}
			});
			 
		 }
		 else{
		 	 button1.setBackgroundResource(img(setQ.getValue(queCode),1));
			 button2.setBackgroundResource(img(setQ.getValue(queCode),1));
			 button3.setBackgroundResource(img(setQ.getValue(queCode),1));
			 button4.setBackgroundResource(img(setQ.getValue(queCode),1));
			 
		 }
	}
private void hideNSheek(){
	 button1.setVisibility(Button.INVISIBLE);
	 button2.setVisibility(Button.INVISIBLE);
	 button3.setVisibility(Button.INVISIBLE);
	 button4.setVisibility(Button.INVISIBLE);
}
 
 private int img(String x,int code){
	  //int[] i = {R.drawable.visa,R.drawable.noodle,R.drawable.highway,R.drawable.homework,R.drawable.cards,R.drawable.answer,R.drawable.hall,R.drawable.guess,R.drawable.lottery,R.drawable.input};
	 int i,j; 
	 if(x.equals("visa")){
		  i =  R.drawable.op_visa;
		  j = R.drawable.op_visa_clicked;
	  }
	  
	 else if(x.equals("noodle")){
		  i = R.drawable.op_noodle;
		  j = R.drawable.op_noodle_clicked;
	  }
	  
	  else if(x.equals("highway")){
		  i = R.drawable.op_highway;
		  j = R.drawable.op_highway_clicked;
	  }
	  
	  else if(x.equals("cards")){
	  i = R.drawable.op_cards;
	  j = R.drawable.op_cards_clicked;
	  }
	  
	  else if(x.equals("power")){
	  i = R.drawable.op_power;
	  j = i;
	  }

	  else if(x.equals("homework")){
	  i = R.drawable.op_homework;
	  j =  i;
	  }
	 
	  else if(x.equals("answer")){
	  i = R.drawable.op_answer;
	  j = R.drawable.op_answer_clicked;
	  }
	  
	  else if(x.equals("hall")){
	  i = R.drawable.op_hall;
	  j = R.drawable.op_hall_clicked;
	  }
	  
	  else if(x.equals("lottery")){
	  i = R.drawable.op_lottery;
	  j = R.drawable.op_lottery_clicked;
	  }
	  
	 else{
	 i = R.drawable.op_guess;
	 j = R.drawable.op_guess_clicked;
	 }
	if(code == 1){
	 return i;}
	else{
		return j;
	}
 }

	@Override
public void onClick(View arg0) {
		Preference.playMusic(sound, this); 
		Preference.progress(1, this, "Checking answer", "Process answer...");
		
		int clicked = 0;
		// TODO Auto-generated method stub
		System.out.println(arg0.getId());
		switch(arg0.getId()){
		case  R.id.button1:
			clicked = 0;
			button1.setBackgroundResource(img(setQ.getValue(asked.queCode),2));
			//Toast.makeText(getBaseContext(), "button 1 clicked", Toast.LENGTH_LONG).show();
			break;
		case R.id.button2:
			button2.setBackgroundResource(img(setQ.getValue(asked.queCode),2));
			clicked  = 1 ;
			//Toast.makeText(getBaseContext(), "button 2 clicked", Toast.LENGTH_LONG).show();
			break;
		case R.id.button3:
			button3.setBackgroundResource(img(setQ.getValue(asked.queCode),2));
			clicked = 2;
			//Toast.makeText(getBaseContext(), "button 3 clicked", Toast.LENGTH_LONG).show();
			break;
		case R.id.button4:
			button4.setBackgroundResource(img(setQ.getValue(asked.queCode),2));
			clicked =  3;
			//Toast.makeText(getBaseContext(), "button 4 clicked", Toast.LENGTH_LONG).show();
			break;
		
		}
		
		new Algorithm(setQ.getPossibility(asked.queCode),clicked,4).start();
		 
	}
 
private void takeInput(){
	AlertDialog.Builder alert = new AlertDialog.Builder(this);

	alert.setTitle("What number am i guessing?");
	alert.setMessage("Enter any number range from 0 - 10");

	// Set an EditText view to get user input 
	final EditText et = new EditText(this);
	alert.setView(et);

	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	

	public void onClick(DialogInterface dialog, int whichButton) {
	  String value = et.getText().toString();
	  // Do something with value!
	     try{
	    	 int i = Integer.parseInt(value);
	        	if(i <= 10){
		    		//process answer here
		    		new Algorithm(80,i,10).start();
		    	}else{
		    		takeInput();
		    	}
	     }
	     catch(Exception e){
	    	 Toast.makeText(context, "Please dont enter any alphabet characters or special characters", Toast.LENGTH_LONG).show();
	    	 takeInput();
	     }
	  }
	});

	/*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	  public void onClick(DialogInterface dialog, int whichButton) {
	    // Canceled.
	  }
	});*/
	alert.setCancelable(false);
	alert.show();
	
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	finish();
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
// TODO Auto-generated method stub
	 MenuInflater mf = getMenuInflater();
	 mf.inflate(R.menu.q_menu, menu);
	 return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	 switch(item.getItemId()){
	 case R.id.q_refresh:
		 Preference.progress(1, this, "Refreshing ", "Trying to load new question...");
		 Preference.doLate(3000,this);
		 startActivity(new Intent(Question.this,Question.class));
		 return true;
	 case R.id.q_auto:
		  				Preference.progress(1, this, "Generating Answer", "Trying to generate auto answer...");
		 				Preference.doLate(3000,this);
		 			    Algorithm.getAutoLogics();
		  return true;
	 default:
			 return super.onOptionsItemSelected(item);
	  }
	 
}



		}


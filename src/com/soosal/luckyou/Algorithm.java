package com.soosal.luckyou;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
 
public class Algorithm {
private String[] cutValue;
private ArrayList<Integer> checkOption ;
private ArrayList<String> trueOption  ;  
private static int randomValue;
private int percentage;
private int option;
private int showOption;
private SharedPreferences set,set2;
private Preference settings,settings2;
private static Activity ACTIVITY;
private static String ASKED;
private static String ASKEDCODE;
public  Algorithm(int percentage, int option,int showOption) {
	        set 			=  	ACTIVITY.getSharedPreferences("Data",0);
	        settings 		=  	new Preference(set);
	        set2 			=  	ACTIVITY.getSharedPreferences("Asked",0);
	        settings2 		=  	new Preference(set2);
	 		this.percentage = 	percentage; 
            this.option 	= 	option;
            this.showOption = 	showOption;
            checkOption  	= 	new ArrayList<Integer>(this.showOption);
            trueOption 		= 	new ArrayList<String>(this.showOption);
            cutValue 		=	new String[showOption];
    }
    
     public Algorithm() {
	// TODO Auto-generated constructor stub
}

	public void start(){
        //percentage : chance of answer to be right
        //option: the option user has selected
        //showOption: the number of  option shown to the user
        int cut = 100 / cutValue.length;
        int x =  cut;
        int i = 0;
        while(x <= 100){
           cutValue[i] = Integer.toString(x); 
           x += cut;
           i++;
        }
         for(int a = 0; a <= cutValue.length - 1; a++){
       getLogics();
         }
       Iterator<String> it = trueOption.iterator();
       while(it.hasNext()){
       System.out.println(it.next());
       }
       checkAnswer(option,percentage);
    }
    
   public void getLogics(){
       randomValue = getRandom(this.showOption);
      if(checkOption.contains(randomValue))
      {
        getLogics();
      }else
      {
      checkOption.add(randomValue);
      trueOption.add(cutValue[randomValue]);
      }
  
   }
   
   public static void getAutoLogics(){ 
        try {
            int autoOption 		= getRandom(5);
            int autoPercentage 	= getRandom(100); 
            int autoAnswer 		= getRandom(autoOption);
                new Algorithm(autoPercentage, autoAnswer,autoOption).start();
                
         }catch(IllegalArgumentException e){
                Algorithm.getAutoLogics();       
        }
        
   }
   public void checkAnswer(int answer,int percentage){

        int x = Integer.parseInt(trueOption.get(answer).toString());
        
        if(x <= percentage){
        	System.out.println("your guess is right:  " + x);
        	savingSettingsData(1);
        }else{
        	savingSettingsData(0);
        	System.out.println("your guess is wrong: " + x);
 }
         System.out.println(settings.getValue("UserName"));
         
   }
 
   public static int getRandom(int range) {
       Random r = new Random();
       int  queCode = r.nextInt(range);
       return queCode;
   }

 private void savingSettingsData(int i){
	 float temp; 	 
	 float temp2;
	 if(i == 1){
		 temp 	= Float.parseFloat(settings.getValue("CorrectAnswer")) + 1;
		 temp2  = Float.parseFloat(settings.getValue("Asked")) + 1;
		 Preference.Vibrate(ACTIVITY,300);
		Toast.makeText(ACTIVITY.getBaseContext(), "Your Luck is with you !!!", Toast.LENGTH_SHORT).show();
	 }else{
		 temp 	= Float.parseFloat(settings.getValue("CorrectAnswer")) ;
		 temp2  = Float.parseFloat(settings.getValue("Asked")) + 1;
		 Toast.makeText(ACTIVITY.getBaseContext(), "Your Luck is not with you !!!", Toast.LENGTH_SHORT).show();
	 }
	 float luck = (temp / temp2 )* 100;
	 int t1 = (int) temp;
	 int t2 = (int) temp2;
	 settings.setValue("Luck",Float.toString(luck));
	 //System.out.println(luck + " ; " + temp + " ; " +temp2);
	 settings.setValue("CorrectAnswer",Integer.toString(t1));
	 settings.setValue("Asked", Integer.toString(t2));
	 //System.out.println(ASKED +" : "+ASKEDCODE+":"+luck);
	 settings2.setValue("Asked", ASKED + "-" +ASKEDCODE);
	 Preference.doLate(5000,ACTIVITY);
	 Intent nextScene = new Intent(ACTIVITY.getBaseContext(),Question.class);
	 ACTIVITY.startActivity(nextScene);
  }
 
 public static void setAskedValues(String x){
	 ASKED = x;
  }
 public static void setAskedCode(int x){
	 ASKEDCODE = Integer.toString(x);
 }
 public static void setActivity(Activity a){
	 ACTIVITY = a;
  }
}

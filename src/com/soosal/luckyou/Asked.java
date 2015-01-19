package com.soosal.luckyou;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Asked {

Activity a;
public int queCode;
private int range;
SharedPreferences set;
Preference settings;
public static String getAskedValues;
StringBuilder sb;
public Asked(Context c,Activity a,int range){
	this.a= a;
	new ArrayList<String>();
	this.range =  range;
	set = this.a.getSharedPreferences("Asked",0);
	settings = new Preference(set);
	}
	public void toAsk() {
		 String x = settings.getValue2("Asked", "empty");
		 
		 if(x.equals("empty")){
			  settings.setValue("Asked", Integer.toString(getRandom(this.range)) + "-");
			  toAsk();
		 }else{
			 String[] arr= x.split("-");
			 if(arr.length > 9){
				new Preference(this.a.getSharedPreferences("Data",0)).setValue("ResultAnimation", "0");
				this.a.startActivity(new Intent(this.a.getBaseContext(),Result.class));
			 }else{
			    ArrayList<String> temp = new ArrayList<String>(Arrays.asList(arr));
			     if(temp.contains(Integer.toString(queCode))){
			    	getRandom(this.range);
			    	toAsk();
			    }else{
			    	 String[] i = new String[temp.size()];
			    	 i = temp.toArray(i);
			    	 getAskedValues  = combine(i,"-");
			    	 //System.out.println("this is this: "+getAskedValues);
			     }
		   }
		 }
	}
	
 
	
	public int getRandom(int range) {
        Random r = new Random();
        queCode = r.nextInt(range);
        return queCode;
    }
	public String getAskedValues(){
		return getAskedValues;
	}
	
private	String combine(String[] s, String glue)
	{
	  int k = s.length;
	  if ( k == 0 )
	  {
	    return null;
	  }
	  StringBuilder out = new StringBuilder();
	  out.append( s[0] );
	  for ( int x=1; x < k; ++x )
	  {
	    out.append(glue).append(s[x]);
	  }
	  return out.toString();
	}
}

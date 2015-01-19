package com.soosal.luckyou;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class prefs extends PreferenceActivity implements  SharedPreferences.OnSharedPreferenceChangeListener 
{
    private static int pref=R.xml.preference;
     
	 

	@Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        try {
            getClass().getMethod("getFragmentManager");
            AddResourceApi11AndGreater();
        } catch (NoSuchMethodException e) { //Api < 11
            AddResourceApiLessThan11();
        }
        
    }

    @SuppressWarnings("deprecation")
    protected void AddResourceApiLessThan11()
    {
        addPreferencesFromResource(pref);
    }

    @TargetApi(11)
    protected void AddResourceApi11AndGreater()
    {
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PF()).commit();
    }

    @TargetApi(11)
    public static class PF extends PreferenceFragment
    {       
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(prefs.pref); //outer class
            // private members seem to be visible for inner class, and
            // making it static made things so much easier
        }
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		   if(arg1.equals("pr_name")){
			   String x = arg0.getString(arg1, "empty");
			   if(x.length() <= 4){
				     Toast.makeText(getBaseContext(), "Make sure name is not empty or name is above 4 character", Toast.LENGTH_SHORT).show();
			   }else{
				   Preference.toast(this, "Your new name has been updated");
				   new Preference(getSharedPreferences("Data",0)).setValue("UserName", x);
			   }   
		   }else if(arg1.equals("pr_sound")){
			   if(arg0.getBoolean("pr_sound", true))
				   Preference.toast(this, "Yes, You gona hear sound again");
			   else
				   Preference.toast(this, "Okay! No sound any more");
		   }else if(arg1.equals("pr_notification")){
			   if(arg0.getBoolean("pr_notification", true))
			       Preference.toast(this, "You will be notified everyday");
			   else
				    Preference.toast(this, "You will not receive any notification");
		   }else if(arg1.equals("pr_vibration")){
			   if(arg0.getBoolean("pr_vibration", true))
			  Preference.toast(this, "Yes! Ready to vibrate your mobile");
			   else 
			Preference.toast(this, "Okay! I will not vibrate your mobile anymore");
				   
		   }
	      
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
}
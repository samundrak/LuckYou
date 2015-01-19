package com.soosal.luckyou;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import android.content.Context;

public class SetQuestion extends HandlingXMLStuff {

     
    public static int queCode;    
    public SetQuestion(Context c) throws FileNotFoundException, IOException {
            super(c);
            super.setXML();
            queCode = getRandom(TOTAL_QUESTION);
    }
     
   

    public int getRandom(int range) {
        Random r = new Random();
        queCode = r.nextInt(range);
        return queCode;
    }
    
    
}

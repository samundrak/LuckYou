package com.soosal.luckyou;
 
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

import android.content.Context;
import android.content.res.AssetManager;

public class HandlingXMLStuff {
private ArrayList <String> questions;
private ArrayList <String> possibility;
private ArrayList <String> code;
private ArrayList <String> value;
private ArrayList<String>  type;
private DocumentBuilderFactory dbFactory;
private DocumentBuilder dbBuilder;
private Document doc;
public static int TOTAL_QUESTION, TOTAL_POSSIBILITY, TOTAL_CODE, TOTAL_VALUE = 0;
private Context mContext ;
    public HandlingXMLStuff(Context c)  {
       this.mContext =  c;
        questions   =  new ArrayList<String>();
        possibility =  new ArrayList<String>();
        code        =  new ArrayList<String>();
        value       =  new ArrayList<String>();
        type        = new ArrayList<String>();
    }

    public void start(){
      System.out.println("hello world");
    }
    protected String getQuestion(int x){
       return questions.get(x);
    }
    protected String getValue(int x){
          return value.get(x).toLowerCase(Locale.US).trim();
    }
    protected int getCode(int x){
       return Integer.parseInt(code.get(x));
    }
    protected int getPossibility(int x){
       return Integer.parseInt(possibility.get(x));
    }
    protected String getType(int x){
        return type.get(x);
     }
    protected void setXML(){
        xmlInit();
        TOTAL_QUESTION     = questions.size();
        TOTAL_POSSIBILITY  = possibility.size();
        TOTAL_CODE         = code.size();
        TOTAL_VALUE        = value.size(); 
    }
    protected void xmlInit() {
           try{
        	   
       dbFactory = DocumentBuilderFactory.newInstance();
       dbBuilder = dbFactory.newDocumentBuilder();
       AssetManager assetManager = mContext .getAssets();
	   InputStream inputStream = assetManager.open("questions.xml"); 
       doc =   dbBuilder.parse(inputStream);
       doc.getDocumentElement().normalize();
       NodeList listOfQuestion = doc.getElementsByTagName("question"); 
       listOfQuestion.getLength();
        
       for(int i = 0; i <= listOfQuestion.getLength() - 1; i++){
       Node firstQuestionNode = listOfQuestion.item(i);
        if(firstQuestionNode.getNodeType() ==  Node.ELEMENT_NODE){
           Element firstQuestionElement = (Element) firstQuestionNode;
           
           NodeList firstQueList = firstQuestionElement.getElementsByTagName("que");
           Element question = (Element) firstQueList.item(0);
           NodeList que = question.getChildNodes();
           
           NodeList firstPossibilityList = firstQuestionElement.getElementsByTagName("possibility");
           Element ePossibility = (Element) firstPossibilityList.item(0);
           NodeList node_possibility = ePossibility.getChildNodes();
           
           NodeList firstCodeList = firstQuestionElement.getElementsByTagName("code");
           Element eCode = (Element) firstCodeList.item(0);
           NodeList node_code = eCode.getChildNodes();
          
           NodeList firstValueList = firstQuestionElement.getElementsByTagName("value");
           Element eValue = (Element) firstValueList.item(0);
           NodeList node_value = eValue.getChildNodes();
           
           NodeList firstTypeList = firstQuestionElement.getElementsByTagName("type");
           Element eType = (Element) firstTypeList.item(0);
           NodeList node_type = eType.getChildNodes();
           
            this.questions.add(que.item(0).getNodeValue().trim());
            this.code.add(node_code.item(0).getNodeValue().trim());
            this.value.add(node_value.item(0).getNodeValue().trim());
            this.possibility.add(node_possibility.item(0).getNodeValue().trim());
            this.type.add(node_type.item(0).getNodeValue().trim());
        }
       }}catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }  
        }
         
    }


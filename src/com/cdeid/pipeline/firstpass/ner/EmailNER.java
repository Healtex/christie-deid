package com.cdeid.pipeline.firstpass.ner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gate.util.InvalidOffsetException;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 * 
 * Saves annotations to annotation set: passOne
 * 
 * Email NER for GATE documents.
 */
public class EmailNER {

	private static final String EMAIL_PATTERN = 
			"[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9- ]+)*@"
		  + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.(com|org|edu|gov|mil|co\\.uk|nhs\\.uk))"; //expand
		
	public static void run(gate.Document gateDoc)
	{
		String text = gateDoc.getContent().toString();
		Pattern p= Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);

		while(m.find())
		{
			addAnnotation(gateDoc, m.start(), m.end());
		}
	}
	
	private static void addAnnotation(gate.Document gateDoc, int start, int end)
	{
		gate.FeatureMap gateMap = gate.Factory.newFeatureMap();
		gateMap.put("CATEGORY", "CONTACT");
	
		    try {
		    	//annotation set: final_predictions, category: CONTACT, label: EMAIL
				gateDoc.getAnnotations("union").add((long)start, (long)end, "DEIDED", gateMap);
		    } catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InvalidOffsetException e) {
				e.printStackTrace();
			}		    
	}
}

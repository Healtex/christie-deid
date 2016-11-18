package com.cdeid.pipeline.firstpass.ner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gate.util.InvalidOffsetException;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 * 
 * Save annotations to annotation set: passOne
 * 
 * URL NER for GATE documents.
 */
public class UrlNER {
	private static final String URL_PATTERN = 
			"(https?://)?(www.)([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?(/[a-zA-Z0-9]+)?";
		
	public static void run(gate.Document gateDoc)
	{
		String text = gateDoc.getContent().toString();
		Pattern p= Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
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
		    	//annotation set: final_predictions, category: CONTACT, label: URL
				gateDoc.getAnnotations("union").add((long)start, (long)end, "DEIDED", gateMap);
		    } catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InvalidOffsetException e) {
				e.printStackTrace();
			}
	}
}

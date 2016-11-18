package com.cdeid.io;

import gate.Annotation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ivy.util.StringUtils;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 * 
 * Generate Output.
 */
public class Output {

	
	/**
	 * Gate XML US labels
	 * 
	 * @param gateDoc gate.Document
	 * @param annotationSet annotation set where all annotations are stored
	 * @return XML in String format
	 */
	public static String getXmlUK(gate.Document gateDoc, String annotationSet)
	{	
		String[] labels = { "NAME", "PATIENT", 	"MEDICALSTAFF", "USERNAME",		"OCCUPATION",
							"POSTCODE", "STREET",		"CITY-TVD", 	"COUNTY", 	"COUNTRY", "HOSPITAL", "LOCATION",
							"PHONE", 	"FAX", 			"URL", 			"EMAIL",	"CONTACT",
							"AGE", 		"MEDICALRECORD","IDNUM", "DOB"};

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
					"<ChristieDEID>\n" +
					"<TEXT><![CDATA[" + gateDoc.getContent().toString() + "]]></TEXT>\n" +
					"<TAGS>\n";
		
		int i=1;
		for(String label: labels)
		{
			List<Annotation> ann = new ArrayList<Annotation>(gateDoc.getAnnotations(annotationSet).get(label));		
			Collections.sort(ann, gate.Utils.OFFSET_COMPARATOR);
			
			for(Annotation a: ann)
			{	
				xml += "<"+a.getFeatures().get("CATEGORY").toString()+" id=\"P"+i+"\" "+"start=\"" + a.getStartNode().getOffset() + "\" " + "end=\""+ a.getEndNode().getOffset() +"\" " + "text=\""+ gate.Utils.stringFor(gateDoc, a) +"\" " + "TYPE=\""+a.getType().toString()+"\" />\n";
			i++;
			}
		}		
		xml += "</TAGS>\n" +
				"</ChristieDEID>";
		
		return xml;
	}
	
	/**
	 * Get GATE XML.
	 * 
	 * @param gateDoc gate.Document
	 * @return gate.Document XML
	 */
	public static String getGateXml(gate.Document gateDoc){
		return gateDoc.toXml();
	}
	
	
	/**
	 * Scrub all identifiers identified; replaces the entity mention with a string of 'd's  
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static String getDeidedText(gate.Document gateDoc, String annotationSet){
		StringBuffer text = new StringBuffer(gateDoc.getContent().toString());
		int length = 0;
		List<Annotation> ann = new ArrayList<Annotation>(gateDoc.getAnnotations(annotationSet).get("DEIDED"));		
		Collections.sort(ann, gate.Utils.OFFSET_COMPARATOR);
		
		for(Annotation a: ann)
		{	
			length = a.getEndNode().getOffset().intValue() - a.getStartNode().getOffset().intValue();
			text.replace(a.getStartNode().getOffset().intValue(), a.getEndNode().getOffset().intValue(), StringUtils.repeat("d", length));
		}
	return new String(text);
	}
	
	
	public static void saveNotes(File output_dir, ArrayList<gate.Document> gateDocList, String outputFormat) throws IOException{
		for(gate.Document gateDoc: gateDocList){
			if(outputFormat.equals("gatexml"))
				FileUtils.writeStringToFile(new File(output_dir.getAbsolutePath()+"/"+gateDoc.getName().substring(0, gateDoc.getName().length()-3)+"xml"), Output.getGateXml(gateDoc), "UTF-8");
			else if(outputFormat.equals("xml"))
				FileUtils.writeStringToFile(new File(output_dir.getAbsolutePath()+"/"+gateDoc.getName().substring(0, gateDoc.getName().length()-3)+"xml"), Output.getXmlUK(gateDoc, "passOne"), "UTF-8");
			else if(outputFormat.equals("dtext"))
				FileUtils.writeStringToFile(new File(output_dir.getAbsolutePath()+"/"+gateDoc.getName().substring(0, gateDoc.getName().length()-3)+"txt"), Output.getDeidedText(gateDoc, "union"), "UTF-8");
		}
	}

}

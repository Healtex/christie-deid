package com.cdeid.pipeline.other;

import gate.Annotation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 * 
 * Remove overlapping annotation. 
 * Overlap.class needed due to poor dictionary matching implemented as part of PassTwo.class.
 */
public class Overlap {
	public static void rmOverlap(gate.Document gateDoc, String annType, String annSet)
	{
		//annType: annotation type to process 
		ArrayList<Annotation> ann = new ArrayList<Annotation>(gateDoc.getAnnotations(annSet).get(annType));
		Collections.sort(ann, gate.Utils.OFFSET_COMPARATOR);
		
		for(int i=0;i<ann.size() && i+1 != ann.size();i++){
			for(int j=i+1;j<ann.size();j++) //j=next, i previous
			{
				if((ann.get(j).getStartNode().getOffset() >= ann.get(i).getStartNode().getOffset() && ann.get(j).getStartNode().getOffset() <= ann.get(i).getEndNode().getOffset()) 
				   || 
				   (ann.get(j).getEndNode().getOffset() <= ann.get(i).getEndNode().getOffset() && ann.get(j).getEndNode().getOffset() >= ann.get(i).getStartNode().getOffset()))
				{
					if((ann.get(i).getEndNode().getOffset() - ann.get(i).getStartNode().getOffset()) >= (ann.get(j).getEndNode().getOffset() - ann.get(j).getStartNode().getOffset())){	
						gateDoc.getAnnotations(annSet).remove(ann.remove(j));
							j--;
					}
					else{
						gateDoc.getAnnotations(annSet).remove(ann.remove(i));
					}
				}
			}
		}
	}	
}

package com.cdeid.controller;

import gate.creole.ResourceInstantiationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.cdeid.io.Input;
import com.cdeid.io.Output;
import com.cdeid.pipeline.firstpass.PassOne;
import com.cdeid.pipeline.firstpass.ner.EmailNER;
import com.cdeid.pipeline.firstpass.ner.UrlNER;
import com.cdeid.pipeline.other.Overlap;
import com.cdeid.pipeline.other.PostProcess;
import com.cdeid.pipeline.other.PreProcess;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 * 
 * Controller.
 * 
 */
public class Controller {
	
	public Controller(){}
	
	/**
	 * Run NLP components:
	 * 1. Pre-processing
	 * 2. PassOne
	 * 3. Post-processing 
	 * 
	 * @param r_corpus input directory
	 * @param w_corpus output directory
	 * @throws ResourceInstantiationException
	 * @throws IOException 
	 * 
	 */
	public void runLetterNERs(File input_dir, File output_dir, String outputFormat) throws ResourceInstantiationException, IOException{
		
		PreProcess p = new PreProcess();
		gate.Document gateDoc = null;

		Collection<File> fileList = Input.readFolder(input_dir);
		
		
		//to store processed documents @runtime
		ArrayList<gate.Document> gateDocList = new ArrayList<gate.Document>();
						
		
		//for each input document:
		System.out.println("Processing document(s):");
		for(File f: fileList){
			System.out.print("\r"+f.getName());
		
			
			gateDoc = Input.getGateDocument(f);
			
			if(gateDoc == null)
				continue;

			//NLP pre-process i.e., Tokeniser and Sentence splitter
			gateDoc.setName(f.getName()); //set name; necessary for PassTwo.class
			p.preProcessingPipeline(gateDoc);
			gateDocList.add(gateDoc);	
		}
		System.out.print("\r");
		
		/*
		 * run pass one
		 */
		 runFirstPassPipeline_UkLetter(gateDocList);
				
		/*
		 * run post processing pipeline
		 */
		 runPostProcessingLetterPipeline(gateDocList);
		
		/*
		 * output 
		 */
		Output.saveNotes(output_dir, gateDocList, outputFormat);
	}
	
	
	/**
	 * Run first pass pipeline. Prerequisite: PreProcess.java processed gate.Documents.
	 * 
	 * @param gateDocList list of gate.Documents
	 */
	private static void runFirstPassPipeline_UkLetter(ArrayList<gate.Document> gateDocList){
		PassOne p1 = new PassOne();
		for(gate.Document g: gateDocList){
			p1.firstPassPipeline_Letter(g);
			Overlap.rmOverlap(g, "HOSPITAL", "passOne"); //needed?
			UrlNER.run(g);
			EmailNER.run(g);
		}
	}
	
	/**
	 * Run post processing pipeline for letters only. Prerequisite: PassOne.java and PassTwo.java processed gate.Documents.
	 * 
	 * @param gateDocList
	 */
	private static void runPostProcessingLetterPipeline(ArrayList<gate.Document> gateDocList){
		PostProcess pp = new PostProcess(); //post processing
		for(gate.Document g: gateDocList){
			pp.postProcessingLetterPipeline(g);
		}
	}
	
}
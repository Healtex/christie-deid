package com.cdeid.pipeline.other;

import java.io.File;
import java.io.IOException;

import gate.Corpus;
import gate.CorpusController;
import gate.Factory;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 * 
 * Saves annotations to annotation set: final_predictions
 * 
 * See GATE/pipeline/postProcessing.xgapp
 */
public class PostProcess {

	private static CorpusController postProc1;
	private static Corpus corpus1;
	
	public PostProcess(){
		PostProcess.init();
	}
	
	public CorpusController getController(){
		return postProc1;
	}
	
	private static void init(){
		/*
		 * post-processing pipeline
		 */
		String path1 = "GATE/pipeline/postProcessing-letter.xgapp";

		try {
			PostProcess.postProc1 = (CorpusController)PersistenceManager.loadObjectFromFile(new File(path1));
			PostProcess.corpus1 = Factory.newCorpus("c1");
			PostProcess.postProc1.setCorpus(corpus1);
			
		} catch (GateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Post processing pipeline for letters only 
	 * 
	 * @param gateDoc gate.Document
	 */
	public void postProcessingLetterPipeline(gate.Document gateDoc)
	{		
		try{
			PostProcess.corpus1.add(gateDoc); 
			PostProcess.postProc1.execute();
		} catch (GateException e) {
			System.err.println("Pipeline.postProcessingPipeline(...): " + e.getMessage() );
		}
				
		PostProcess.corpus1.clear();
		PostProcess.corpus1.cleanup();
		PostProcess.postProc1.cleanup();
		Factory.deleteResource(corpus1);
		Factory.deleteResource(postProc1);
	} 
}

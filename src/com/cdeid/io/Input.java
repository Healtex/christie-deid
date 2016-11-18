package com.cdeid.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import gate.creole.ResourceInstantiationException;

public class Input {
	
	/**
	 * Get the list of files from a given input directory.
	 * 
	 * @param input_dir
	 * @return
	 */
	public static Collection<File> readFolder(File input_dir){
		Collection<File> fileList = FileUtils.listFiles(new File(input_dir.getAbsolutePath()), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		return fileList;
	}
	
	/**
	 * Read given file format.
	 * 
	 * @param f file
	 * @return gate.Document
	 * @throws IOException
	 * @throws ResourceInstantiationException
	 * 
	 * TODO: expand input formats ...
	 */
	public static gate.Document getGateDocument(File f) {
		
		PDFTextStripper stripper;
		gate.Document gateDoc = null;
		try {
			stripper = new PDFTextStripper();

			if(f.getName().endsWith(".txt")){
				gateDoc = gate.Factory.newDocument(FileUtils.readFileToString(f, "UTF-8"));
			}else if(f.getName().endsWith(".pdf")){
				PDDocument d = PDDocument.load(f);
				gateDoc = gate.Factory.newDocument(stripper.getText(d));
			}else{
				System.err.println("ChristieDEID can only process plain text (.txt) and searchable portable document format (.pdf) documents.\n"
								+  "Amend the filename extension accordingly.");
				System.err.println("Document err: " + f.getName());
		}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ResourceInstantiationException e) {
			e.printStackTrace();
		} 
		
	return gateDoc;
	}
}

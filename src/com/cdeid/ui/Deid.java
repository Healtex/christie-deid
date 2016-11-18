package com.cdeid.ui;

import gate.creole.ResourceInstantiationException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import com.cdeid.controller.Controller;

/**
 * mDEID Copyright (C) 2015-16 A. Dehghan
 * ChristieDEID  Copyright (C) 2016  Christie NHS Foundation Trust
 *
 * User interface.
 */
public class Deid {

	public static void main(String[] args) throws URISyntaxException, IOException, ResourceInstantiationException
	{	
		try {
			parseCmdLine(args);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			usage();
		}
	}
	
	/**
	 * print usage information.
	 * 
	 * @return
	 */
	private static String usage()
	{
		return "\n****************************************************************************************" +
				"\nChristieDEID Copyright (C) 2016 \n\n"

				+ "Usage: java -jar target/christie-deid-0.1.jar [--letters] [--xml|--gatexml|--dtext] <input_dir> <output_dir>\n\n" +
				"*****************************************************************************************\n";
	}
	
	private static void parseCmdLine(String[] args) throws IOException, ResourceInstantiationException
	{
		Controller controller = new Controller();
		
		if(args.length < 3 || args.length > 4){
			System.out.println(usage());
		}
		else if(args[1].toLowerCase().contentEquals("--gatexml"))
		{	
			String r_corpus = args[2];
			String w_output = args[3];
			if(args[0].toLowerCase().contentEquals("--letters"))
				controller.runLetterNERs(new File(r_corpus), new File(w_output), "gatexml");
		} 
		else if(args[1].toLowerCase().contentEquals("--xml"))
		{	
			String r_corpus = args[2];
			String w_output = args[3];
			
			if(args[0].toLowerCase().contentEquals("--letters"))
				controller.runLetterNERs(new File(r_corpus), new File(w_output), "xml");

		}
		else if(args[1].toLowerCase().contentEquals("--dtext"))
		{	
			String r_corpus = args[2];
			String w_output = args[3];
			
			if(args[0].toLowerCase().contentEquals("--letters"))
				controller.runLetterNERs(new File(r_corpus), new File(w_output), "dtext");

		}
		else if(args[1].toLowerCase().contentEquals("--dtextdb"))
		{	
			String r_corpus = args[2];
			String w_output = args[3];
			
			if(args[0].toLowerCase().contentEquals("--letters"))
				controller.runLetterNERs(new File(r_corpus), new File(w_output), "dtext");

		}
		else if(args[0].toLowerCase().contentEquals("--help")||args[0].toLowerCase().contentEquals("-h"))
			System.out.println(usage());
		else
			System.err.println(usage());
	}
}


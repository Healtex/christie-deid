# ChristieDEID v.0.1
A de-identification tool for removing personal identifiable information from clinical free text narratives. The current version include the knowledge-driven tool for processing clinical letters.

## Features
Available NERs: 
* AGE, 
* CITY-TVD, 
* COUNTRY, 
* COUNTY, 
* DOB, 
* FAX, 
* HOSPITAL, 
* ID, 
* MEDICALSTAFF, 
* PATIENT, 
* PHONE, 
* POSTCODE, 
* PROFESSION, 
* STREET, 
* EMAIL, and 
* URL.

## USAGE
The ChristieDEID is a simple command line tool. The source code should be straight forward to disinsect and integrate (see Controller.java) into your own application.

### Installation

1. Build maven project: mvn package 
2. Basic usage: 


### Example usecases

Use case 1: Print help screen.
 java -jar target/christie-deid-0.1.jar -h


Use case 2: Process a set of plain text letters in directory *inputdir/* and save results in *outputdir/*. The output should include de-identified text. 
 java -jar target/cDeid-0.1.jar --letters --dtext inputdir/ outputdir/ 

/**
 * 
 */
package celfocus.project.be.java.phoneNumbers;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import celfocus.project.be.java.countrycodes.CountryCodes;
import celfocus.project.be.java.files.FileHandler;

/**
 * The purpose of PhoneNumber is to process the 
 * input file with phone numbers
 * @author cpcra
 *
 */
public class ProcessNumbers {

	private CountryCodes countryCodes ;
	private Map<Integer, PhoneNumber> mapPhoneNumbers = new HashMap<>();
	private Map<String, Integer> outputMap = new LinkedHashMap<>();
	private PhoneNumber phoneNumber;
	private static final int iPortugalCode = 351;
	private String phoneNumbersFilename = "input.txt";
	private String strNoSpecialCharPatt = "^[0-9 +]*$";
	private String strLongNumberPatt = "^(\\+|00)(?:[0-9] ?){8,13}[0-9]$";
	private String strShortNumberPatt = "^(?:[1-9]?)[0-9]{4,5}$";
	
	/**
	 * Constructor
	 */
	public ProcessNumbers() {
		this.countryCodes = new CountryCodes();
		this.setListPhoneNumbers();
		//countryCodes.printMapCountryCodes();
	}
	
	/**
	 * Constructor
	 * @param phoneNumbersFilename
	 */
	public ProcessNumbers(String phoneNumbersFilename) {
		this.countryCodes = new CountryCodes();
		this.phoneNumbersFilename = phoneNumbersFilename;
		this.setListPhoneNumbers();
	}
	
	/**
	 * @return the strNoSpecialCharPatt
	 */
	public String getStrNoSpecialCharPatt() {
		return strNoSpecialCharPatt;
	}
	
	/**
	 * @return the strLongNumberPatt
	 */
	public String getStrLongNumberPatt() {
		return strLongNumberPatt;
	}
	
	/**
	 * @return the strShortNumberPatt
	 */
	public String getStrShortNumberPatt() {
		return strShortNumberPatt;
	}
	
	/**
	 * getStreamPhoneNumbers: gets the stream of strings
	 * read from the input file
	 * @return Stream<String>
	 */
	private Stream<String> getStreamPhoneNumbers() {
		try {
			return (new FileHandler(this.phoneNumbersFilename).getListFileLines());
		} catch (IOException ioex) {
			System.out.println("Unable to get data from input file!! " + ioex.toString());
			return null;
		} catch (Exception ex) {
			System.out.println("Unable to get data from input file!! " + ex.toString());
			return null;
		}
	}
	
	/**
	 * setListPhoneNumbers: builds the list of phone numbers
	 * @param mapCountryCodes the mapCountryCodes to set
	 */
	private void setListPhoneNumbers() {
		Stream<String> streamStrings = getStreamPhoneNumbers();
		if(streamStrings!=null) {
			List<String> listOfLines = streamStrings.collect(Collectors.toList());
			listOfLines.forEach(line -> processInputFileLine(line));
		}
	}
	
	
	/**
	 * @param strLine
	 */
	private boolean filterSpecialCharacters (String strLine, String strPattern) {
		if (strLine == null || strLine.trim().isEmpty()) {
	         System.out.println("Incorrect format of string");
	         return false;
	     }
	     Pattern p = Pattern.compile(strPattern);
	     Matcher m = p.matcher(strLine.trim());
	     if (m.find()) {
	        //System.out.println(strLine+": the pattern matches!");
	    	 return true;
	     }else {
	         //System.out.println(strLine+": there is special char not allowed!");
	         return false;
	     }
	 }
	
	/**
	 * @param strLine
	 */
	private boolean filterLongShort (String strLine, String strPattern) {
		if (strLine == null || strLine.trim().isEmpty()) {
	         System.out.println("Incorrect format of string");
	         return false;
	     }
	     Pattern p = Pattern.compile(strPattern);
	     Matcher m = p.matcher(strLine.trim());
	     if (m.matches()) {
	        //System.out.println(strLine+": the pattern matches!");
	    	 return true;
	     }else {
	         //System.out.println(strLine+": the pattern does not matches!");
	         return false;
	     }
	 }
	
	/**
	 * @param line
	 */
	private void processInputFileLine(String line) {
		//first verify if the line is valid
		if(!(this.filterSpecialCharacters(line.trim(), this.getStrNoSpecialCharPatt())) ) {
			return;
		}else{
			//test the Long numbers
			if(this.filterLongShort(line.trim(), this.getStrLongNumberPatt())) {
				//process the long
				insertLongNumber(line);
			}else if(this.filterLongShort(line.trim(), this.getStrShortNumberPatt())){
				//process the short which is a portuguese number
				insertShortNumber(line);
			}
			//System.out.println(iPortugalCode+"="+this.mapPhoneNumbers.get(iPortugalCode).getiNumberOcurrences());
		}
	}
	
	/**
	 * @param line
	 */
	private void insertShortNumber(String line) {
		//process the short which is a portuguese number
		int icode = iPortugalCode;;
		String countryName = this.countryCodes.getCountryByCode(icode);
		if(this.mapPhoneNumbers.get(icode)!=null) {
			this.phoneNumber = mapPhoneNumbers.get(icode);
			this.phoneNumber.IncrementOccurrence(line);
		}else {
			this.phoneNumber = new PhoneNumber(icode, countryName, line);
		}
		this.mapPhoneNumbers.put(icode, phoneNumber);
	}
	
	/**
	 * @param line
	 */
	private void insertLongNumber(String line) {
		//process the long number
		int icode;
		String strCountry;
		String strNumber;
		//clean all white spaces
		line = line.replaceAll(" ", "");
		
		//the international code may be 
		//composed of 1, 2 or 3 ints
		int[] arriCode = {0, 0, 0};
		String[] arrNumber = {"", "", ""};
		int index = -1;
		
		//long started with +
		if(line.indexOf('+')!=-1) {
			index=1;
		} else { //long started with 00
			index=2;
		}
		arriCode[0] = Integer.parseInt(line.substring(index, index+1));
		arrNumber[0] = line.substring(index+1);
		
		arriCode[1] = Integer.parseInt(line.substring(index, index+2));
		arrNumber[1] = line.substring(index+2);
		
		arriCode[2] = Integer.parseInt(line.substring(index, index+3));
		arrNumber[2] = line.substring(index+3);
		
		for(int i=0; i<arriCode.length; i++) {
			icode = arriCode[i];
			strNumber = arrNumber[i];
			//Verify if code exists
			if(!this.countryCodes.isCountryCode(icode)) {
				//the code does not exist, skip loop
				continue;
			}
			//the code exists
			if(this.mapPhoneNumbers.get(icode)!=null) {
				//update data
				this.phoneNumber = mapPhoneNumbers.get(icode);
				this.phoneNumber.IncrementOccurrence(strNumber);
			}else {
				//new data
				strCountry = this.countryCodes.getCountryByCode(icode);
				this.phoneNumber = new PhoneNumber(icode, strCountry, strNumber);
			}
			this.mapPhoneNumbers.put(icode, phoneNumber);
		}//for cycle
	}
	
	/**
	 * 
	 */
	private void setOutputMap() {
		//iterate the mapPhoneNumbers
		if(!this.mapPhoneNumbers.isEmpty()) {
			for (Map.Entry<Integer, PhoneNumber> entry : mapPhoneNumbers.entrySet()) {
	            outputMap.put(mapPhoneNumbers.get(entry.getKey()).getStrCountryName(), 
	                          mapPhoneNumbers.get(entry.getKey()).getInteNumberOcurrences());
				//System.out.println("Key = " + mapPhoneNumbers.get(entry.getKey()).getStrCountryName() +
	            //                ", Value = " + mapPhoneNumbers.get(entry.getKey()).getInteNumberOcurrences());
			}
		}
	}
	
	/**
	 * 
	 */
	private void sortOutputMap() {
		setOutputMap();
		this.outputMap = outputMap.entrySet()
								  .stream()
								  .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
								  .collect(Collectors.toMap(
										    Map.Entry::getKey, 
										    Map.Entry::getValue, 
										    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}
	

	/**
	 * 
	 */
	public void setOutput() {
		sortOutputMap();
		this.outputMap.entrySet()
		  .stream()
		  .forEach(line -> System.out.println(line.toString().replace("=", ":")));
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ProcessNumbers processNumbers = new ProcessNumbers();
		processNumbers.setOutput();
		
		/*
		String strTestValid = "+3511 23";
		String strTestInvalid = "+35112 /";
		String strTestLong1= "+01 2345678";
		String strTestLong2= "000 12345678";
		String strTestShort1= "123456";
		String strTestShort2= "1234567";
		
		boolean res = processNumbers.filterSpecialCharacters(strTestValid, processNumbers.getStrNoSpecialCharPatt());
		System.out.println("Special characters1: " + strTestValid + " - " + res);
		
		res = processNumbers.filterSpecialCharacters(strTestInvalid, processNumbers.getStrNoSpecialCharPatt());
		System.out.println("Special characters2: " + strTestInvalid + " - " + res);
		
		res = processNumbers.filterLongShort(strTestLong1, processNumbers.getStrLongNumberPatt());
		System.out.println("Long1: " + strTestLong1 + " - " + res);
		
		res = processNumbers.filterLongShort(strTestLong2, processNumbers.getStrLongNumberPatt());
		System.out.println("Long2: " + strTestLong2 + " - " + res);
		
		res = processNumbers.filterLongShort(strTestShort1, processNumbers.getStrShortNumberPatt());
		System.out.println("Short1: " + strTestShort1 + " - " + res);
		
		res = processNumbers.filterLongShort(strTestShort2, processNumbers.getStrShortNumberPatt());
		System.out.println("Short2: " + strTestShort2 + " - " + res);
		*/
	}

	

}

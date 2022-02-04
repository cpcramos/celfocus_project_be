/**
 * 
 */
package celfocus.project.be.java.countrycodes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import celfocus.project.be.java.files.FileHandler;

/**
 * The purpose of CountryCodes is to handle country codes file information
 *
 * @author cpcramos
 */
public class CountryCodes {
	
	//Each country as an international Code
	//If it is the same (e: Canada and USA both use 1)
	//the String will be "country1 | country2"
	private Map<Integer, String> mapCountryCodes = new HashMap<>();
	private String countryCodesFilename = "coutryCodes.txt";
	
	/**
	 * Constructor
	 */
	public CountryCodes() {
		setMapCountryCodes();
	}
	
	/**
	 * Constructor
	 * @param filename
	 */
	public CountryCodes(String filename) {
		this.countryCodesFilename = filename;
		setMapCountryCodes();
	}
	
	/**
	 * getStreamCountryCodes: gets the stream of strings
	 * read from the country code file
	 * @return Stream<String>
	 */
	private Stream<String> getStreamCountryCodes() {
		try {
			return (new FileHandler(this.countryCodesFilename).getListFileLines());
		} catch (IOException ioex) {
			System.out.println("Unable to get Country codes!! " + ioex.toString());
			return null;
		} catch (Exception ex) {
			System.out.println("Unable to get Country codes!! " + ex.toString());
			return null;
		}
	}
	
	/**
	 * setMapCountryCodes: builds the map of country codes
	 * @param mapCountryCodes the mapCountryCodes to set
	 */
	private void setMapCountryCodes() {
		Stream<String> streamStrings = getStreamCountryCodes();
		if(streamStrings!=null) {
			List<String> listOfLines = streamStrings.collect(Collectors.toList());
			listOfLines.forEach(line -> splitCountryCodesLine(line.trim()));
		}
	}
	
	/**
	 * splitCountryCodesLine: process each line of the list of country codes
	 * @param line
	 */
	private void splitCountryCodesLine(String line) {
		if((line != null) && (!line.isEmpty())) {
			//splits the line to get an int key and a String value
			String[] arrString = line.split("-");
			int iKey = Integer.parseInt(arrString[1]);
			String strValue = "";
			
			if(this.mapCountryCodes.containsKey(iKey)){
				strValue = strValue.concat(mapCountryCodes.get(iKey)).concat(" | ".concat(arrString[0]));
			}else {
				strValue = strValue.concat(arrString[0]);
			}
			this.mapCountryCodes.put(iKey, strValue);
		}
	}
	
	/**
	 * @return the mapCountryCodes
	 */
	public Map<Integer, String> getMapCountryCodes() {
		return mapCountryCodes;
	}
	
	/**
	 * @return the mapCountryCodes
	 */
	public void printMapCountryCodes() {
		if(this.mapCountryCodes != null) {
			mapCountryCodes.forEach((key, value) -> System.out.println((key + ":" + value)));
		}
	}
	
	/**
	 * isCountryCode: return true if the country
	 *  code is part of the map
	 * @param key
	 * @return
	 */
	public boolean isCountryCode (int key) {
		return this.mapCountryCodes.containsKey(key);
	}
	
	/**
	 * getCountryByCode: gets the country with
	 * the input code
	 * @param key
	 * @return
	 */
	public String getCountryByCode(int key) {
		return this.mapCountryCodes.get(key);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountryCodes countryCodes = new CountryCodes();
		countryCodes.printMapCountryCodes();
	}

}

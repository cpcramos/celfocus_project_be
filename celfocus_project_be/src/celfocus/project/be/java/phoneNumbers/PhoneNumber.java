/**
 * 
 */
package celfocus.project.be.java.phoneNumbers;

import java.util.HashSet;
import java.util.Set;

/**
 * The purpose of PhoneNumber is to represent a phone number
 * with country code and number
 * @author cpcra
 *
 */
public class PhoneNumber {
	
	private int iCountryCode;
	private String strCountryName;

	//phone number are a set in order to avoid duplicates
	private Set<String> setOfPhoneNumbers = new HashSet<> ();
	private int iNumberOcurrences = 0;

	/**
	 * Constructor
	 * @param iCountryCode
	 * @param strPhoneNumber
	 */
	public PhoneNumber(int iCountryCode, String strCountry, String strPhoneNumber) {
		this.iCountryCode = iCountryCode;
		this.strCountryName = strCountry;
		this.IncrementOccurrence(strPhoneNumber);
	}

	/**
	 * @return the iCountryCode
	 */
	public int getiCountryCode() {
		return iCountryCode;
	}

	/**
	 * @param iCountryCode the iCountryCode to set
	 */
	public void setiCountryCode(int iCountryCode) {
		this.iCountryCode = iCountryCode;
	}
	
	/**
	 * @return the strCountryName
	 */
	public String getStrCountryName() {
		return strCountryName;
	}
	
	/**
	 * @return the strCountryName
	 */
	public String setStrCountryName(String strCountryName) {
		this.strCountryName = strCountryName;
		return this.strCountryName;
	}

	/**
	 * @param setOfPhoneNumbers the setOfPhoneNumbers to set
	 */
	public void addSetOfPhoneNumbers(String strPhoneNumber) {
		this.setOfPhoneNumbers.add(strPhoneNumber);
	}
	
	public void IncrementOccurrence(String strPhoneNumber) {
		//verify if the number already exists
		if(!this.setOfPhoneNumbers.contains(strPhoneNumber)) {
			this.addSetOfPhoneNumbers(strPhoneNumber);
			this.iNumberOcurrences++;
		}
	}
	
	/**
	 * @return the iNumberOcurrences
	 */
	public int getiNumberOcurrences() {
		return this.iNumberOcurrences;
	}
	
	/**
	 * @return the iNumberOcurrences as Integer
	 */
	public Integer getInteNumberOcurrences() {
		return Integer.valueOf(this.iNumberOcurrences);
	}
	
	/**
	 * @param setOfPhoneNumbers the setOfPhoneNumbers to set
	 */
	public void addSetOfPhoneNumbers(Set<String> setOfPhoneNumbers) {
		this.setOfPhoneNumbers = setOfPhoneNumbers;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

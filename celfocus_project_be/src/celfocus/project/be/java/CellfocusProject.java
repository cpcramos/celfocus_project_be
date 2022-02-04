/**
 * 
 */
package celfocus.project.be.java;

import celfocus.project.be.java.phoneNumbers.ProcessNumbers;

/**
 * @author cpcra
 *
 */
public class CellfocusProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Cellfocus Project BE");
		ProcessNumbers processNumbers;
		if(args.length>0) {
			processNumbers = new ProcessNumbers(args[0]);
		} else {
			processNumbers = new ProcessNumbers();
		}
		processNumbers.setOutput();
	}

}

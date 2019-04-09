package org.sap.challenge.util;

import java.util.BitSet;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public final class Util {
	
	static final Logger logger = Logger.getLogger(Util.class);
	static Scanner scanner = null;
	
	static {
		BasicConfigurator.configure();
	}
	
	// private constructor to stop instantiation
	private Util() {
		
	}
	
	/**
	 * Method to print a string on the console
	 * @param str
	 */
	public static void outputConsoleMessage(String str) {
		System.out.println(str);
		
	}
	
	/**
	 * Method to print a string on the console
	 * @param str
	 */
	public static void outputConsoleMessage(String str, Object obj) {
		System.out.println(str + ": " + obj);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static String inputConsoleMessage() {
		scanner = new Scanner(System.in);
		String userInputData = scanner.nextLine();
		return userInputData;
	}
	
	public static void closeScanner() {
		if(scanner != null) {
			scanner.close();
		}
	}
	/**
	 * Method to generate logs in the log file.
	 * @param str
	 */
	public static void logMessage(String str) {
		logger.debug(str + "in this");
	}
	
	/**
	 *Overloaded method :  Method to generate logs in the log file.
	 * @param str
	 */
	public static void logMessage(String str, Object obj) {
		logger.debug(str + ": " + obj);
	}
	
	public static void printBits(String prompt, BitSet b) {
		System.out.print(prompt + " ");
		for (int i = 0; i < 20; i++) {
			System.out.print(b.get(i) ? "1" : "0");
		}
		System.out.println();
	}
}

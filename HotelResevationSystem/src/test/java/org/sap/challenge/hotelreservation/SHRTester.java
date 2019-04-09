package org.sap.challenge.hotelreservation;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Main class to execute all test cases
 * @author tanvi
 *
 */
public class SHRTester {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(HotelReservationTest.class);
		if(result.wasSuccessful()) {
			System.out.println("All Tests Passed!");
		}else {
			for (Failure failure : result.getFailures()) {
	            System.out.println(failure.toString());
	        }
		}
	}

}

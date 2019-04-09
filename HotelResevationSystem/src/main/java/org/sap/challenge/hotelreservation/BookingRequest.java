package org.sap.challenge.hotelreservation;

import java.util.BitSet;

public class BookingRequest {

	private int startDay;  
	private int endDay;
	private int numRequestedDays;
	// the requestVector represents the requested days in form of bits (1: Occupied 0: Unoccupied)
	private BitSet requestVector = new BitSet(365);	

	
	public BookingRequest(int numPlanningDays) {
		// TODO Auto-generated constructor stub
		this.requestVector = new BitSet(numPlanningDays);
	}
	
	

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public int getNumRequestedDays() {
		return numRequestedDays;
	}

	public void setNumRequestedDays(int numRequestedDays) {
		this.numRequestedDays = numRequestedDays;
	}

	public BitSet getRequestVector() {
		return requestVector;
	}

	public void setRequestVector(BitSet requestVector) {
		this.requestVector = requestVector;
	}

}

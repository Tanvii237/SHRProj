package org.sap.challenge.hotelreservation;

import java.util.BitSet;

public class Room {

	private static int idInitializer = -1; // initializer variable to set roomId
	private final int roomId; // parameter uniquely identifies the room object
	// the roomState represents the occupancy status of the room in the form of bits (1: Occupied 0: Unoccupied)
	private BitSet roomState = new BitSet(365); // a bit vector of length number of days
	private int numOccupiedDays = 0;

	public Room(int numPlanningDays) { 
		roomId = ++idInitializer;
		roomState = new BitSet(numPlanningDays);
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public BitSet getRoomState() {
		return roomState;
	}

	public void setRoomState(BitSet roomState) {
		this.roomState = roomState;
	}

	public int getNumOccupiedDays() {
		return numOccupiedDays;
	}

	public void setNumOccupiedDays(int numOccupiedDays) {
		this.numOccupiedDays = numOccupiedDays;
	}

}

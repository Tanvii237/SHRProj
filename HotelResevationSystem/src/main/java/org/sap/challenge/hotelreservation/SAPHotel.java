package org.sap.challenge.hotelreservation;

import java.util.ArrayList;
import java.util.BitSet;

//import org.sap.challenge.util.Util;

public class SAPHotel {
	
	public static enum REQUEST_RESPONSE{
		ACCEPTED,
		DECLINED,
		REJECTED
	}
	//hotelSize: number of rooms in the hotel
	int hotelSize;
	//numDaysInReservationSystem: number of days that can be booked 
	int numDaysInReservationSystem = 365;

	private ArrayList<Room> occupiedHotelRooms = new ArrayList<Room>(); // maintains the state of the hotel.
	// In case of no booking being made to the hotel, this remains empty.

	BookingRequest request = null;

	/**
	 * Only create SAPHotel by providing required values
	 */
	@SuppressWarnings("unused")
	private SAPHotel() {}
	
	/**
	 * To instantiate object of this class with hotelSize.
	 * It is called from the main class (CLI)
	 * 
	 * @param hotelSize
	 */
	public SAPHotel(int hotelSize) {
		this.hotelSize = hotelSize;

	}
	
	/**
	 * To instantiate object of this class with hotelSize and
	 * no. of days in the reservation days
	 * 
	 * @param hotelSize
	 * @param numDaysInReservationSystem
	 */
	public SAPHotel(int hotelSize, int numDaysInReservationSystem) {
		this(hotelSize);
		this.numDaysInReservationSystem = numDaysInReservationSystem;
	}

	

	/**
	 * Method to start processing the booking request
	 * 
	 * @param request
	 * @return ACCEPT/DECLINE/REJECT: based on the availability in the hotel and the
	 *         validity of the booking request
	 */
	public REQUEST_RESPONSE processRequest(BookingRequest request) {
		REQUEST_RESPONSE requestResponse = null;
		// validate the method parameter request before initializing the request
		// instance variable
		boolean isValid = validateRequest(request.getStartDay(), request.getEndDay());
		if (!isValid) {
			requestResponse = REQUEST_RESPONSE.REJECTED; // in case the requested days are outside the planning system or endDay <
										// startDays
			return requestResponse;
		}

		// as the request is validated, create bit request vector and set it in the
		// request object
		BitSet requestVector = new BitSet(this.numDaysInReservationSystem);
		// Util.outputConsoleMessage("---------------------------------------------------------------");
		requestVector.flip(request.getStartDay(), request.getEndDay() + 1);
		// Util.printBits("requestVector: ", requestVector);
		request.setRequestVector(requestVector);
		this.request = request; // set request instance variable

		// check hotel availability corresponding to the made request
		requestResponse = checkAvailability() ? REQUEST_RESPONSE.ACCEPTED : REQUEST_RESPONSE.DECLINED; // set availability based on the response
		return requestResponse;
	}

	/**
	 * Method to validate the request by checking if startDay or endDay is out of range
	 * It also checks if endDay < startDay
	 * 
	 * @param startDay
	 * @param endDay
	 * @return true if request is valid
	 */
	public boolean validateRequest(int startDay, int endDay) {
		boolean isValid = true;
		if (startDay < 0 || startDay > 364 || endDay < 0 || endDay > 364 || endDay < startDay) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Method to check the availability of the hotel corresponding to the request
	 * 
	 * @return true if hotel is available for the requested days
	 */
	private boolean checkAvailability() {
		boolean isAvailable = false;

		// first condition to check : if OccupiedHotelRooms instance variable is empty
		if (this.occupiedHotelRooms == null || this.occupiedHotelRooms.size() == 0) { // implies no previous booking in
																						// any of the room
			addAndBookRoom();
			isAvailable = true;
			return isAvailable; // implies that the hotel is available
		} else { // in case there are previous booking(s) in the hotel

			// We sequentially check each room object in OccupiedHotelRooms until we find an
			// available space
			for (Room room : this.occupiedHotelRooms) {
				int numOccupiedDays = room.getNumOccupiedDays();

				int numAvailableDays = this.numDaysInReservationSystem - numOccupiedDays;
				int numRequestedDays = request.getNumRequestedDays();
				if (numAvailableDays < numRequestedDays) { // number of available days is less than requested days
					continue; // process the next room
				}
				//Util.logMessage("Processing room ", room.getRoomId());
				BitSet roomState = (BitSet) room.getRoomState().clone();
				BitSet updatedroomState = computeUpdatedRoomState(roomState);
				// compute expected number of occupied days for the room, if the room is booked
				// for the request
				int expectedNumOccupiedDaysIfBooked = numOccupiedDays + numRequestedDays;

				// check availability in the room: room available if expectedNumOccupiedDays are
				// equal to
				// the no. of 1's in the updated roomState vector
				boolean isRoomAvailable = false;
				isRoomAvailable = checkRoomAvailability(updatedroomState, expectedNumOccupiedDaysIfBooked);
				// if room is available, book room and no need to check other rooms
				if (isRoomAvailable) {
					// call to bookRoom()
					bookRoom(room, updatedroomState, expectedNumOccupiedDaysIfBooked);
					isAvailable = true;
					break; // no need to check further rooms
				}
			}

			// if OccupiedHotelRooms size < hotelSize : i.e. we have not considered all the
			// rooms
			// we still have rooms that are completely unoccupied.
			if (!isAvailable && (this.occupiedHotelRooms.size() < this.hotelSize)) {
				// add room in OccupiedHotelRooms and Book it.
				addAndBookRoom();
				isAvailable = true;
				return isAvailable; // implies that the hotel is available
			}
		}
		return isAvailable;
	}

	/**
	 * Method to check if room can process the request. If the updated room state has
	 * the same number of occupied rooms as the expectedNumOccupiedDays then this
	 * method returns "true" , i.e, the room is available for the request
	 * 
	 * @param room
	 * @return "true" , in case the room is available for the request
	 */
	private boolean checkRoomAvailability(BitSet computedRoomState, int expectedNumOccupiedDays) {
		boolean isRoomAvailable = false;
		//Util.logMessage("in check room ");
		//Util.printBits("cardinality check of bitset : ", computedRoomState);
		// check if the vector cardinality is same as expected occupied days
		int cardinality = computedRoomState.cardinality();
		if (cardinality == expectedNumOccupiedDays) {
			isRoomAvailable = true;
		}
		return isRoomAvailable;
	}

	/**
	 * To add new room object in the occupied HotelRooms
	 * and invokes bookRoom 
	 * 
	 */
	private void addAndBookRoom() {
		Room room = addRoomInOccupiedRooms();
		BitSet roomState = (BitSet) room.getRoomState().clone(); // clone the state vector and set it to a new bitSet
		BitSet updatedroomState = computeUpdatedRoomState(roomState);
		bookRoom(room, updatedroomState, request.getNumRequestedDays());
	}

	/**
	 * Method to add a new room object in in the OccupiedHotelRooms instance
	 * variable
	 * 
	 * @return newly added room object
	 */
	private Room addRoomInOccupiedRooms() {
		Room room = new Room(this.numDaysInReservationSystem); // create Room type object
		//Util.outputConsoleMessage("ADDING room with room id:  " + room.getRoomId());
		this.occupiedHotelRooms.add(room);
		return room;
	}

	/**
	 * Method to book the particular room for the request. It updates the
	 * roomStateVector and the numOccupiedDays of the room
	 * 
	 * @param room
	 * @param newRoomState
	 * @param updatedNumOccupiedDays
	 */
	private void bookRoom(Room room, BitSet newRoomState, int updatedNumOccupiedDays) {
		//Util.outputConsoleMessage("room id:  " + room.getRoomId());
		//Util.printBits("updated  room: ", newRoomState);
		room.setRoomState(newRoomState);
		room.setNumOccupiedDays(updatedNumOccupiedDays);
	}

	/**
	 * Method to compute the room state by performing XOR operation on the existing room state with the
	 * request vector
	 * 
	 * @param clonedRoomState
	 * @return the updated room State vector (i.e result of xor operation on the
	 *         roomState)
	 */
	private BitSet computeUpdatedRoomState(BitSet clonedRoomState) {
		//Util.printBits("computedBitSet befor xor ", clonedRoomState);
		clonedRoomState.xor(request.getRequestVector());
		//Util.printBits("computedBitSet after xor ", clonedRoomState);
		return clonedRoomState;
	}

	/**
	 * Method to create request object for the particular booking
	 * 
	 * @param startDay
	 * @param endDay
	 * @return created request object
	 */
	public BookingRequest createRequest(int startDay, int endDay) {

		BookingRequest request = new BookingRequest(this.numDaysInReservationSystem);
		request.setStartDay(startDay);
		request.setEndDay(endDay);
		int numrequestedDays = endDay - startDay + 1; // as we have assumed 0 to be the first day.
		request.setNumRequestedDays(numrequestedDays);

		return request;
	}

}

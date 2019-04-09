package org.sap.challenge.hotelreservation;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;
import org.sap.challenge.hotelreservation.testhelpers.BookingRequestTest;

/**
 * 
 * @author tanvi
 *
 */
public class HotelReservationTest {

	final static SAPHotel.REQUEST_RESPONSE ACCEPT = SAPHotel.REQUEST_RESPONSE.ACCEPTED;
	final static SAPHotel.REQUEST_RESPONSE DECLINE = SAPHotel.REQUEST_RESPONSE.DECLINED;
	final static SAPHotel.REQUEST_RESPONSE REJECT = SAPHotel.REQUEST_RESPONSE.REJECTED;

	/**
	 * Test case to test all ACCEPT Scenarios
	 */
	@Test
	public void testAllAcceptScenario() {

		SAPHotel hotel = new SAPHotel(3);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 5), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(7, 13), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(3, 9), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(5, 7), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(6, 6), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 4), ACCEPT));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test  DECLINE after All Accept
	 *
	 */

	@Test
	public void testDecline() {
		SAPHotel hotel = new SAPHotel(3);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 0), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 2), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 4), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 2), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 3), DECLINE));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test  DECLINE after All Accept with different input
	 *
	 */
	@Test
	public void testDecline_1() {
		SAPHotel hotel = new SAPHotel(3);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 0), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 2), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 4), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 2), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 3), DECLINE));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test  DECLINE after All Accept with different input
	 */
	@Test
	public void testDecline_2() {
		SAPHotel hotel = new SAPHotel(3);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(1, 3), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 5), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(1, 9), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 15), DECLINE));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test ACCEPT after DECLINE 
	 */
	@Test
	public void testAcceptAfterDecline() {
		SAPHotel hotel = new SAPHotel(3);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(1, 3), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 15), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(1, 9), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 5), DECLINE));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(4, 9), ACCEPT));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 *  Test case to test complex requests
	 */
	@Test
	public void testComplexRequests() {
		SAPHotel hotel = new SAPHotel(2);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(1, 3), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 4), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(2, 3), DECLINE));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(5, 5), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(4, 10), DECLINE));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(10, 10), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(6, 7), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(8, 10), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(8, 9), ACCEPT));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test DECLINE if entire hotel is booked 
	 */
	@Test
	public void testBookAllThenDecline() {
		SAPHotel hotel = new SAPHotel(1);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 364), ACCEPT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(364, 364), DECLINE));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test ACCEPT until entire hotel is booked by making 1 day request  and then DECLINE
	 */
	@Test
	public void testBookAllRoomsOneDayPerRequest() {
		int sizeOfHotel = 10;
		SAPHotel hotel = new SAPHotel(sizeOfHotel);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();

		// Book entire hotel
		for (int i = 0; i < sizeOfHotel; i++) {
			for (int j = 0; j < 365; j++) {

				listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(j, j), ACCEPT));

			}
		}
		// attempt to book room first day and last day
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(0, 0), DECLINE));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(364, 364), DECLINE));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}
	
	/**
	 * test case to test repeated request 
	 */
	@Test
	public void testBookSameDayRangeRequest() {

		int sizeOfHotel = 10;
		SAPHotel hotel = new SAPHotel(sizeOfHotel);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();

		// Book hotel all rooms for a specific date range
		for (int i = 0; i < sizeOfHotel; i++) {
			listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(13, 19), ACCEPT));
		}
		// attempt to book room first day and last day
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(13, 19), DECLINE));

		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);

	}

	/**
	 * Test case to test invalid Start day and end day
	 */
	@Test
	public void testInvalidStartEndDay() {

		SAPHotel hotel = new SAPHotel(3);
		ArrayList<BookingRequestTest> listBookMultipleStatesArrayList = new ArrayList<BookingRequestTest>();
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(-0, -5), REJECT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(-9, 0), REJECT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(8, -9), REJECT));
		listBookMultipleStatesArrayList.add(new BookingRequestTest(hotel.createRequest(9, 8), REJECT));
		checkBookingRequestStatusAssertion(hotel, listBookMultipleStatesArrayList);
	}

	
	public void checkBookingRequestStatusAssertion(SAPHotel hotel,
			ArrayList<BookingRequestTest> listBookMultipleStatesArrayList) {
		for (BookingRequestTest req : listBookMultipleStatesArrayList) {
			SAPHotel.REQUEST_RESPONSE expectedRequestStatus = req.getExpectedRequestStatus();
			BookingRequest request = req.getRequest();
			SAPHotel.REQUEST_RESPONSE requestStatus = hotel.processRequest(request);

			// System.out.println("requestStatus: " + requestStatus + "
			// expectedRequestStatus: " + expectedRequestStatus);
			assertEquals(expectedRequestStatus, requestStatus);
		}
	}

}

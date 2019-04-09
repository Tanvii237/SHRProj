package org.sap.challenge.hotelreservation.testhelpers;

import org.sap.challenge.hotelreservation.BookingRequest;
import org.sap.challenge.hotelreservation.SAPHotel;

public class BookingRequestTest {
	BookingRequest request = null;

	SAPHotel.REQUEST_RESPONSE expectedRequestStatus = null;

	public BookingRequestTest(BookingRequest request, SAPHotel.REQUEST_RESPONSE expectedRequestStatus) {
		this.request = request;
		this.expectedRequestStatus = expectedRequestStatus;
	}

	public BookingRequest getRequest() {
		return request;
	}

	public SAPHotel.REQUEST_RESPONSE getExpectedRequestStatus() {
		return expectedRequestStatus;
	}

}

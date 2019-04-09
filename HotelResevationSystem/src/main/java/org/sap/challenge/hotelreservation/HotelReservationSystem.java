package org.sap.challenge.hotelreservation;

import org.sap.challenge.util.Util;

/**
 * Main Class which configures the hotel and starts the reservation system
 * 
 * @author Tanvi Gupta
 *
 */
public class HotelReservationSystem {

	public static void main(String[] args) {

		Boolean isHotelSizeDefined = false;
		int hotelSize = 1; // assumption that hotel has at least one room.

		while (!isHotelSizeDefined) { // until the user gives a valid hotelSize
			try {
				// Initialize hotel configuration
				Util.outputConsoleMessage("Enter the size of hotel (1-1000): ");
				// read user Input
				String inputString = Util.inputConsoleMessage();
				// check if it can be parsed to int, if not then user is asked to enter again
				hotelSize = Integer.parseInt(inputString);

				// in case input is parsed as int, we check if the input is in valid
				// range(1-1000)
				if (hotelSize > 0 && hotelSize <= 1000) {
					isHotelSizeDefined = true;
				} else {
					Util.outputConsoleMessage("INVALID INPUT: Please enter Hotel size in range (1-1000).\n");
				}
			} catch (Exception e) {
				Util.outputConsoleMessage("INVALID INPUT: Input is not a number.\n");
			}
		}

		// show hotel reservation menu
		HotelReservationSystem hrs = new HotelReservationSystem();
		hrs.showHotelResevationMenu(hotelSize);

	}

	/**
	 * Method to ask user for a vaild booking request and to start its processing.
	 * 
	 * @param hotelSize
	 */
	public void showHotelResevationMenu(int hotelSize) {
		SAPHotel hotel = new SAPHotel(hotelSize);
		Boolean isBookingDone = false;
		Util.outputConsoleMessage("\n**********WELCOME TO SAP HOTEL***********");

		while (!isBookingDone) { // until the user wants to make a booking request
			try {
				Util.outputConsoleMessage("\nTo request a booking, please provide the following information:");
				Util.outputConsoleMessage("Note: '0' is a valid input and is considered as the current day.\n"
						+ "You can make a booking request limited to 1 year (i.e. upto day '364')\n");
				Util.outputConsoleMessage("Please enter start day (0-364):");
				String startDayStr = Util.inputConsoleMessage();
				Util.outputConsoleMessage("Please enter end day(0-364):");
				String endDayStr = Util.inputConsoleMessage();

				int startDay = Integer.parseInt(startDayStr);
				int endDay = Integer.parseInt(endDayStr);

				// in case startDay and Day are valid integers, we proceed to process request
				BookingRequest request = hotel.createRequest(startDay, endDay);
				SAPHotel.REQUEST_RESPONSE requestStatus = hotel.processRequest(request);
				if (requestStatus == SAPHotel.REQUEST_RESPONSE.REJECTED) {
					Util.outputConsoleMessage(
							"The booking days are INVALID: Either start/end Day is out of range (0,364) or endDay is greater than startDay\n");
					continue;
				} else {
					Util.outputConsoleMessage("Your request for booking Days: (" + startDay + "-" + endDay + ") is: "
							+ String.valueOf(requestStatus) + "\n");
				}

				// ask if the user wants to book more dates
				Util.outputConsoleMessage(
						"Do you want to exit the booking system( Press Y or type yes)? \nor press ENTER to continue....");
				String yesOrNo = Util.inputConsoleMessage();
				if (yesOrNo.equalsIgnoreCase("Y") || yesOrNo.equalsIgnoreCase("Yes")) {
					isBookingDone = true;
					Util.outputConsoleMessage("Thank You for using SAP Hotel Bookings System. Have a Good Day!!\n");
					Util.closeScanner();
				}

			} catch (NumberFormatException ex) {

				Util.outputConsoleMessage("INVALID INPUT: Start Day or End Day is not a valid number.");
			}
			catch (Exception ex) {
				Util.outputConsoleMessage("System Error: Try Again ");
				break;
			}
		}

	}

}

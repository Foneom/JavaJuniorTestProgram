package com.gridnine.testing;

import com.gridnine.testing.FlightBuilder;
import com.gridnine.testing.Input;

/**
 * Program interface class
 */
public class StartUI {

    private final String MSG = "";

    public static void allSegments() {
        System.out.println("===All segments===");
        FlightBuilder.createListAllFlights(FlightBuilder.getFilteredFlightList());
    }

    public static void flightUntilTheCurrentTime() {
        System.out.println("===Segments until the current time===");
        FlightBuilder.createFlightUntilTheCurrentTime(FlightBuilder.getFilteredFlightList(),
                FlightBuilder.getThreeDaysFromNow());
    }

    public static void flightWhereArrivalDateBeforeDepartureDate() {
        System.out.println("===FlightWhereArrivalDateBeforeDepartureDate==");
        FlightBuilder.createFlightWhereArrivalDateBeforeDepartureDate(FlightBuilder.getFilteredFlightList());
    }

    public static void flightWhenTotalTimeMoreThenTwoHours() {
        System.out.println("===FlightWhenTotalTimeMoreThenTwoHours===");
        FlightBuilder.createFlightWhenTotalTimeMoreThenTwoHours(FlightBuilder.getFilteredFlightList());
    }

    public void init(Input input) {
        boolean flag = true;
        while (flag) {
            showMenu();
            int select = Integer.parseInt(input.askStr(MSG));
            if (select == 0) {
                allSegments();
            } else if (select == 1) {
                flightUntilTheCurrentTime();
            } else if (select == 2) {
                flightWhereArrivalDateBeforeDepartureDate();
            } else if (select == 3) {
                flightWhenTotalTimeMoreThenTwoHours();
            } else if (select == 4) {
                flag = false;
            }
        }
    }

    public void showMenu() {
        System.out.println("Select a flight filter:"
                + "\n0.Full list if flights"
                + "\n1.Departure to the current point in time"
                + "\n2.The arrival date is earlier than the departure date"
                + "\n3.The total time on earth is more than two hours"
                + "\n4.Exit"
        );
    }
}

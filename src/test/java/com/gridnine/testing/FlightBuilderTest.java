package com.gridnine.testing;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test class of FlightBuilder
 */
public class FlightBuilderTest {

    /**
     * Test of the method displays all flights
     */
    @Test
    public void whenCreateListOfAllFlights() {
        LocalDateTime departure = LocalDateTime.now(); //вылет
        LocalDateTime arrived = departure.plusHours(2); //прилет

        List<Flight> expected = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived.plusHours(1),
                        departure.plusHours(2), arrived.plusHours(3))
        );

        List<Flight> actual = FlightBuilder.createListAllFlights(expected);

        assertThat(expected, is(actual));
    }

    /**
     * Method test that excludes all flights where there is a departure until the current time
     */
    @Test
    public void whenCreateFlightUntilTheCurrentTime() {
        LocalDateTime departure = LocalDateTime.now(); //вылет
        LocalDateTime arrived = departure.plusHours(2); //прилет

        List<Flight> flights = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure.minusHours(4), arrived.plusHours(1))

        );

        List<Flight> expected = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived));

        List<Flight> actual = FlightBuilder.createFlightUntilTheCurrentTime(flights, departure);

        assertThat(expected, is(actual));
    }

    /**
     * Method test that  excludes all flights where the arrival date is earlier than the departure date
     */
    @Test
    public void whenCreateFlightWhereArrivalDateBeforeDepartureDate() {
        LocalDateTime departure = LocalDateTime.now(); //вылет
        LocalDateTime arrived = departure.plusHours(2); //прилет

        List<Flight> flights = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived.minusDays(4)),
                FlightBuilder.createFlight(departure, arrived.plusHours(4)),
                FlightBuilder.createFlight(departure, arrived)
        );

        List<Flight> expected = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived.plusHours(4)),
                FlightBuilder.createFlight(departure, arrived)
        );
        List<Flight> actual = FlightBuilder.createFlightWhereArrivalDateBeforeDepartureDate(flights);

        assertThat(expected, is(actual));
    }

    /**
     * Method test that excludes all flights where there is transportation in which there is more than two hours
     * between arrival and departure.
     */
    @Test
    public void whenFlightWhenTotalTimeMoreThenTwoHours() {
        LocalDateTime departure = LocalDateTime.now(); //вылет
        LocalDateTime arrived = departure.plusHours(2); //прилет

        List<Flight> flights = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived, arrived.plusHours(3), arrived.plusHours(5)),
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived.plusHours(2))
        );

        List<Flight> expected = Arrays.asList(
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived),
                FlightBuilder.createFlight(departure, arrived.plusHours(2))
        );
        List<Flight> actual = FlightBuilder.createFlightWhenTotalTimeMoreThenTwoHours(flights);

        assertThat(expected, is(actual));
    }

    /**
     * Method test that creations flight when not even number of arguments
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenCreateFlightWithNotEvenArgument() {
        FlightBuilder.createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2));
    }

    /**
     * Method test that creations flight when an even number of arguments
     */
    @Test
    public void whenCreateFlightTrue() {
        FlightBuilder.createFlight(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
    }


}
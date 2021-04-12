package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Factory class to get sample list of flights.
 */
public class FlightBuilder {

    /**
     * Test starting point
     */
    private static final LocalDateTime THREE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(3);
    /**
     * Test list of flights
     */
    private static final List<Flight> FILTERED_FLIGHT_LIST = Arrays.asList(
            createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)),
            //A normal multi segment flight
            createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                    THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(5)),
            //A flight departing in the past
            createFlight(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW),
            //A flight that departs before it arrives
            createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6)),
            //A flight with more than two hours ground time
            createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                    THREE_DAYS_FROM_NOW.plusHours(5), THREE_DAYS_FROM_NOW.plusHours(6)),
            //Another flight with more than two hours ground time
            createFlight(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2),
                    THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(4),
                    THREE_DAYS_FROM_NOW.plusHours(6), THREE_DAYS_FROM_NOW.plusHours(7))
    );

    public static LocalDateTime getThreeDaysFromNow() {
        return THREE_DAYS_FROM_NOW;
    }

    public static List<Flight> getFilteredFlightList() {
        return FILTERED_FLIGHT_LIST;
    }


    /**
     * This method displays all flights
     *
     * @param flights
     * @return
     */
    public static List<Flight> createListAllFlights(List<Flight> flights) {

        flights.stream().map(Flight::getSegments)
                .forEach(System.out::println);
        return flights;
    }

    /**
     * This method excludes all flights where there is a departure until the current time
     *
     * @return
     */
    public static List<Flight> createFlightUntilTheCurrentTime(List<Flight> flights, LocalDateTime departure) {

        List<Flight> filtered = new ArrayList<>();
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                if (!segment.getDepartureDate().isBefore(departure)) {
                    filtered.add(flight);
                    break;
                }
            }
        }
        filtered.forEach(System.out::println);

        return filtered;
    }

    /**
     * This method excludes all flights where the arrival date is earlier than the departure date
     *
     * @param flights
     * @return
     */

    public static List<Flight> createFlightWhereArrivalDateBeforeDepartureDate(List<Flight> flights) {

        List<Flight> filtered = new ArrayList<>();
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                if (!segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    filtered.add(flight);
                    break;
                }
            }
        }
        filtered.forEach(System.out::println);

        return filtered;
    }

    /**
     * This method excludes all flights where there is transportation in which there is more than two hours
     * between arrival and departure.
     *
     * @return
     */
    public static List<Flight> createFlightWhenTotalTimeMoreThenTwoHours(List<Flight> flights) {

        List<Flight> filtered = new ArrayList<>();

        for (Flight flight : flights) {
            if (flight.getSegments().size() < 2) {
                filtered.add(flight);
            } else {
                for (int i = 0; i < flight.getSegments().size() - 1; i++) {

                    if ((flight.getSegments().get(i + 1).getDepartureDate().getHour() -
                            flight.getSegments().get(i).getArrivalDate().getHour() < 2)
                    ) {
                        filtered.add(flight);
                    }
                }
            }
        }


        filtered.forEach(System.out::println);

        return filtered;

    }

    static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}

/**
 * Bean that represents a flight.
 */
class Flight {
    private final List<Segment> segments;

    Flight(final List<Segment> segs) {
        segments = segs;
    }

    List<Segment> getSegments() {
        return segments;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Flight other = (Flight) obj;
        if (segments == null) {
            if (other.segments != null)
                return false;
        } else if (!segments.equals(other.segments))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(segments);
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}

/**
 * Bean that represents a flight segment.
 */
class Segment {

    private final LocalDateTime departureDate;

    private final LocalDateTime arrivalDate;

    Segment(final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    LocalDateTime getDepartureDate() {
        return departureDate;
    }

    LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return departureDate.equals(segment.departureDate) &&
                arrivalDate.equals(segment.arrivalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureDate, arrivalDate);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + '|' + arrivalDate.format(fmt)
                + ']';
    }
}



package com.dnlab.dway.flight.util

import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.region.domain.Airport
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification
import java.sql.Timestamp

object FlightSpecification {
    fun hasDepartureTimeBetween(start: Timestamp, end: Timestamp): Specification<Flight> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.between(root["departureTime"], start, end)
        }
    }

    fun hasDepartureAirportId(airportId: String): Specification<Flight> {
        return Specification { root, _, criteriaBuilder ->
            val join = root.join<Flight, Airport>("departureAirport", JoinType.INNER)
            criteriaBuilder.equal(join.get<String>("id"), airportId)
        }
    }

    fun hasArrivalAirportId(airportId: String): Specification<Flight> {
        return Specification { root, _, criteriaBuilder ->
            val join = root.join<Flight, Airport>("arrivalAirport", JoinType.INNER)
            criteriaBuilder.equal(join.get<String>("id"), airportId)
        }
    }
}

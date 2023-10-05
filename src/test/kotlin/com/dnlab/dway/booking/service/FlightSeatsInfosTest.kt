package com.dnlab.dway.booking.service

import com.dnlab.dway.booking.dto.request.DeptArriAirportCode
import com.dnlab.dway.booking.dto.request.ItineraryInfoRequestDto
import com.dnlab.dway.booking.repository.TicketRepository
import com.dnlab.dway.flight.domain.*
import com.dnlab.dway.flight.repository.FlightRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest
class FlightSeatsInfosTest {

    @InjectMocks
    private lateinit var bookingService: BookingServiceImpl

    @Mock
    private lateinit var flightRepository: FlightRepository

    @Mock
    private lateinit var ticketRepository: TicketRepository

    @Test
    fun findFlightSeatInfos_Success() {
        val schedule = LocalDate.now()
        val itineraryInfoRequestDto = ItineraryInfoRequestDto(
            schedule = LocalDate.now(),
            airportCodes = listOf(DeptArriAirportCode("DEP", "ARR")),
            adultCount = 1,
            childCount = 0,
            infantCount = 0
        )

        val flight = mock(Flight::class.java)
        val flightSeats = mock(FlightSeats::class.java)
        val aircraft = Aircraft(model = "B737-200", manufacture = Manufacture.BOEING)

        whenever(flight.departureTime).thenReturn(LocalDateTime.now())
        whenever(flight.arrivalTime).thenReturn(LocalDateTime.now())
        whenever(flight.code).thenReturn("TW000")
        whenever(flight.aircraft).thenReturn(aircraft)
        whenever(flightSeats.fareGrade).thenReturn(FareGrade.NORMAL)
        whenever(flightSeats.seatGrade).thenReturn(SeatGrade.ECONOMY)
        whenever(flightRepository.findFlightInfosBy(any(), any(), any())).thenReturn(listOf(flight))
        whenever(flight.flightSeats).thenReturn(mutableListOf(flightSeats))
        whenever(ticketRepository.countAvailableSeatsByFlightSeats(any())).thenReturn(10)

        val result = bookingService.findFlightSeatInfos(itineraryInfoRequestDto)

        assertThat(result).isNotNull
        assertThat(result.flightFareInfos).hasSize(1)
        assertThat(result.flightFareInfos.first().flightSeatInfos).hasSize(1)
        assertThat(result.flightFareInfos.first().flightSeatInfos.first().remainingSeats).isEqualTo(
            10
        )
    }

    @Test
    fun findFlightFareInfos_IllegalArgument() {
        // 준비
        val itineraryInfoRequestDto = ItineraryInfoRequestDto(
            schedule = null,
            airportCodes = listOf(DeptArriAirportCode("DEP", "ARR")),
            adultCount = 1,
            childCount = 0,
            infantCount = 0
        )

        // 실행
        assertThrows<IllegalArgumentException> {
            bookingService.findFlightSeatInfos(
                itineraryInfoRequestDto
            )
        }
    }


}
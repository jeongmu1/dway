package com.dnlab.dway.flight.service

import com.dnlab.dway.flight.domain.Aircraft
import com.dnlab.dway.flight.domain.Flight
import com.dnlab.dway.flight.domain.Manufacture
import com.dnlab.dway.flight.dto.request.NewFlightRequestDto
import com.dnlab.dway.flight.repository.*
import com.dnlab.dway.region.domain.Airport
import com.dnlab.dway.region.domain.Country
import com.dnlab.dway.region.domain.RegionCategory
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.sql.Timestamp

@ExtendWith(MockitoExtension::class)
internal class AddingFlightTest {
    @Mock
    private lateinit var flightRepository: FlightRepository

    @Mock
    private lateinit var flightSeatsRepository: FlightSeatsRepository

    @Mock
    private lateinit var aircraftRepository: AircraftRepository

    @Mock
    private lateinit var airportRepository: AirportRepository

    @Mock
    private lateinit var ticketRepository: TicketRepository

    @InjectMocks
    private lateinit var flightService: FlightServiceImpl

    private val country = Country(
        id = "KR",
        code3 = "KOR",
        korName = "대한민국",
        engName = "Republic of Korea",
        numberCode = 82
    )

    private val airport1 = Airport(
        id = "PUS",
        korName = "김해국제공항",
        engName = "Gimhae Internatioinal Airport",
        regionName = "부산/김해",
        regionCategory = RegionCategory.KOREA,
        country = country
    )

    private val airport2 = Airport(
        id = "ICN",
        korName = "인천국제공항",
        engName = "Incheon Internatioinal Airport",
        regionName = "서울/인천",
        regionCategory = RegionCategory.KOREA,
        country = country
    )

    private val aircraft = Aircraft(
        id = 1L,
        model = "B737-800",
        manufacture = Manufacture.BOEING
    )

    private val flight = Flight(
        id = 1L,
        code = "DW100",
        arrivalAirport = airport1,
        departureAirport = airport2,
        createdDate = Timestamp(System.currentTimeMillis()),
        departureTime = Timestamp(System.currentTimeMillis()),
        arrivalTime = Timestamp(System.currentTimeMillis()),
        aircraft = aircraft
    )

    @Test
    fun addFlight_success() {
        val flightRequestDto = NewFlightRequestDto(
            code = "DW100",
            departureAirport = "PUS",
            arrivalAirport = "ICN",
            departureTime = Timestamp(System.currentTimeMillis()),
            arrivalTime = Timestamp(System.currentTimeMillis()),
            aircraftModel = "B737-800",
            flightSeatInfo = listOf()
        )

        `when`(aircraftRepository.findAircraftByModel(anyString())).thenReturn(aircraft)
        `when`(airportRepository.findAirportById("PUS")).thenReturn(airport1)
        `when`(airportRepository.findAirportById("ICN")).thenReturn(airport2)
        `when`(flightRepository.save(any())).thenReturn(flight)

        assertNotNull(flightService.addFlight(flightRequestDto))
    }

}
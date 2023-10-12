package com.flight_airasia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight_airasia.dto.ReservationRequest;
import com.flight_airasia.entity.Airasia;
import com.flight_airasia.entity.Passenger;
import com.flight_airasia.entity.Reservation;
import com.flight_airasia.repository.FlightRepository;
import com.flight_airasia.repository.PassengerRepository;
import com.flight_airasia.repository.ReservationRepository;
import com.flight_airasia.utilities.EmailUtil;
import com.flight_airasia.utilities.PDFgenerator;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private PassengerRepository passengerRepo;
	
	@Autowired
	private FlightRepository flightRepo;
	
	@Autowired
	private ReservationRepository reservationRepo;
	
	@Autowired
	private PDFgenerator pdfGenertor;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public Reservation bookFlight(ReservationRequest request) {
		
		
		Passenger passenger = new Passenger();
		passenger.setFirstName(request.getFirstName());
		passenger.setLastName(request.getLastName());
		passenger.setMiddleName(request.getMiddleName());
		passenger.setEmail(request.getEmail());
		passenger.setPhone(request.getPhone());
		passengerRepo.save(passenger);
		
		long flightId = request.getFlightId();
		Optional<Airasia> findById = flightRepo.findById(flightId);
		Airasia airasia = findById.get();
		
		Reservation reservation = new Reservation();
		reservation.setAirasia(airasia);
		reservation.setPassenger(passenger);
		reservation.setCheckedIn(false);
		reservation.setNumberOfBags(0);
		String filepath = "D:\\jan_code\\flight_airasia\\tickets\\reservation"+reservation.getId()+".pdf";
		reservationRepo.save(reservation);
		
		pdfGenertor.generateItinerary(reservation, filepath);
	    emailUtil.sendItinerary(passenger.getEmail(), filepath);
		return reservation;
		
	}

}

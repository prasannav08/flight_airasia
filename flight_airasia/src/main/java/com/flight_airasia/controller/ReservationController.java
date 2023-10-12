package com.flight_airasia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flight_airasia.dto.ReservationRequest;
import com.flight_airasia.entity.Reservation;
import com.flight_airasia.service.ReservationService;
import com.flight_airasia.utilities.PDFgenerator;


@Controller
public class ReservationController {
	@Autowired
   private ReservationService reservationService;
	
	@RequestMapping("/confirmReservation")
	public String confirmReservation(ReservationRequest request, ModelMap modelMap) {
		Reservation reservationId = reservationService.bookFlight(request);
		modelMap.addAttribute("reservationId", reservationId.getId());
		PDFgenerator pdf = new PDFgenerator();
		return "confirmReservation";
		}

}

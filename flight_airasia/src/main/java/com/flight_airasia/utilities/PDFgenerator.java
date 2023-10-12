package com.flight_airasia.utilities;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

import com.flight_airasia.entity.Reservation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PDFgenerator {

    public void generateItinerary(Reservation reservation, String filePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();
            document.add(generateTable(reservation));
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    // iText allows adding metadata to the PDF, which can be viewed in Adobe Reader
    // under File -> Properties
    private PdfPTable generateTable(Reservation reservation) {

        PdfPTable table = new PdfPTable(2);
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Flight Itinerary"));
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Flight Details"));
        cell.setColspan(2);
        table.addCell(cell);

        table.addCell("Departure City");
        table.addCell(reservation.getAirasia().getDepartureCity());

        table.addCell("Arrival City");
        table.addCell(reservation.getAirasia().getArrivalCity());

        table.addCell("Flight Number");
        table.addCell(reservation.getAirasia().getFlightNumber());

        table.addCell("Operating Airline");
        table.addCell(reservation.getAirasia().getOperatingAirlines());

        table.addCell("Departure Date");
        table.addCell(reservation.getAirasia().getDateOfDeparture().toString());

        table.addCell("Departure time");
        table.addCell(reservation.getAirasia().getEstimatedDepartureTime().toString());

        cell = new PdfPCell(new Phrase("Passenger Details"));
        cell.setColspan(2);
        table.addCell(cell);

        table.addCell("First Name");
        table.addCell(reservation.getPassenger().getFirstName());

        table.addCell("Last Name");
        table.addCell(reservation.getPassenger().getLastName());

        table.addCell("Middle Name");
        table.addCell(reservation.getPassenger().getMiddleName());

        table.addCell("Email");
        table.addCell(reservation.getPassenger().getEmail());

        table.addCell("Phone No");
        table.addCell(reservation.getPassenger().getPhone());

        return table; // Added return statement
    }
}
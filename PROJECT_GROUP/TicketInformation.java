import java.util.*;
import java.time.LocalDate;

public class TicketInformation {
    private String ticketId;
    private String rideName;
    private double ticketPrice;
    private LocalDate purchaseDate;

    public TicketInformation(String ticketId, String rideName, double ticketPrice, LocalDate purchaseDate) {
        this.ticketId = ticketId;
        this.rideName = rideName;
        this.ticketPrice = ticketPrice;
        this.purchaseDate = purchaseDate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getRideName() {
        return rideName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getPurchaseDate() {
        return purchaseDate.toString(); //Convert LocalDate to String for display
    }

    public String getToString() {
        return "TICKET INFORMATION:\n" +
        "Ticket ID = " + ticketId + "\n" +
        "Ride Name = " + rideName + "\n" +
        "Ticket Price = " + ticketPrice + "\n" +
        "Purchase Date = " + getPurchaseDate();
    }   
}
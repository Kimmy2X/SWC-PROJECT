import java.util.*;
import java.text.DecimalFormat;

public class CustomerInformation {
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private int custId;
    private String custName;
    private int assignedCounter;
    private int ticketQuantity;
    private List<TicketInformation> purchasedTickets;

    // No-args constructor
    public CustomerInformation() {
    }

    // Normal constructor
    public CustomerInformation(int custId, String custName, int assignedCounter, int ticketQuantity, List<TicketInformation> purchasedTickets) {
        this.custId = custId;
        this.custName = custName;
        this.assignedCounter = assignedCounter;
        this.ticketQuantity = ticketQuantity;
        this.purchasedTickets = purchasedTickets;
    }

    // Getters
    public int getCustId() {
        return custId;
    }

    public String getCustName() {
        return custName;
    }

    public int getAssignedCounter() {
        return assignedCounter;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public List<TicketInformation> getPurchasedTickets() {
        return purchasedTickets;
    }

    public double totalPrice() {
        double total = 0.0;
        for (TicketInformation tickets : purchasedTickets) {
            total += tickets.getTicketPrice();
        }
        return total * ticketQuantity;
    }

    // Setters
    public void setCustId(int custId) {
        this.custId = custId;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public void setAssignedCounter(int assignedCounter) {
        this.assignedCounter = assignedCounter;
    }  

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public void setPurchasedTickets(List<TicketInformation> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("======================================\n");
        sb.append("Customer ID: ").append(custId).append("\n");
        sb.append("Customer Name: ").append(custName).append("\n");
        sb.append("Counter Paid: ").append(assignedCounter).append("\n");
        sb.append("Ticket Quantity: ").append(ticketQuantity).append("\n");
        sb.append("--------------------------------------\n");

        for (TicketInformation ticket : purchasedTickets) {
            sb.append(ticket.getToString()).append("\n");
        }
        
        sb.append("--------------------------------------\n");
        sb.append("Total Price: ").append(decimalFormat.format(totalPrice())).append("\n");
        sb.append("======================================\n\n");

        return sb.toString();
    }

}
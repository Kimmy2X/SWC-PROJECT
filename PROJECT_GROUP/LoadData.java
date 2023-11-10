import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoadData {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LinkedList<CustomerInformation> loadData() {
        try (BufferedReader in = new BufferedReader(new FileReader("customerList.txt"))) {
            // Create LinkedList for CustomerInformation objects
            LinkedList<CustomerInformation> customerList = new LinkedList<>();

            String inline;

            while ((inline = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inline, "\\|");

                if (st.hasMoreTokens()) {
                    int custId = Integer.parseInt(st.nextToken().trim());
                    String custName = st.nextToken().trim();
                    int assignedCounter = Integer.parseInt(st.nextToken().trim());
                    int ticketQuantity = Integer.parseInt(st.nextToken().trim());
                    List<TicketInformation> purchasedTickets = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        String ticketId = st.nextToken().trim();
                        String rideName = st.nextToken().trim();
                        double ticketPrice = Double.parseDouble(st.nextToken().trim());
                        String purchaseDateString = st.nextToken().trim();
                        if (purchaseDateString.endsWith(";")) {
                            purchaseDateString = purchaseDateString.substring(0, purchaseDateString.length() - 1);
                        }

                        LocalDate purchaseDate = LocalDate.parse(purchaseDateString, DATE_FORMATTER);
                        // Create a TicketInformation object and add it to the list
                        TicketInformation ticket = new TicketInformation(ticketId, rideName, ticketPrice, purchaseDate);
                        purchasedTickets.add(ticket);
                    }
                    CustomerInformation cust = new CustomerInformation(custId, custName, assignedCounter, ticketQuantity, purchasedTickets);
                    customerList.add(cust);
                }
            }
            return customerList;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number format exception: " + e.getMessage());
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Missing element in string tokenization: " + e.getMessage());
        }
        return null;
    }

    public static List<CustomerInformation> loadFirstNCustomers(String filename, int n) {
        List<CustomerInformation> firstNCustomers = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String inline;
            int count = 0;

            while ((inline = in.readLine()) != null && count < n) {
                StringTokenizer st = new StringTokenizer(inline, "\\|");

                if (st.hasMoreTokens()) {
                    int custId = Integer.parseInt(st.nextToken().trim());
                    String custName = st.nextToken().trim();
                    int assignedCounter = Integer.parseInt(st.nextToken().trim());
                    int ticketQuantity = Integer.parseInt(st.nextToken().trim());
                    List<TicketInformation> purchasedTickets = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        String ticketId = st.nextToken().trim();
                        String rideName = st.nextToken().trim();
                        double ticketPrice = Double.parseDouble(st.nextToken().trim());
                        String purchaseDateString = st.nextToken().trim();
                        if (purchaseDateString.endsWith(";")) {
                            purchaseDateString = purchaseDateString.substring(0, purchaseDateString.length() - 1);
                        }

                        LocalDate purchaseDate = LocalDate.parse(purchaseDateString, DATE_FORMATTER);
                        // Create a TicketInformation object and add it to the list
                        TicketInformation ticket = new TicketInformation(ticketId, rideName, ticketPrice, purchaseDate);
                        purchasedTickets.add(ticket);
                    }
                    CustomerInformation cust = new CustomerInformation(custId, custName, assignedCounter, ticketQuantity, purchasedTickets);
                    firstNCustomers.add(cust);
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number format exception: " + e.getMessage());
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Missing element in string tokenization: " + e.getMessage());
        }

        return firstNCustomers;
    }

    public static List<CustomerInformation> loadRemainingCustomers(String filename, int n) {
        List<CustomerInformation> remainingCustomers = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String inline;
            int count = 0;

            while ((inline = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inline, "\\|");

                if (st.hasMoreTokens()) {
                    int custId = Integer.parseInt(st.nextToken().trim());
                    String custName = st.nextToken().trim();
                    int assignedCounter = Integer.parseInt(st.nextToken().trim());
                    int ticketQuantity = Integer.parseInt(st.nextToken().trim());
                    List<TicketInformation> purchasedTickets = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        String ticketId = st.nextToken().trim();
                        String rideName = st.nextToken().trim();
                        double ticketPrice = Double.parseDouble(st.nextToken().trim());
                        String purchaseDateString = st.nextToken().trim();
                        if (purchaseDateString.endsWith(";")) {
                            purchaseDateString = purchaseDateString.substring(0, purchaseDateString.length() - 1);
                        }

                        LocalDate purchaseDate = LocalDate.parse(purchaseDateString, DATE_FORMATTER);
                        // Create a TicketInformation object and add it to the list
                        TicketInformation ticket = new TicketInformation(ticketId, rideName, ticketPrice, purchaseDate);
                        purchasedTickets.add(ticket);
                    }
                    CustomerInformation cust = new CustomerInformation(custId, custName, assignedCounter, ticketQuantity, purchasedTickets);
                    if (count >= n) {
                        remainingCustomers.add(cust);
                    }
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Number format exception: " + e.getMessage());
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Missing element in string tokenization: " + e.getMessage());
        }

        return remainingCustomers;
    }

    // Rest of the code...
}

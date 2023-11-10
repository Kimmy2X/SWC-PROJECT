import javax.swing.*;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Queue;
import java.util.Stack;
import java.awt.GridLayout;

public class Gui implements ActionListener {
    private JFrame frame;
    private JLabel queueStatusLabel;
    private JButton btnCounter1, btnCounter2, btnCounter3;
    private int totalCustomer;
    private Queue<CustomerInformation> qCounter1, qCounter2, qCounter3;
    private LinkedList<CustomerInformation> customerList;
    private Stack<CustomerInformation> completedStack;
    private JTextArea textAreaQueue;
    private JButton btnAddNewCustomer, btnLoadData, btnDisplayPaidCustomers;
    private int customersProcessedAtCounter1 = 0;
    private int customersProcessedAtCounter2 = 0;
    private int customersProcessedAtCounter3 = 0;
    private PaidCustomerDisplay paidCustomerDisplay = new PaidCustomerDisplay();

    public Gui(LinkedList<CustomerInformation> customerList, Queue<CustomerInformation> qCounter1, Queue<CustomerInformation> qCounter2, Queue<CustomerInformation> qCounter3,
    int totalCustomer, Stack<CustomerInformation> completedStack) {
        this.customerList = customerList;
        this.totalCustomer = totalCustomer;
        this.qCounter1 = qCounter1;
        this.qCounter2 = qCounter2;
        this.qCounter3 = qCounter3;
        this.completedStack = completedStack;

        frame = new JFrame("COUNTER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(3, 1)); 

        // Label for Queue Status
        queueStatusLabel = new JLabel("Customer In Queue: " + totalCustomer);
        queueStatusLabel.setHorizontalAlignment(JLabel.CENTER); 
        contentPane.add(queueStatusLabel); // Add it to the content pane

        // Panel for Counter Buttons
        JPanel counterPanel = new JPanel(new GridLayout(1, 3));
        btnCounter1 = new JButton("COUNTER 1");
        btnCounter2 = new JButton("COUNTER 2");
        btnCounter3 = new JButton("COUNTER 3");
        counterPanel.add(btnCounter1);
        counterPanel.add(btnCounter2);
        counterPanel.add(btnCounter3);

        // Add the counter panel to the content pane
        contentPane.add(counterPanel);

        // Panel for Other Buttons
        JPanel otherButtonsPanel = new JPanel(new GridLayout(1, 3));
        btnLoadData = new JButton("LOAD DATA");
        btnAddNewCustomer = new JButton("Add New Customer");
        btnDisplayPaidCustomers = new JButton("Display Paid Customers");
        otherButtonsPanel.add(btnAddNewCustomer);
        otherButtonsPanel.add(btnDisplayPaidCustomers);
        otherButtonsPanel.add(btnLoadData);

        // Add the other buttons panel to the content pane
        contentPane.add(otherButtonsPanel);

        // Adding action listeners for the buttons
        btnCounter1.addActionListener(this);
        btnCounter2.addActionListener(this);
        btnCounter3.addActionListener(this);
        btnLoadData.addActionListener(this);
        btnDisplayPaidCustomers.addActionListener(this);
        btnAddNewCustomer.addActionListener(this);

        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.pack(); // Pack the frame to preferred sizes of its components
        frame.setVisible(true); // Make the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoadData) {
            List<CustomerInformation> loadedCustomers = LoadData.loadData();
            if (loadedCustomers != null && !loadedCustomers.isEmpty()) {
                customerList.clear();
                customerList.addAll(loadedCustomers);

                // Calculate the total remaining customers from the customerList.txt file
                totalCustomer = customerList.size();

                // Update the queue status label with the total remaining customers
                updateQueueStatusLabel();
            }
        }

        if (e.getSource() == btnCounter1) {
            if (!qCounter1.isEmpty()) {
                showQueueFrame(e.getSource()); //call the showQueueFrame class to execetue
            }
        }

        if (e.getSource() == btnCounter2) {
            if (!qCounter2.isEmpty()) {
                showQueueFrame(e.getSource()); //call the showQueueFrame class to execetue
            }
        }

        if (e.getSource() == btnCounter3) {
            if (!qCounter3.isEmpty()) {
                showQueueFrame(e.getSource()); //call the showQueueFrame class to execetue
            }
        }

        if (e.getSource() == btnAddNewCustomer) {
            // Remove the first 15 customers from the customerList.txt file and assign them to counters
            assignCustomersToCountersFromFile();

            // Display the newly added customer
            JOptionPane.showMessageDialog(null, "New customer added");

            // Update the queue status label and any other relevant UI elements
            updateQueueStatusLabel();
        }

        if (e.getSource() == btnDisplayPaidCustomers) {
            // Show the paid customer display
            paidCustomerDisplay.showPaidCustomers();
        }

        updateQueueStatusLabel();
    }

    private void showQueueFrame(Object source) {
        Queue<CustomerInformation> selectedQueue = (source == btnCounter1) ? qCounter1 :
            (source == btnCounter2) ? qCounter2 : qCounter3;
        int counterNumber = (source == btnCounter1) ? 1 : (source == btnCounter2) ? 2 : 3;
        
        // Frame for the Counter to display the first 5 customers
        JFrame queueFrame = new JFrame("Counter " + counterNumber + " Queue");
        queueFrame.setSize(300, 300); //(width, length)
        queueFrame.setLayout(new BorderLayout());

        textAreaQueue = new JTextArea(10, 20); //(row, column)
        textAreaQueue.setEditable(false); //so that the user can't edit the content of the text
        JScrollPane scrollPaneQueue = new JScrollPane(textAreaQueue);
        
        //execute the Make Payment button
        JButton btnMakePayment = new JButton("Make Payment");
        btnMakePayment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // Get the selected customer from the queue based on the source button
                    CustomerInformation selectedCustomer = null;
                    if (source == btnCounter1) {
                        selectedCustomer = qCounter1.poll(); //removes and returns the element at the head of the queue.
                    } else if (source == btnCounter2) {
                        selectedCustomer = qCounter2.poll();
                    } else if (source == btnCounter3) {
                        selectedCustomer = qCounter3.poll();
                    }

                    if (selectedCustomer != null) {
                        // Write the selected customer's information to the "paidCustomer.txt" file
                        writeCustomerToFile(selectedCustomer, "paidCustomer.txt");

                        // Update the paid customers area in the PaidCustomerDisplay
                        paidCustomerDisplay.updatePaidCustomersArea();

                        // Update any other necessary data structures and UI elements
                        completedStack.push(selectedCustomer);

                        // Display a message indicating that payment is successful
                        JOptionPane.showMessageDialog(null, "Payment is successful");

                        // Close the queue frame
                        queueFrame.dispose();

                        // Update the queue status label and any other relevant UI elements
                        updateQueueStatusLabel();

                        // Check if there are more customers in the queue and process them if needed
                        assignCustomersToCountersFromFile();
                    }
                }
            });

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (CustomerInformation customer : selectedQueue) {
            if (count >= 5) {
                break;
            }
            sb.append("Customers Name: ").append(customer.getCustName()).append("\n");
            count++;
        }
        textAreaQueue.setText(sb.toString()); // Set the text with the StringBuilder content
        queueFrame.add(scrollPaneQueue, BorderLayout.CENTER);
        queueFrame.add(btnMakePayment, BorderLayout.SOUTH);

        textAreaQueue.revalidate();
        textAreaQueue.repaint();

        queueFrame.setVisible(true);
    }

    private void assignCustomersToCountersFromFile() {
        List<CustomerInformation> customersFromFile = LoadData.loadFirstNCustomers("customerList.txt", 15);
        if (customersFromFile.isEmpty()) {
            return; // No customers in the file
        }

        for (CustomerInformation customer : customersFromFile) {
            int tickets = customer.getTicketQuantity();

            if (tickets <= 5) {
                if (customersProcessedAtCounter1 <= customersProcessedAtCounter2) {
                    qCounter1.add(customer);
                    customer.setAssignedCounter(1);
                    customersProcessedAtCounter1++;
                } else {
                    qCounter2.add(customer);
                    customer.setAssignedCounter(2);
                    customersProcessedAtCounter2++;
                }
            } else {
                qCounter3.add(customer);
                customer.setAssignedCounter(3);
            }

            completedStack.push(customer);
        }

        // Remove the assigned customers from the customerList.txt file
        List<CustomerInformation> remainingCustomers = LoadData.loadRemainingCustomers("customerList.txt", 15);
        updateCustomerListFile(remainingCustomers);
    }

    private void updateQueueStatusLabel() {
        // Update the queue status label whenever the queue size changes
        queueStatusLabel.setText("Customer In Queue: " + totalCustomer);
    }

    private void updateCustomerListFile(List<CustomerInformation> allCustomers) {
        // Update the customerList.txt file with the provided list of customers
        try (FileWriter writer = new FileWriter("customerList.txt", false)) {
            for (CustomerInformation customer : allCustomers) {
                // Write customer data
                writer.write(String.format("%d|%s|%d|%d|", customer.getCustId(), customer.getCustName(),
                        customer.getAssignedCounter(), customer.getTicketQuantity()));

                // Iterate through the customer's purchased tickets
                List<TicketInformation> purchasedTickets = customer.getPurchasedTickets();
                if (purchasedTickets != null && !purchasedTickets.isEmpty()) {
                    for (TicketInformation ticket : purchasedTickets) {
                        writer.write(String.format("%s|%s|%.2f|%s;", ticket.getTicketId(), ticket.getRideName(),
                                ticket.getTicketPrice(), ticket.getPurchaseDate().toString()));
                    }
                }

                // Write a newline character to separate customer records
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCustomerToFile(CustomerInformation customer, String filename) {
        // write the customer information to the paidCustomer.txt
        try (FileWriter writer = new FileWriter("paidCustomer.txt", true)) {
            // Format and write the customer data
            writer.write(String.format("Customer ID: %d\n", customer.getCustId()));
            writer.write(String.format("Customer Name: %s\n", customer.getCustName()));
            writer.write(String.format("Assigned Counter: %d\n", customer.getAssignedCounter()));
            writer.write(String.format("Ticket Quantity: %d\n", customer.getTicketQuantity()));

            // Iterate through the customer's purchased tickets and write them
            List<TicketInformation> purchasedTickets = customer.getPurchasedTickets();
            if (purchasedTickets != null && !purchasedTickets.isEmpty()) {
                writer.write("Purchased Tickets:\n");
                for (TicketInformation ticket : purchasedTickets) {
                    writer.write(String.format("Ticket ID: %s\n", ticket.getTicketId()));
                    writer.write(String.format("Ride Name: %s\n", ticket.getRideName()));
                    writer.write(String.format("Ticket Price: %.2f\n", ticket.getTicketPrice()));
                    writer.write(String.format("Purchase Date: %s\n", ticket.getPurchaseDate().toString()));
                    writer.write("\n"); // Separate each ticket
                }
            }

            writer.write("========================================\n"); // Separate customer records
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGui() {
        frame.setVisible(true);
    }
}

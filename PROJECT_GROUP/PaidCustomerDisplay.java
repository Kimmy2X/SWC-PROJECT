import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PaidCustomerDisplay {
    private JFrame frame;
    private JTextArea paidCustomersArea; 

    public PaidCustomerDisplay() {
        frame = new JFrame("Paid Customers");
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        paidCustomersArea = new JTextArea();
        paidCustomersArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(paidCustomersArea);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void showPaidCustomers() {
        updatePaidCustomersArea(); // Update the text area before showing
        frame.setVisible(true);
    }

    public void updatePaidCustomersArea() {
        paidCustomersArea.setText(""); // Clear previous data
        try (BufferedReader reader = new BufferedReader(new FileReader("paidCustomer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                paidCustomersArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

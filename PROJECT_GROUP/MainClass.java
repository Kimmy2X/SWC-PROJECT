import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class MainClass {
    public static void main(String[] args) {
        LinkedList<CustomerInformation> customerList = LoadData.loadData();
        Queue<CustomerInformation> qCounter1 = new LinkedList<>(); 
        Queue<CustomerInformation> qCounter2 = new LinkedList<>(); 
        Queue<CustomerInformation> qCounter3 = new LinkedList<>(); 
        Stack<CustomerInformation> completedStack = new Stack<>();

        int totalCustomer = customerList.size(); // Set the initial total customer count

        // Create an instance of the Gui class
        Gui gui = new Gui(customerList, qCounter1, qCounter2, qCounter3, totalCustomer, completedStack);
        
        // Call the showGui method to display the GUI
        gui.showGui();
    }
}

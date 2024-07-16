package ir.ac.kntu;

import ir.ac.kntu.customerthread.CustomerThread;
import ir.ac.kntu.db.AnswerDB;
import ir.ac.kntu.db.CustomerDB;
import ir.ac.kntu.db.SimCardDB;
import ir.ac.kntu.message.MessageOption;
import ir.ac.kntu.user.customer.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SingUpPageController {

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField pNumber;

    @FXML
    private TextField iDocu;

    @FXML
    private Label label;

    private Stage stage;
    private Scene scene;

    public void submit(ActionEvent event) throws IOException {
        boolean check = true;
        String firstName = fName.getText();
        String lastName = lName.getText();
        String phoneNumber = pNumber.getText();
        String iDocument = iDocu.getText();

        for (Customer customer : CustomerDB.getCustomers()) {
            if (customer.getPhoneNumber().equals(phoneNumber) || customer.getIDocument().equals(iDocument)) {
                label.setText("*invalid input detected");
                check = false;
            }
        }
        if(check) {
            Customer customer = new Customer(firstName, lastName, "0000", iDocument, phoneNumber, SimCardDB.getPhones());
            String request = "want to have account";
            Message message = new Message(phoneNumber, request, MessageOption.REPORT);
            customer.getMessageDB().addMessage(message);
            AnswerDB.add(message);
            CustomerDB.addCustomer(customer);
            CustomerThread customerThread = new CustomerThread();
            customerThread.setInfo(customer);
            Thread thread = new Thread(customerThread);
            thread.start();
            label.setText("the customer is added!!");
            back(event);
        }

    }

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("LoginStyle.css");
        stage.setScene(scene);
        stage.show();
    }
}

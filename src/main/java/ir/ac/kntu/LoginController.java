package ir.ac.kntu;

import ir.ac.kntu.db.CustomerDB;
import ir.ac.kntu.user.customer.Customer;
import ir.ac.kntu.user.customer.State;
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

public class LoginController {
    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField iDocument;

    @FXML
    private Label errorLabel;

    private Customer cust = null;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws IOException {
        String userPhoneNumber = phoneNumber.getText();
        String userIDocument = iDocument.getText();
        for(Customer customer : CustomerDB.getCustomers()) {
            if (customer.getIDocument().equals(userIDocument) && customer.getPhoneNumber().equals(userPhoneNumber)) {
                cust = customer;
                break;
            }
        }
        if (cust != null) {
            if(cust.getState() == State.ACCEPTED) {
                showDashboardPage(event);
            } else if(cust.getState() == State.IN_PROGRESSING) {
                errorLabel.setText("in progressing!!");
            } else if(cust.getState() == State.REJECT) {
                CustomerDB.removeCustomer(cust);
                errorLabel.setText(cust.getMessageDB().getMessageList().get(1).toString());
            }
        } else {
            errorLabel.setText("*phone number or SSN incorrect");
        }
    }

    public void signup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("LoginStyle.css");
        stage.setScene(scene);
        stage.show();
    }

    private void showDashboardPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        root = loader.load();

        MainPageController mainPageController = loader.getController();
        mainPageController.setCustomer(cust);

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

package ir.ac.kntu;

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

public class IncreaseBalanceController {

    private Customer customer;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField textField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void increaseCredit(ActionEvent event) {
        try {
            long inputMoney = Long.parseLong(textField.getText());
            customer.getAccount().increaseCredit(inputMoney);
            errorLabel.setText("successfully added!!");
        } catch (Exception e) {
            errorLabel.setText("*invalid input detected");
        }
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        root = loader.load();
        MainPageController mainPageController = loader.getController();
        mainPageController.setCustomer(customer);
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("Dashboard.css");
        stage.setScene(scene);
        stage.show();
    }

}

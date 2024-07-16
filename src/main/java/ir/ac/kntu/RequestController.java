package ir.ac.kntu;

import ir.ac.kntu.db.AnswerDB;
import ir.ac.kntu.message.MessageOption;
import ir.ac.kntu.user.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class RequestController {
    private Customer customer;

    @FXML
    private TextArea requestField;

    @FXML
    private TableView<Message> tableView;

    @FXML
    private TableColumn<Message, MessageOption> matter;
    @FXML
    private TableColumn<Message, String> request;

    @FXML
    private RadioButton report;
    @FXML
    private RadioButton contact;
    @FXML
    private RadioButton transfer;
    @FXML
    private RadioButton setting;

    @FXML
    private Label label;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        setTable();
    }

    private void setTable() {
        matter.setCellValueFactory(new PropertyValueFactory<>("messageOption"));
        request.setCellValueFactory(new PropertyValueFactory<>("message"));
        tableView.getColumns().add(matter);
        tableView.getColumns().add(request);
        ObservableList<Message> data = FXCollections.observableArrayList();

        for(Message message : customer.getMessageDB().getMessageList()) {
            data.add(new Message(message));
        }
        tableView.setItems(data);
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

    public void addRequest(ActionEvent event) throws IOException {
        String message = requestField.getText();
        Message newMessage = null;
        if (contact.isSelected()) {
            newMessage = new Message(customer.getPhoneNumber(), message, MessageOption.CONTACT);
        } else if (transfer.isSelected()) {
            newMessage = new Message(customer.getPhoneNumber(), message, MessageOption.TRANSFER);
        } else if (setting.isSelected()) {
            newMessage = new Message(customer.getPhoneNumber(), message, MessageOption.SETTING);
        } else if (report.isSelected()) {
            newMessage = new Message(customer.getPhoneNumber(), message, MessageOption.REPORT);
        }
        if (newMessage == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("chose request message!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        } else {
            newMessage.setMessageAnswer("Our colleagues will contact you");
            customer.getMessageDB().addMessage(newMessage);
            AnswerDB.add(newMessage);
            label.setText("successfully added");
            customer.getMessageDB().printMessage();
            setTable();
        }
    }
}

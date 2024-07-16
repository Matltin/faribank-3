package ir.ac.kntu;

import ir.ac.kntu.db.CustomerDB;
import ir.ac.kntu.user.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable{

    private Customer customer;

    @FXML
    private TableView<ContactPerson> tableView;

    @FXML
    private TableColumn<ContactPerson, String> nameColumn;
    @FXML
    private TableColumn<ContactPerson, String> lastNameColumn;
    @FXML
    private TableColumn<ContactPerson, String> phoneColumn;

    @FXML
    private TextField fName;
    @FXML
    private TextField lName;
    @FXML
    private TextField phone;

    @FXML
    private Label label;

    private ContactPerson contactPerson;
    private Alert alert;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        addContact();
    }

    public void add(ActionEvent event) {
        boolean check = false;
        String firstName = fName.getText();
        String lastName = lName.getText();
        String phoneNumber = phone.getText();
        if (customer.getContactPerson().findPerson(phoneNumber) != null) {
            alert.setTitle("ERROR");
            alert.setContentText("The contact is already exist");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        for(Customer cust : CustomerDB.getCustomers()) {
            if(cust.getPhoneNumber().equals(phoneNumber)) {
                check = true;
                break;
            }
        }
        if(check) {
            customer.addContactPerson(firstName, lastName, phoneNumber);
            label.setText("successfully added");
            addContact();
        } else {
            alert.setTitle("ERROR");
            alert.setContentText("there is no customer with this phone number!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    public void remove(ActionEvent event) {
        if(contactPerson == null) {
            alert.setTitle("ERROR");
            alert.setContentText("Select contact");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        customer.getContactPerson().removePerson(contactPerson);
        label.setText("successfully removed");
        addContact();
    }

    public void changeContact(ActionEvent event) {
        if(contactPerson == null) {
            alert.setTitle("ERROR");
            alert.setContentText("Select contact");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        String firstName = fName.getText();
        String lastName = lName.getText();
        editContact(firstName, lastName);
        contactPerson.setFirstName(firstName);
        contactPerson.setLastName(lastName);
        label.setText("successfully edited");
        addContact();
    }

    private void editContact(String firstName, String lastName) {
        ContactPerson contact = customer.getContactPerson().findPerson(contactPerson.getPhoneNumber());
        if(contact != null) {
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
        }
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        root = loader.load();

        MainPageController mainPageController = loader.getController();
        mainPageController.setCustomer(customer);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void addContact() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        ObservableList<ContactPerson> data = FXCollections.observableArrayList();

        for(ContactPerson contactPerson : customer.getContactPerson().getContactPerson()) {
            data.add(new ContactPerson(contactPerson));
        }
        tableView.setItems(data);
        tableView.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) { // Double-click
                ContactPerson selectedPerson = tableView.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    System.out.println(selectedPerson);
                    contactPerson = selectedPerson;
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.ERROR);
    }
}

package ir.ac.kntu;

import ir.ac.kntu.db.CustomerDB;
import ir.ac.kntu.user.customer.Customer;
import ir.ac.kntu.user.customer.State;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TransferController implements Initializable {

    private Customer customer;

    @FXML
    private AnchorPane chosePage;
    @FXML
    private AnchorPane transferPage;

    @FXML
    private TableView<ContactPerson> tableViewContact;

    @FXML
    private TableColumn<ContactPerson, String> nameColumnContact;
    @FXML
    private TableColumn<ContactPerson, String> lastNameColumnContact;
    @FXML
    private TableColumn<ContactPerson, String> phoneColumnContact;
    @FXML
    private TableColumn<ContactPerson, String> accountNumberContact;

    @FXML
    private TableView<ContactPerson> tableViewTransfer;

    @FXML
    private TableColumn<ContactPerson, String> nameColumnTransfer;
    @FXML
    private TableColumn<ContactPerson, String> lastNameColumnTransfer;
    @FXML
    private TableColumn<ContactPerson, String> phoneColumnTransfer;
    @FXML
    private TableColumn<ContactPerson, String> accountNumberTransfer;

    @FXML
    private TextField number;
    @FXML
    private TextField money;

    @FXML
    private Label label;
    @FXML
    private Label errorLabel;

    private boolean card = false;
    private boolean account = false;
    private ContactPerson contactPerson;
    private Alert alert;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        addContact();
        addTransfer();
    }

    public void showAccountNumber(ActionEvent event) {
        changeAnchroPane();
        label.setText("Account number");
        account = true;
        card = false;
    }

    public void showCardNumber(ActionEvent event) {
        changeAnchroPane();
        label.setText("Card number");
        account = false;
        card = true;
    }

    public void transferIt(ActionEvent event) {
        if(account) {
            String accountNumber = number.getText();
            transfer(accountNumber);
        } else if(card) {
            String cardNumber = number.getText();
            String accountNumber = CustomerDB.getAccountNumber(cardNumber);
            transfer(accountNumber);
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

    private void transfer(String accountNumber) {
        Customer cust = CustomerDB.findCustomerByAccountNO(accountNumber);
        if(contactPerson != null) {
            cust = CustomerDB.findCustomerByAccountNO(contactPerson.getAccountNumber());
            accountNumber = contactPerson.getAccountNumber();
        }
        if (cust != null) {
            if (!isAcceptedCustomer(cust)) {
                alert.setTitle("ERROR");
                alert.setContentText("There is no customer");
                if(alert.showAndWait().get() == ButtonType.YES) {
                    alert.close();
                }
                return;
            }
            long inputMoney = Long.parseLong(money.getText());
            boolean check = customer.getAccount().transferFari(inputMoney, accountNumber);
            if (!check) {
                return;
            }
            ContactPerson contactPerson1 = new ContactPerson(cust.getFirstName(), cust.getLastName(), cust.getPhoneNumber(), cust.getAccount().getAccountNO());
            customer.getRecentTransaction().addContactPersonList(contactPerson1);
            errorLabel.setText("Successfully transferred");
        } else {
            alert.setTitle("ERROR");
            alert.setContentText("there is no customer with this account number!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        }
    }


    private void changeAnchroPane() {
        chosePage.setVisible(false);
        transferPage.setVisible(true);
    }

    private void addTransfer() {
        nameColumnTransfer.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnTransfer.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumnTransfer.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        accountNumberTransfer.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

        ObservableList<ContactPerson> data = FXCollections.observableArrayList();

        for(ContactPerson contactPerson : customer.getRecentTransaction().getContactPersonList()) {
            data.add(new ContactPerson(contactPerson));
        }

        tableViewTransfer.setItems(data);
        tableViewTransfer.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) { // Double-click
                ContactPerson selectedPerson = tableViewTransfer.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    System.out.println(selectedPerson);
                    contactPerson = selectedPerson;
                }
            }
        });
    }

    private void addContact() {
        nameColumnContact.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnContact.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumnContact.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        accountNumberContact.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

        ObservableList<ContactPerson> data = FXCollections.observableArrayList();

        for(ContactPerson contactPerson : customer.getContactPerson().getContactPerson()) {
            data.add(new ContactPerson(contactPerson));
        }

        tableViewContact.setItems(data);
        tableViewContact.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) { // Double-click
                ContactPerson selectedPerson = tableViewContact.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                    System.out.println(selectedPerson);
                    contactPerson = selectedPerson;
                }
            }
        });
    }

    private boolean isAcceptedCustomer(Customer customer) {
        return customer.getState() == State.ACCEPTED;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chosePage.setVisible(true);
        transferPage.setVisible(false);
        alert = new Alert(Alert.AlertType.ERROR);
    }
}

package ir.ac.kntu;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    private Customer customer;

    @FXML
    private TableView<Transaction> tableView;

    @FXML
    private TableColumn<Transaction, String> dstColumn;
    @FXML
    private TableColumn<Transaction, Long> moneyColumn;
    @FXML
    private TableColumn<Transaction, String> dateColumn;

    @FXML
    private Label cardNumber;
    @FXML
    private Label accountNumber;
    @FXML
    private Label balance;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        cardNumber.setText(customer.getAccount().getCard().getCardNumber());
        accountNumber.setText(customer.getAccount().getAccountNO());
        balance.setText(String.valueOf(customer.getAccount().getBalance()));
        addTransaction();
    }

    public void contact(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("contacts.fxml"));
        root = loader.load();

        ContactController contactController = loader.getController();
        contactController.setCustomer(customer);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loan(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loan-request.fxml"));
        root = loader.load();

        LoanController loanController = loader.getController();
        loanController.setCustomer(customer);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void transfer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transfers.fxml"));
        root = loader.load();

        TransferController transferController = loader.getController();
        transferController.setCustomer(customer);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void increase(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-fund.fxml"));
        root = loader.load();
        IncreaseBalanceController increaseBalanceController = loader.getController();
        increaseBalanceController.setCustomer(customer);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void box(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
        root = loader.load();
        BoxController boxController = loader.getController();
        boxController.setCustomer(customer);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void support(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("support.fxml"));
        root = loader.load();
        RequestController requestController = loader.getController();
        requestController.setCustomer(customer);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("LoginStyle.css");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void addTransaction() {
        dstColumn.setCellValueFactory(new PropertyValueFactory<>("dstAccNO"));
        moneyColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("formatDate"));

        ObservableList<Transaction> data = FXCollections.observableArrayList();

        for(Transaction transaction : customer.getAccount().getTransactionDB().getTransactions()) {
            data.add(new Transaction(transaction));
        }
        tableView.setItems(data);
    }
}

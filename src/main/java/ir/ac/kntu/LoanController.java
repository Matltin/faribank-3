package ir.ac.kntu;

import ir.ac.kntu.loan.LoanType;
import ir.ac.kntu.loanthread.LoanThread;
import ir.ac.kntu.transaction.TransactionType;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoanController implements Initializable {
    private Customer customer;

    @FXML
    private AnchorPane showLoanPage;
    @FXML
    private AnchorPane requestLoanPage;

    @FXML
    private TableView<Loan> tableViewLoan;

    @FXML
    private TableColumn<Loan, String> amountColumnLoan;
    @FXML
    private TableColumn<Loan, String> installmentsColumnLoan;
    @FXML
    private TableColumn<Loan, String> initiationDateColumnLoan;

    @FXML
    private TableView<Loan> tableViewLoanRequest;

    @FXML
    private TableColumn<Loan, String> amountColumnLoanRequest;
    @FXML
    private TableColumn<Loan, String> installmentsColumnLoanRequest;
    @FXML
    private TableColumn<Loan, String> statusColumnRequest;

    @FXML
    private TextField amount;
    @FXML
    private TextField duration;

    @FXML
    private Label installmentNo;
    @FXML
    private Label installmentAmount;
    @FXML
    private Label errorLabelLoan;
    @FXML
    private Label errorLabelRequestLoan;

    private boolean show = false;
    private boolean request = false;
    private Loan selectedLoan;
    private Alert alert;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        addLoan();
        addLoanRequest();
    }

    public void activeLoan(ActionEvent event) {
        showLoanPage.setVisible(true);
        requestLoanPage.setVisible(false);
    }

    public void requestLoan(ActionEvent event){
        showLoanPage.setVisible(false);
        requestLoanPage.setVisible(true);
    }

    public void pay(ActionEvent event) {
        if(customer.getAccount().getBalance() < selectedLoan.getMoneyPerPayment()) {
            alert.setTitle("ERROR");
            alert.setContentText("your balance less than payment");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        boolean isOver = selectedLoan.pay();
        customer.getAccount().withdraw(selectedLoan.getMoneyPerPayment());
        errorLabelLoan.setText("successfully paid");
        if(isOver) {
            customer.getLoanDB().remove(selectedLoan);
        }
        addLoan();
        Transaction transaction = new Transaction(customer.getFirstName(), customer.getLastName(), customer.getAccount().getAccountNO(),
                customer.getAccount().getAccountNO(), TransactionType.LOAN_INSTALMENT, selectedLoan.getMoneyPerPayment());
        customer.getAccount().getTransactionDB().addTransaction(transaction);
        showInfoLoan();
    }

    public void add(ActionEvent event) {
        try {
            long money = Long.parseLong(amount.getText());
            int month = Integer.parseInt(duration.getText());
            long moneyPerPayment = money / month;
            LoanType loanType = LoanType.LONG_TIME;
            Loan loan = new Loan(money, month, moneyPerPayment, loanType);
            customer.getLoanRequestDB().addLoan(loan);
            LoanThread loanThread = new LoanThread();
            loanThread.setInfo(customer, loan);
            Thread thread = new Thread(loanThread);
            thread.start();
            errorLabelRequestLoan.setText("successfully sent!!");
            addLoanRequest();
        } catch (Exception e) {
            alert.setTitle("Error");
            alert.setContentText("invalid input!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
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

    private void addLoan(){
        amountColumnLoan.setCellValueFactory(new PropertyValueFactory<>("loanMoney"));
        installmentsColumnLoan.setCellValueFactory(new PropertyValueFactory<>("moneyPerPayment"));
        initiationDateColumnLoan.setCellValueFactory(new PropertyValueFactory<>("date"));
        ObservableList<Loan> data = FXCollections.observableArrayList();

        for(Loan loan : customer.getLoanDB().getLoans()) {
            data.add(new Loan(loan));
        }
        tableViewLoan.setItems(data);
        tableViewLoan.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) { // Double-click
                Loan selectedLoan = tableViewLoan.getSelectionModel().getSelectedItem();
                if (selectedLoan != null) {
                    this.selectedLoan = customer.getLoanDB().findLoan(selectedLoan.getId());
                    System.out.println(selectedLoan);
                    showInfoLoan();
                }
            }
        });
    }

    private void showInfoLoan() {
        installmentNo.setText(String.valueOf(selectedLoan.getInstallmentNo()));
        installmentAmount.setText(String.valueOf(selectedLoan.getMoneyPerPayment()));
    }

    private void addLoanRequest() {
        amountColumnLoanRequest.setCellValueFactory(new PropertyValueFactory<>("loanMoney"));
        installmentsColumnLoanRequest.setCellValueFactory(new PropertyValueFactory<>("moneyPerPayment"));
        statusColumnRequest.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Loan> data = FXCollections.observableArrayList();

        for(Loan loan : customer.getLoanRequestDB().getLoans()) {
            data.add(new Loan(loan));
        }
        tableViewLoanRequest.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLoanPage.setVisible(true);
        requestLoanPage.setVisible(false);
        alert = new Alert(Alert.AlertType.ERROR);
    }
}

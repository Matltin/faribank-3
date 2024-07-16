package ir.ac.kntu;

import ir.ac.kntu.box.BoxType;
import ir.ac.kntu.profitthread.ProfitThread;
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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BoxController implements Initializable {

    private Customer customer;

    @FXML
    private AnchorPane showBoxPage;
    @FXML
    private AnchorPane addBoxPage;
    @FXML
    private AnchorPane transferBoxPage;

    @FXML
    private TableView<Box> tableView;

    @FXML
    private TableColumn<Box, String> amountColumn;
    @FXML
    private TableColumn<Box, String> typeColumn;
    @FXML
    private TableColumn<Box, String> nameColumn;

    @FXML
    private TextField amountAdd;
    @FXML
    private TextField duration;
    @FXML
    private TextField name;
    @FXML
    private TextField amountTransfer;

    @FXML
    private RadioButton saving;
    @FXML
    private RadioButton profit;
    @FXML
    private RadioButton toAccount;
    @FXML
    private RadioButton toBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> comboBox;

    private Alert alert;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String selectedName;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        addBox();
        addComboBox();
    }

    private void addComboBox() {
        System.out.println(customer.getAccount().getBoxDB().getBoxes());
        for(Box box : customer.getAccount().getBoxDB().getBoxes()) {
            comboBox.getItems().add(box.getName());
        }
        comboBox.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 1) {
                String selectedName = comboBox.getSelectionModel().getSelectedItem();
                if (selectedName != null) {
                    this.selectedName = selectedName;
                    System.out.println(selectedName);
                }
            }
        });
    }

    public void transferButton(ActionEvent event) {
        if(selectedName == null) {
            alert.setTitle("ERROR");
            alert.setContentText("select box name!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        if (toAccount.isSelected()) {
            transferToAccount();
        } else if(toBox.isSelected()) {
            transferToBox();
        } else {
            alert.setTitle("ERROR");
            alert.setContentText("select Transfer Type");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    private void transferToBox() {
        try {
            String boxName = selectedName;
            if (checkBox(boxName)) {
                return;
            }
            Box box = customer.getAccount().getBoxDB().findBox(boxName);
            if (box.getBoxType() == BoxType.REMAINING) {
                alert.setTitle("ERROR");
                alert.setContentText("you cant transfer money to REMAINING box!!");
                if(alert.showAndWait().get() == ButtonType.YES) {
                    alert.close();
                }
                return;
            } else if (box.getBoxType() == BoxType.PROFIT) {
                alert.setTitle("ERROR");
                alert.setContentText("you cant transfer money to PROFIT box!!");
                if(alert.showAndWait().get() == ButtonType.YES) {
                    alert.close();
                }
                return;
            }
            long inputMoney = Long.parseLong(amountTransfer.getText());
            System.out.println(inputMoney);
            if (inputMoney > customer.getAccount().getBalance()) {
                alert.setTitle("ERROR");
                alert.setContentText("your input money is more than your balance!!");
                if(alert.showAndWait().get() == ButtonType.YES) {
                    alert.close();
                }
                return;
            }
            box.deposit(inputMoney);
            customer.getAccount().withdraw(inputMoney);
            errorLabel.setText("successfully transfer");
        } catch (Exception e) {
            alert.setTitle("ERROR");
            alert.setContentText("invalid input!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    private void transferToAccount() {
        try {
            String boxName = name.getText();
            if (checkBox(boxName)) {
                return;
            }
            Box box = customer.getAccount().getBoxDB().findBox(boxName);
            if (box.getBoxType() == BoxType.PROFIT) {
                if (!checkMonth(box)) {
                    alert.setTitle("ERROR");
                    alert.setContentText("you can not transfer money before " + box.getCountMonth() + " month!!");
                    if(alert.showAndWait().get() == ButtonType.YES) {
                        alert.close();
                    }
                    return;
                }
            }
            long inputMoney = Long.parseLong(amountTransfer.getText());
            if (inputMoney > box.getBalance()) {
                alert.setTitle("ERROR");
                alert.setContentText("your input money is more than your balance's box!!");
                if(alert.showAndWait().get() == ButtonType.YES) {
                    alert.close();
                }
                return;
            }
            box.withdraw(inputMoney);
            customer.getAccount().deposit(inputMoney);
            if (box.getBoxType() == BoxType.PROFIT) {
                customer.getAccount().getBoxDB().removeBox(box);
            }
        } catch (Exception e) {
            alert.setTitle("ERROR");
            alert.setContentText("invalid input!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        }
    }

    public void showBoxes(ActionEvent event) {
        addBoxPage.setVisible(false);
        showBoxPage.setVisible(true);
        transferBoxPage.setVisible(false);
        addBox();
    }
    public void addBoxes(ActionEvent event) {
        addBoxPage.setVisible(true);
        showBoxPage.setVisible(false);
        transferBoxPage.setVisible(false);
    }
    public void transferBoxes(ActionEvent event) {
        addBoxPage.setVisible(false);
        showBoxPage.setVisible(false);
        transferBoxPage.setVisible(true);
        addComboBox();
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

    public void addBoxButton(ActionEvent event) {
        Box box = customer.getAccount().getBoxDB().findBox(name.getText());
        if(box != null) {
            alert.setTitle("ERROR");
            alert.setContentText("this box is already exist!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        if(saving.isSelected()) {
            box = new Box(name.getText(), 0, BoxType.SAVING);
            errorLabel.setText("Successfully added");
        } else if(profit.isSelected()) {
            box = mackProfitBox();
            if(box == null) {
                return;
            } else {
                errorLabel.setText("Successfully added");
            }
        } else {
            alert.setTitle("ERROR");
            alert.setContentText("chose one of BOX TYPE!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
        }
        customer.getAccount().getBoxDB().addBox(box);
    }

    private Box mackProfitBox() {
        try {
            long inputMoney = Long.parseLong(amountAdd.getText());
            if (inputMoney > customer.getAccount().getBalance()) {
                alert.setTitle("ERROR");
                alert.setContentText("your input money is more than tour balance!!");
                if(alert.showAndWait().get() == ButtonType.YES) {
                    alert.close();
                }
                return null;
            }
            int monthCounter = Integer.parseInt(duration.getText());

            Box box = new Box(name.getText(), inputMoney, BoxType.PROFIT, monthCounter);
            customer.getAccount().withdraw(inputMoney);

            ScheduledExecutorService scheduler
                    = Executors.newScheduledThreadPool(monthCounter);

            for(int i = 1; i <= monthCounter; i++) {
                ProfitThread profitThread = new ProfitThread();
                profitThread.setBox(box, customer);
                Thread thread = new Thread(profitThread);
                scheduler.schedule(thread, 10L * i, TimeUnit.SECONDS);
            }
            return box;
        } catch (Exception e) {
            alert.setTitle("ERROR");
            alert.setContentText("invalid input!!");
            if(alert.showAndWait().get() == ButtonType.YES) {
                alert.close();
            }
            return null;
        }
    }

    private boolean checkBox(String boxName) {
        for (Box box : customer.getAccount().getBoxDB().getBoxes()) {
            if (boxName.equals(box.getName())) {
                return false;
            }
        }
        System.out.println(Constance.RED + "there is no box with this name!!" + Constance.RESET);
        return true;
    }

    private boolean checkMonth(Box box) {
        Date nowDate = new Date();
        Date boxDate = box.getDate();
        long diff = nowDate.getTime() - boxDate.getTime();
        return diff > Constance.MILE_SECOND * box.getCountMonth();
    }

    private void addBox() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("boxType"));

        ObservableList<Box> data = FXCollections.observableArrayList();

        for(Box box : customer.getAccount().getBoxDB().getBoxes()) {
            data.add(new Box(box));
        }
        tableView.setItems(data);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addBoxPage.setVisible(false);
        showBoxPage.setVisible(true);
        transferBoxPage.setVisible(false);
        alert = new Alert(Alert.AlertType.ERROR);
    }
}

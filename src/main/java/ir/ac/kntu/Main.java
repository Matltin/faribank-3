package ir.ac.kntu;

import ir.ac.kntu.db.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private SimCardDB simCardDB;
    private CustomerDB customerDB;
    private BankDB bankDB;
    private AnswerDB answerDB;
    private PayaDB payaDB;

    @Override
    public void start(Stage stage) throws Exception {
        inti();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("LoginStyle.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void inti() {
        simCardDB = new SimCardDB();
        customerDB = new CustomerDB(simCardDB);
        bankDB = new BankDB(simCardDB);
//        adminDB = new AdminDB(new HashSet<>());
//        chiefDB = new ChiefDB(new HashSet<>());
        answerDB = new AnswerDB();
        payaDB = new PayaDB();
    }
}

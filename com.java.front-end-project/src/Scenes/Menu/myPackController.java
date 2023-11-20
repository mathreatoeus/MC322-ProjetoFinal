package Scenes.Menu;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class myPackController {

    @FXML
    private MenuItem btnConfig;

    @FXML
    private Button btnExplore;

    @FXML
    private MenuItem btnLogOut;

    @FXML
    private MenuItem btnMyTravelPack;

    @FXML
    private MenuButton userMenu;

    @FXML
    private Label userName;

    @FXML
    void customPackPage(ActionEvent event) {

    }

    @FXML
    void explorePage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("./sysLayout.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       

        userName.getScene().getWindow().hide();

    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        URL path = getClass().getResource("../Authentication/layout.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       

        userName.getScene().getWindow().hide();

    }

    @FXML
    void myTravelPackPage(ActionEvent event) {

    }

    @FXML
    void packConfigPage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("../UserSpecific/PackageRegistration.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       

        userName.getScene().getWindow().hide();

    }

    @FXML
    void userConfigurationPage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("./sysUserUpdate.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       

        userName.getScene().getWindow().hide();

    }

}


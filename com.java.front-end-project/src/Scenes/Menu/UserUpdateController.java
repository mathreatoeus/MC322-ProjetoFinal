package Scenes.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Scenes.Authentication.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class SystemController implements Initializable{

    @FXML
    private MenuItem btnConfig;

    @FXML
    private Button btnExplore;

    @FXML
    private Button btnLeft;

    @FXML
    private MenuItem btnLogOut;

    @FXML
    private MenuItem btnMyTravelPack;

    @FXML
    private Button btnRight;

    @FXML
    private Label cardCost1;

    @FXML
    private Label cardName1;

    @FXML
    private MenuButton userMenu;

    @FXML
    private Label userName;



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
    void customPackPage(ActionEvent event) {

    }

    @FXML
    void explorePage(ActionEvent event) {

    }

    @FXML
    void myTravelPackPage(ActionEvent event) {

    }

    @FXML
    void packConfigPage(ActionEvent event) {

    }

    @FXML
    void passToLeft(ActionEvent event) {

    }

    @FXML
    void passToRight(ActionEvent event) {

    }

    @FXML
    void userConfigurationPage(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        userName.setText(Session.user);
    }

}








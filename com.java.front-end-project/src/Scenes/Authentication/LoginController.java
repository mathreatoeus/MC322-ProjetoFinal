package Scenes.Authentication;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField textFieldPwd;

    @FXML
    private TextField textFieldUser;

    @FXML
    void forgotPwd(ActionEvent event) {

    }

    @FXML
    void logIn(ActionEvent event) {
        //Call a function to get credentials
        //Check if the user exists or password is correct
        if(textFieldUser.getText().equals("test") && textFieldPwd.getText().equals("123")){
            Session.user = textFieldUser.getText();
            try {
                URL path = getClass().getResource("../Menu/sysLayout.fxml");
                
                Parent root;
                root = FXMLLoader.load(path);
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


                btnLogin.getScene().getWindow().hide();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else{
            Alert alert = new Alert(AlertType.ERROR, "User or password is invalid.");
            alert.show();

        }

    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        URL path = getClass().getResource("./regLayout.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


        btnLogin.getScene().getWindow().hide();



    }

}
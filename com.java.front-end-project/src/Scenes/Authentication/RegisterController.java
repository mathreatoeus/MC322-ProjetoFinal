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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Button btnReg;

    @FXML
    private PasswordField textFieldPwd;

    @FXML
    private PasswordField textFieldPwdConf;

    @FXML
    private TextField textFieldUser;

    @FXML
    void register(ActionEvent event) throws IOException {
        //Check if any field is blank
        if(textFieldPwd.getText().equals("") || textFieldPwdConf.getText().equals("") || textFieldUser.getText().equals("")){
            Alert alert = new Alert(AlertType.INFORMATION, "There are still empty fields!");
            alert.showAndWait();  
        } else{
            //Check if password matches
            if(textFieldPwd.getText().equals(textFieldPwdConf.getText())){
                //Check if user already exists
                if(true){
                    URL path = getClass().getResource("./layout.fxml");
                    Parent root = FXMLLoader.load(path);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    Alert alert = new Alert(AlertType.INFORMATION, "User registered!");
                    alert.show();
                    
                    btnReg.getScene().getWindow().hide();
                } else{
                    Alert alert = new Alert(AlertType.INFORMATION, "User already exists!");
                    alert.show();

                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "Passwords don't match!");
                alert.show();
            }

        }

    }

}
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
import controllers.UsuarioControllerImpl;
import models.usuario.Cliente;
import models.usuario.Funcionario;


public class LoginController {
    UsuarioControllerImpl uC =  new UsuarioControllerImpl();

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
        Funcionario f =  null;
        Cliente c = null;

        if(!(textFieldUser.getText().equals("") || textFieldPwd.getText().equals(""))){
            if (uC.funcionarioExiste(textFieldUser.getText().toString())){
                System.out.println("-->Getting Employee");
                f = uC.usuarioExists(textFieldPwd.getText().toString(), textFieldUser.getText().toString());
            
                Session.userId = f.getIdUsuario();
                Session.userName = f.getNome();
                Session.userType = f.getCargo().toString();

            }
            if (uC.clienteExiste(textFieldUser.getText().toString())){
                System.out.println("-->Getting Client");
                c = uC.usuarioExists(textFieldPwd.getText().toString(), textFieldUser.getText().toString());

                Session.userId = c.getIdUsuario();
                Session.userName = c.getNome();
                Session.userType = "CLIENTE";

            }

            if(f != null || c != null){
                try {
                    URL path;
                    if (Session.userType == "CLIENTE"){
                        path = getClass().getResource("../Menu/sysLayoutCustomer.fxml");

                    }else{
                        path = getClass().getResource("../Menu/sysLayout.fxml");

                    }
                    
                    
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
        } else{
            Alert alert = new Alert(AlertType.ERROR, "There are blank fields.");
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
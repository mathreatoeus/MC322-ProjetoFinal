package Scenes.Menu;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Scenes.Authentication.Session;
import controllers.UsuarioControllerImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import models.usuario.Cliente;
import models.usuario.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class userUpdateController implements Initializable{

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
    private TextField celular;

    @FXML
    private TextField cpf;

    @FXML
    private TextField cvv;

    @FXML
    private TextField email;

    @FXML
    private TextField endereco;

    @FXML
    private TextField nascimento;

    @FXML
    private TextField ncartao;

    @FXML
    private TextField nome;

    @FXML
    private TextField nomecartao;

    @FXML
    private TextField validade;

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
    private TableColumn<Funcionario, Funcionario.Cargo> col_cargo;

    @FXML
    private TableColumn<Funcionario, String> col_celular;

    @FXML
    private TableColumn<Funcionario, String> col_cpf;

    @FXML
    private TableColumn<Funcionario, String> col_email;

    @FXML
    private TableColumn<Funcionario, String> col_endereco;

    @FXML
    private TableColumn<Funcionario, Integer> col_idUsuario;

    @FXML
    private TableColumn<Funcionario, LocalDate> col_nascimento;

    @FXML
    private TableColumn<Funcionario, String> col_nome;

    @FXML
    private TableView<Funcionario> tableUsers;





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
    void myTravelPackPage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("./myPack.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       

        userName.getScene().getWindow().hide();

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
    void passToLeft(ActionEvent event) {

    }

    @FXML
    void passToRight(ActionEvent event) {

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        userName.setText(Session.userName);
        UsuarioControllerImpl uC = new UsuarioControllerImpl();
        if (Session.userType == "CLIENTE"){
            Cliente c = uC.getCliInfo(Session.userId);
            nome.setText(c.getNome());
            nascimento.setText(c.getNascimento().toString());
            celular.setText(c.getCelular().toString());
            nomecartao.setText(c.getNomeCartao().toString());
            validade.setText(c.getValidade().toString());
            cpf.setText(c.getCpf().toString());
            email.setText(c.getEmail().toString());
            endereco.setText(c.getEndereco().toString());
            ncartao.setText(c.getNumeroCartao().toString());
            cvv.setText(String.valueOf(c.getCvv()));


        } else{
            Funcionario f = uC.getFuncInfo(Session.userId);
            nome.setText(f.getNome());
            nascimento.setText(f.getNascimento().toString());
            celular.setText(f.getCelular().toString());
            cpf.setText(f.getCpf().toString());
            email.setText(f.getEmail().toString());
            endereco.setText(f.getEndereco().toString());
        }

        ObservableList <Funcionario> funcList = FXCollections.observableArrayList();
        for(Funcionario func : uC.getAllList()){
            funcList.add(func);
        }

        col_idUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col_nascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
        col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_celular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        col_endereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        col_cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        col_cargo.setCellFactory(
            ComboBoxTableCell.forTableColumn(
                Funcionario.Cargo.values()
            ));
        
        tableUsers.setItems(funcList);


    }

    @FXML
    void updateUser(ActionEvent event) {
        Funcionario f = tableUsers.getSelectionModel().getSelectedItem();
        if (f == null){


        }else{
            System.out.println(f.getCargo());
        }
        

    }

}








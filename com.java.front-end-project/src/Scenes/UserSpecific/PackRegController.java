package Scenes.UserSpecific;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Scenes.Authentication.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.pacote.Pacote;

import controllers.PacoteControllerImpl;

public class PackRegController implements Initializable{

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
    private TableColumn<Pacote, Integer> col_id;

    @FXML
    private TableColumn<Pacote, Integer> col_idDestino;

    @FXML
    private TableColumn<Pacote, Pacote.Categoria> col_categoria;

    @FXML
    private TableColumn<Pacote, Integer> col_idHospedagem;

   @FXML
    private TableColumn<Pacote, Pacote.TipoPassagem> col_tipopassagem;

    @FXML
    private TableColumn<Pacote, Integer> col_idpassagem;

    @FXML
    private TableColumn<Pacote, Integer> col_idaluguelcarro;

    @FXML
    private TableColumn<Pacote, Double> col_desconto;

    @FXML
    private TableColumn<Pacote, Double> col_preco;

    @FXML
    private TableColumn<Pacote, Double> col_mediaavaliacoes;

    @FXML
    private TableColumn<Pacote, Integer> col_numavaliacoes;

    @FXML
    private TableColumn<Pacote, Boolean> col_fechado;

    @FXML
    private TableView <Pacote> tablePacks;

 

    @FXML
    void customPackPage(ActionEvent event) {

    }

    @FXML
    void explorePage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("../Menu/SysLayout.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.setHeight(800);
        //stage.setWidth(1000);
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
    void myTravelPackPage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("../Menu/myPack.fxml");
        
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
    void userConfigurationPage(ActionEvent event) throws IOException {
        URL path = getClass().getResource("../Menu/sysUserUpdate.fxml");
        
        Parent root;
        root = FXMLLoader.load(path);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();       

        userName.getScene().getWindow().hide();

    }


@Override
public void initialize(URL location, ResourceBundle resources){
    ObservableList <Pacote> packList = FXCollections.observableArrayList();
    PacoteControllerImpl pc = new PacoteControllerImpl();

    for(Pacote p : pc.getPacoteList()){
        packList.add(p);

    }    

    for(Pacote p : packList){
        System.out.println(p.getId());
        System.out.println(p.getIdDestino());
        System.out.println(p.getCategoria());
        System.out.println(p.getIdHospedagem());
        System.out.println(p.getTipoPassagem());
        System.out.println(p.getIdPassagem());
        System.out.println(p.getIdAluguelCarro());
        System.out.println(p.getDesconto());
        System.out.println(p.getPreco());
        System.out.println(p.getMediaAvaliacoes());
        System.out.println(p.getNumAvaliacoes());
        System.out.println(p.getFechado());
        System.out.println("--------------------------");
    }
    
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_idDestino.setCellValueFactory(new PropertyValueFactory<>("idDestino"));
    col_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));    
    col_idHospedagem.setCellValueFactory(new PropertyValueFactory<>("idHospedagem"));
    col_tipopassagem.setCellValueFactory(new PropertyValueFactory<>("tipoPassagem"));
    col_idpassagem.setCellValueFactory(new PropertyValueFactory<>("idPassagem"));
    col_idaluguelcarro.setCellValueFactory(new PropertyValueFactory<>("idAluguelCarro"));
    col_desconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
    col_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));
    /*
    col_mediaavaliacoes.setCellValueFactory(new PropertyValueFactory<>("mediaAvaliacoes"));
    col_numavaliacoes.setCellValueFactory(new PropertyValueFactory<>("numAvaliacoes"));    
    col_fechado.setCellValueFactory(new PropertyValueFactory<>("fechado"));
    */

    System.out.println("--> Tentar adicionar os elementos");

    tablePacks.setItems(packList);

    System.out.println("--> Adicionou os elementos");
}


}








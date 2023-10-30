import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL path = getClass().getResource("./Scenes/Authentication/layout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        Parent root = fxmlLoader.load();
        Scene tela = new Scene(root);

        primaryStage.setTitle("Teste");
        primaryStage.setScene(tela);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

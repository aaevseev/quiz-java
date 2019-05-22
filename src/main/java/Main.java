import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Тестирование");
        MainController.changeStage(primaryStage, root);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

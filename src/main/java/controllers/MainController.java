package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    public static void changeStage(Stage stage, Parent root) {
        stage.setScene(new Scene(root));
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
        stage.setMaxHeight(stage.getHeight());
        stage.centerOnScreen();
    }

    static void closePreviouslyStage(ActionEvent event) {
        Stage previouslyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        previouslyStage.close();
    }
}

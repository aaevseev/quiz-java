package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController extends MainController {

    @FXML
    public void onClickStart(ActionEvent event) {
        try {
            closePreviouslyStage(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Тестирование");
            changeStage(stage, loader.load());
            stage.show();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

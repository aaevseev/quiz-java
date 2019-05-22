package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ResultController extends MainController {

    @FXML
    public Label correctCountText;
    @FXML
    public Label correctAnswerText;

    private int count = 0;

    void setResult(int countCorrect, ArrayList<String> correctAnswer) {
        correctCountText.setText(correctCountText.getText() + " " + countCorrect + " верных ответа");
        correctAnswerText.setText("Правильные ответы:");

        for (String mCorrectAnswer : correctAnswer) {
            correctAnswerText.setText(correctAnswerText.getText() + "\n " + ++count + ") [" + mCorrectAnswer + "]");
        }
    }

    @FXML
    public void onClickRepeat(ActionEvent event) {
        closePreviouslyStage(event);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Тестирование");
            changeStage(stage, loader.load());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

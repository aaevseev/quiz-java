package controllers;

import base.Constants;
import base.Question;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class QuizController extends MainController {

    @FXML
    Label textQuestion;
    @FXML
    RadioButton firstChoice;
    @FXML
    RadioButton secondChoice;
    @FXML
    RadioButton thirdChoice;
    @FXML
    RadioButton fourthChoice;

    private ToggleGroup toggleGroup;

    private int answerCount = 0;
    private int correctAnswerCount = 0;

    private Question[] questions;
    private ArrayList<String> correctAnswers = new ArrayList<>();
    private ArrayList<RadioButton> radioButtonsList = new ArrayList<>();

    @FXML
    public void initialize() {

        radioButtonsList = new ArrayList<>();

        radioButtonsList.add(firstChoice);
        radioButtonsList.add(secondChoice);
        radioButtonsList.add(thirdChoice);
        radioButtonsList.add(fourthChoice);

        toggleGroup = new ToggleGroup();

        for (RadioButton aRadioButtonsList : radioButtonsList) {
            aRadioButtonsList.setToggleGroup(toggleGroup);
        }

        getObjectFromJson();
        initAnswer(new ActionEvent());
    }

    private void getObjectFromJson() {
        try {
            InputStream file = this.getClass().getClassLoader().getResourceAsStream(("questions.json"));
            String json = IOUtils.toString(Objects.requireNonNull(file), "UTF-8");
            questions = new Gson().fromJson(json, Question[].class);

            for (Question question : questions) {
                for (int i = 0; i < question.answers.size(); i++) {
                    if (question.answers.get(i).isCorrect) {
                        correctAnswers.add(question.answers.get(i).text);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNextQuestion(ActionEvent event) {
        if (questions[answerCount].isMulti) {
            for (RadioButton radioButton : radioButtonsList) {
                if (radioButton.isSelected())
                    if (Boolean.parseBoolean(radioButton.getUserData().toString())) correctAnswerCount++;
            }
        } else {
            if (Boolean.parseBoolean(toggleGroup.getSelectedToggle().getUserData().toString())) correctAnswerCount++;
        }

        answerCount++;
        initAnswer(event);
    }

    private void initAnswer(ActionEvent event) {

        if (questions.length != 0 && answerCount < questions.length) {

            textQuestion.setText(questions[answerCount].text);

            for (int i = 0; i < questions[answerCount].answers.size(); i++) {
                if (questions[answerCount].isMulti) {
                    textQuestion.setText(textQuestion.getText() + " \n(Выберите несколько вариантов)");
                    for (RadioButton radioButton : radioButtonsList) {
                        radioButton.setToggleGroup(null);
                        radioButton.setSelected(false);
                    }
                } else {
                    for (RadioButton aRadioButtonsList : radioButtonsList) {
                        aRadioButtonsList.setToggleGroup(toggleGroup);
                    }
                }

                if (questions[answerCount].answers.size() == Constants.MAX_CHOICE - 1) {

                    Iterator<RadioButton> iterator = radioButtonsList.iterator();

                    while (iterator.hasNext()) {
                        RadioButton radioButton = iterator.next();
                        if (!iterator.hasNext()) {
                            radioButton.setVisible(false);
                        } else {
                            radioButton.setText(questions[answerCount].answers.get(i).text);
                            radioButton.setUserData(questions[answerCount].answers.get(i++).isCorrect);
                        }
                    }
                } else {
                    fourthChoice.setVisible(true);
                    for (RadioButton radioButton : radioButtonsList) {
                        radioButton.setText(questions[answerCount].answers.get(i).text);
                        radioButton.setUserData(questions[answerCount].answers.get(i++).isCorrect);
                    }
                }

                toggleGroup.selectToggle(null);
            }
        } else {
            Stage previouslyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previouslyStage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/result.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Результат");
                changeStage(stage, loader.load());
                stage.show();

                ResultController controller = loader.getController();
                controller.setResult(correctAnswerCount, correctAnswers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

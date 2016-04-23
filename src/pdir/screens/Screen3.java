package pdir.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pdir.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Screen3 implements Initializable, ControlledScreen {

    final String NAME = "Screen 3";
    ScreensController controller;
    Context context = Context.getInstance();
    Question screen3Question;

    @FXML VBox screen3Vbox;
    @FXML HBox questionHbox;
    @FXML
    ProgressBarController progHBController;
    @FXML HBox nextButton;
    @FXML NextButtonController nextButtonController;

    @FXML
    public void initializeUI() {
        // Set UI components
        progHBController.setPercentage(0.3);
        progHBController.setLabel(NAME);
        nextButtonController.setNextButton(new EventHandler() {
            @Override
            public void handle(Event event) {
                goToScreen4();
            }
        });
        nextButtonController.setNextButtonText("Next");

        screen3Question = new Question("For the photographs you have deleted, why did you delete them?", "1");
        Options ops = new Options();
        String [] options = new String[] {"No reason.", "Unusuable picture", "People in photo",
                "Computer monitor/screen", "Participant was in photo.", "Photo location", "Object in photo",
                "Participant error", "Nudity"};

        for (String option: options) {
            Option op = new Option(option);
            ops.addOption(op);
        }

        screen3Question.addOptions(ops);
        createUI(screen3Question);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
        initializeUI();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void createUI(Question question) {
        Node vBox = question.makeUI();
        questionHbox.getChildren().add(vBox);
    }

    public void goToScreen4() {
        context.setScreen3Question(screen3Question);
        controller.setScreen(Main.screen4ID);
    }
}

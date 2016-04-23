package pdir.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pdir.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Screen7 implements Initializable, ControlledScreen {
    final String NAME = "Screen 7";
    Context mContext = Context.getInstance();
    @FXML Pane questionsVBox;
    @FXML
    ProgressBarController progHBController;
    @FXML HBox nextButton;
    @FXML
    NextButtonController nextButtonController;
    ScreensController controller;

    ArrayList<ReviewImage> images = mContext.getImages();
    ArrayList<ReviewImage> sharedImages = new ArrayList<>();
    ArrayList<ReviewImage> notSharedImages = new ArrayList<>();
    int sharedCounter = 0;
    int notSharedCounter = 0;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;

        progHBController.setPercentage(0.9);
        progHBController.setLabel(NAME);
        nextButtonController.setNextButtonText("Next");
        nextButtonController.setNextButton(new EventHandler() {
            @Override
            public void handle(Event event) {
                loadImage();
            }
        });

        for(ReviewImage image : images) {
            if(!image.isCorrupt() && !image.isInappropriate()) {

                System.out.println("Would share " + image.getUri() + " : " + image.isWouldShare());
                if(image.isWouldShare())
                    sharedImages.add(image);
                else
                    notSharedImages.add(image);
            }
        }
        loadImage();
    }

    private void loadImage() {
        questionsVBox.getChildren().clear();
        ReviewImage rI = null;
        FlowPane flowPane = null;

        if(sharedCounter < sharedImages.size()) {
            rI = sharedImages.get(sharedCounter);
            flowPane = (FlowPane) createUI(rI.getShareReason());
            sharedCounter++;
        }
        else if(notSharedCounter < notSharedImages.size()) {
            rI = notSharedImages.get(notSharedCounter);
            flowPane = (FlowPane) createUI(rI.getNotShareReason());
            notSharedCounter++;
        }
        else {
            goToScreen8();
        }


        try {
            final Image image = new Image(rI.getUri());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(240.0);
            imageView.setFitWidth(280.0);
            questionsVBox.getChildren().add(imageView);
            questionsVBox.getChildren().add(flowPane);
        }
        catch(NullPointerException except) {
            System.out.println("Null pointer exception.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Node createUI(Question question) {
        FlowPane flowPane = new FlowPane();
        Label questionLabel = new Label(question.getText());
        questionLabel.getStyleClass().add("question-label");

        flowPane.getChildren().add(questionLabel);

        for(final Option option : question.getOptions().getOptions()) {
            CheckBox checkBox = new CheckBox(option.getText());

            checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    option.setSelected(!option.isSelected());
                    System.out.println(option.getText());
                    System.out.println("Is selected? " + option.isSelected());
                }
            });
            flowPane.getChildren().add(checkBox);
        }

        flowPane.setMaxWidth(300.0);
        flowPane.getStyleClass().add("question-vbox");

        return flowPane;
    }




    public void goToScreen8() {
        controller.setScreen(Main.screen8ID);
    }
}

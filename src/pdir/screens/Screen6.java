package pdir.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import pdir.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Screen6 implements Initializable, ControlledScreen {
    final String NAME = "Screen 6";
    ScreensController controller;
    Context mContext = Context.getInstance();
    ArrayList<ReviewImage> images = mContext.getImages();
    ReviewImage mCurrentImage = null;

    @FXML
    ProgressBarController progHBController;
    @FXML HBox nextButton;
    @FXML NextButtonController nextButtonController;
    @FXML VBox imageVbox;

    ArrayList<ReviewImage> acceptableImages = new ArrayList<>();

    int counter = 0;
    VBox radioVBox;

    int numSelected = 0;
    boolean wouldShare = true;


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
        progHBController.setPercentage(0.8);
        progHBController.setLabel(NAME);
        nextButtonController.setNextButtonText("Next");
        nextButtonController.setNextButton(new EventHandler() {
            @Override
            public void handle(Event event) {

                loadNextImage();
            }
        });

        for(ReviewImage image : images) {
            if(!image.isCorrupt() && !image.isInappropriate()) {
                acceptableImages.add(image);
            }
        }

        System.out.println("Counter : " + counter);
        loadNextImage();
    }

    public Node createUI(Question question) {
        FlowPane flowPane = new FlowPane();

        for(final Option option : question.getOptions().getOptions()) {
            CheckBox checkBox = new CheckBox(option.getText());

            checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    option.setSelected(!option.isSelected());

                    if (option.getText().equals("Would share with everyone")) {
                        wouldShare = option.isSelected();
                    }
                    else {

                        if (option.isSelected()) {
                            numSelected++;
                        }
                        else {
                            numSelected--;
                        }

                    }
                    wouldShare = (numSelected <= 0) ;

                    if(wouldShare)
                        radioVBox.setVisible(false);
                    else
                        radioVBox.setVisible(true);

                }
            });
            flowPane.getChildren().add(checkBox);
        }

        flowPane.setMaxWidth(300.0);
        flowPane.getStyleClass().add("question-vbox");

        return flowPane;
    }

    public VBox createFollowUpQ(ReviewImage rI) {

        radioVBox = new VBox();
        radioVBox.setAlignment(Pos.CENTER);
        radioVBox.getStyleClass().add("question-vbox");

        Label radioOneLabel = new Label("How would you feel if the image were accidentally shared?");
        radioOneLabel.getStyleClass().add("question-label");
        radioVBox.getChildren().add(radioOneLabel);

        VBox angry = (VBox) rI.getAngryQuestion().makeUI();
        VBox embarrassed = (VBox) rI.getEmbarrassedQuestion().makeUI();

        angry.setMaxWidth(400.0);
        radioVBox.getChildren().add(angry);
        embarrassed.setMaxWidth(400.0);
        radioVBox.getChildren().add(embarrassed);

        radioVBox.setVisible(false);

        return radioVBox;
    }


    public void loadImage(int imageNumber) {
        imageVbox.getChildren().clear();

        ReviewImage reviewImage = acceptableImages.get(imageNumber);
        mCurrentImage = reviewImage;

        final Image image = new Image(reviewImage.getUri());
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(240.0);
        imageView.setFitWidth(280.0);
        imageVbox.getChildren().add(imageView);

        FlowPane flowPane = (FlowPane) createUI(reviewImage.getNotShareQuestion());
        imageVbox.getChildren().add(flowPane);

        VBox radioV = createFollowUpQ(reviewImage);
        radioV.setAlignment(Pos.CENTER);
        imageVbox.getChildren().add(radioV);

    }

    public void loadNextImage() {
        if(mCurrentImage != null) {
            System.out.println("Would share? " + wouldShare);
            mCurrentImage.setWouldShare(wouldShare);
            System.out.println(mCurrentImage.getUri() + " would share " + wouldShare);
        }

        if (counter < acceptableImages.size()){
            loadImage(counter);
            counter++;
            numSelected = 0;
            wouldShare = true;
        }
        else {
            goToScreen7();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void goToScreen7() {
        for(ReviewImage ri:images) {
            System.out.println(ri.toJSON());
        }
        controller.setScreen(Main.screen7ID);
    }
}

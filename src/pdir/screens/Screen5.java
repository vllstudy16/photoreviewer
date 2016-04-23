package pdir.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import pdir.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;


public class Screen5 implements Initializable, ControlledScreen {
    final String NAME = "Screen 5";
    ScreensController controller;
    Context mContext = Context.getInstance();
    @FXML
    ProgressBarController progHBController;
    @FXML HBox nextButton;
    @FXML
    NextButtonController nextButtonController;
    @FXML VBox deleteEventVbox;


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
        progHBController.setPercentage(0.6);
        progHBController.setLabel(NAME);
        nextButtonController.setNextButtonText("Next");
        nextButtonController.setNextButton(new EventHandler() {
            @Override
            public void handle(Event event) {
                goToScreen6();
            }
        });

        loadDeleteEvents();

    }

    private void loadDeleteEvents() {
        ArrayList<DeleteEvent> deleteEvents = mContext.getScreen5Deletes();

        for (DeleteEvent event : deleteEvents) {
            HBox hBox = new HBox();
            VBox imageLabelVbox = new VBox();

            Label disclaimer = new Label("This is the last image taken before you pressed the button.");
            disclaimer.setMaxWidth(280.0);
            disclaimer.setMinHeight(70.0);
            disclaimer.setWrapText(true);
            disclaimer.setTextAlignment(TextAlignment.CENTER);

            ReviewImage reviewImage = filterImages(event.getDeleteDatetime());
            Image image = new Image(reviewImage.getUri());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(280.0);
            imageView.setFitHeight(240.0);

            imageLabelVbox.getChildren().add(disclaimer);
            imageLabelVbox.getChildren().add(imageView);

            VBox vBox = new VBox();
            Label label = new Label(event.getDeleteDatetime().toString());
            label.getStyleClass().add("question-label");
            vBox.getChildren().add(label);

            hBox.getChildren().add(event.getQuestion().makeUI());
            hBox.getChildren().add(imageLabelVbox);
            vBox.getChildren().add(hBox);

            deleteEventVbox.getChildren().add(vBox);
        }
    }

    private ReviewImage filterImages(Date date) {
        long currentDiff = Long.MAX_VALUE;
        ReviewImage returnedImage = null;

        for(ReviewImage rI : mContext.getImages()) {
            Date imageDate = rI.getDate();
            long diff = Math.abs(date.getTime() - imageDate.getTime());

            if(diff < currentDiff) {
                currentDiff = diff;
                returnedImage = rI;
            }

        }

        return returnedImage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToScreen6() {

        controller.setScreen(Main.screen6ID);
    }
}

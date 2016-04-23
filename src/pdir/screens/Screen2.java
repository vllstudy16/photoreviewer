package pdir.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pdir.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Screen2 implements Initializable, ControlledScreen {
    final String NAME = "Screen 2";
    Context mContext = Context.getInstance();
    ArrayList<ReviewImage> mImages = mContext.getImages();
    ScreensController controller;

    @FXML
    FlowPane photoFlow;
    @FXML
    ProgressBarController progHBController;
    @FXML
    HBox nextButton;
    @FXML
    NextButtonController nextButtonController;


    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
        for (ReviewImage rI : mImages) {
            StackPane sP = createImageView(rI);
            photoFlow.getChildren().add(sP);
        }
        progHBController.setPercentage(0.15);
        progHBController.setLabel(NAME);
        nextButtonController.setNextButtonText("Next");
        nextButtonController.setNextButton(new EventHandler() {
            @Override
            public void handle(Event event) {
                nextScreen();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Screen 2");
    }

    private StackPane createImageView(final ReviewImage imageFile) {
        final ImageView imageView = new ImageView();
        String uri = imageFile.getUri();

        // Create Image and set on ImageView
        final Image image = new Image(uri);
        imageView.setImage(image);
        imageView.setFitWidth(140.0);
        imageView.setFitHeight(110.0);

        // Create StackPane and add image view and checkbox
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);
        final CheckBox checkBox = new CheckBox();
        stackPane.getChildren().add(checkBox);
        StackPane.setAlignment(checkBox, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(checkBox, new Insets(5));

        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageFile.setInappropriate(checkBox.isSelected());
                System.out.println("Is photo inappropriate? " + imageFile.isInappropriate());
            }
        });

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                checkBox.setSelected(!checkBox.isSelected());
                imageFile.setInappropriate(checkBox.isSelected());
                System.out.println("Is photo inappropriate? " + imageFile.isInappropriate());
            }
        });

        return stackPane;
    }

    public void nextScreen() {
        controller.setScreen(Main.screen3ID);
    }
}

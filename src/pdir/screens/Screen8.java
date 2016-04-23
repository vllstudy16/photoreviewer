package pdir.screens;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import org.zeroturnaround.zip.ZipUtil;
import pdir.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Screen8 implements Initializable, ControlledScreen {
    final String NAME = "Screen 8";
    ScreensController controller;
    Context context = Context.getInstance();
    ArrayList<ReviewImage> images = context.getImages();
    @FXML
    ProgressBarController progHBController;
    @FXML HBox nextButton;
    @FXML NextButtonController nextButtonController;
    @FXML VBox labelsVbox;

    private boolean DELETE_PHOTOS = true;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
        progHBController.setPercentage(1.0);
        progHBController.setLabel(NAME);
        nextButtonController.setNextButtonText("Save");
        nextButtonController.setNextButton(new EventHandler() {
            @Override
            public void handle(Event event) {
                //File loc = getFolderLocation();
                save();
            }
        });
    }


//    public File getFolderLocation() {
//        Stage window = (Stage) nextButton.getScene().getWindow();
//        File defaultDir = new File(".");
//        DirectoryChooser chooser = new DirectoryChooser();
//        chooser.setTitle("Select Photos Folder.");
//        chooser.setInitialDirectory(defaultDir);
//        return chooser.showDialog(window);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void writeResultFile(JSONObject obj) {
        try {
            File fileLocation = new File(context.getDirectory(), "results.json");
            PrintWriter writer = new PrintWriter(fileLocation, "UTF-8");
            System.out.println(obj);
            writer.println(obj.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private void zipFolder(String source, String destination) {
        System.out.println(source);
        System.out.println(destination);
        ZipUtil.pack(new File(source), new File(destination));
    }

    private void deleteFile(File file) {
        System.out.println("Deleting " + file.getAbsolutePath());

        boolean result = file.delete();

        if(result) {
            System.out.println("Image " + file.getAbsolutePath() + " successfully deleted.");
        }
        else {
            System.out.println("Unable to delete file: " + file.getAbsolutePath());
        }
    }

    private void deleteFiles() {
        System.out.println("Deleting images");
        for(ReviewImage image: images) {
            if(image.isInappropriate() || image.isCorrupt() || image.isDelete()) {
                deleteFile(image.getFile());
            }
        }
    }

    public void save() {

        if(DELETE_PHOTOS) {
            deleteFiles();
        }

        JSONObject obj = new JSONObject();
        LinkedList screen5 = new LinkedList();
        LinkedList imageList = new LinkedList();

        Question screen3Q = context.getScreen3Question();
        obj.put("screen3", screen3Q.toJSON());


        for(DeleteEvent de:context.getScreen5Deletes()) {
            screen5.add(de.toJSON());
        }
        obj.put("screen5", screen5);

        for(ReviewImage rI:images) {
            imageList.add(rI.toJSON());
        }
        obj.put("images", imageList);

        writeResultFile(obj);

        nextButton.setVisible(false);
        labelsVbox.getChildren().add(new Label("You can now close this window."));

    }
}

package pdir.screens;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pdir.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class Screen1 implements Initializable, ControlledScreen {

    @FXML Pane mainPane;
    @FXML Button mSelectFolderButton;
    Context mContext = Context.getInstance();
    Stage mStage;

    ScreensController controller;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {

    }

    public void setScreenParent(ScreensController screenParent) {

        controller = screenParent;
    }

    public File getFolderLocation() {
        Stage window = (Stage) mSelectFolderButton.getScene().getWindow();
        File defaultDir = new File(".");
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Photos Folder.");
        chooser.setInitialDirectory(defaultDir);
        return chooser.showDialog(window);
    }

    public void loadPhotos() {
        File photosDir = getFolderLocation();
        mContext.setDirectory(photosDir);
        System.out.println("Location of photos: " + photosDir);
        loadDeletes(photosDir);
        File[] files = photosDir.listFiles();

        for (File file : files) {
            if (file.isFile() && isJPEG(file)) {
                ReviewImage ri = new ReviewImage(file);
                ri.setDate(parseDate(file.getParentFile().getName(), file.getName()));
                mContext.addImages(ri);
            }
        }

        mStage = mContext.getStage();
        controller.setScreen(Main.screen2ID);
    }

    private void loadDeletes(File folderLocation) {
        String deleteFile = folderLocation.toString() + "/DEL_LOG.TXT";
        Path path = Paths.get(deleteFile);
        System.out.println(folderLocation + "/DEL_LOG.TXT");

        try {
            List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
            for (String line : lines) {
                int time = Integer.parseInt(line);
                Date datetime = new Date((long) time * 1000);
                mContext.addScreen5Delete(new DeleteEvent(datetime));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }


    }

    private boolean isJPEG(File file) {
        String name = file.getName(), extension = "";
        int i = name.lastIndexOf('.');

        if (i > 0) {
            extension = name.substring(i + 1);
        }

        return "JPG".equals(extension);
    }

    private Date parseDate(String day, String time) {
        System.out.println(day + ":" + time);
        Date date;
        SimpleDateFormat parseSDF = new SimpleDateFormat("yyyyMMddHH_mm_ss");
        String d = time.split("\\.")[0];

        try {
            date = parseSDF.parse(day + d);
            System.out.println("Return date " + date);
            return date;
        } catch (ParseException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}

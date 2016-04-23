package pdir;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    public static String screen1ID = "screen1";
    public static String screen1File = "ui/screen1.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "ui/screen2.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "ui/screen3.fxml";
    public static String screen4ID = "screen4";
    public static String screen4File = "ui/screen4.fxml";
    public static String screen5ID = "screen5";
    public static String screen5File = "ui/screen5.fxml";
    public static String screen6ID = "screen6";
    public static String screen6File = "ui/screen6.fxml";
    public static String screen7ID = "screen7";
    public static String screen7File = "ui/screen7.fxml";
    public static String screen8ID = "screen8";
    public static String screen8File = "ui/screen8.fxml";

    Context mContext = Context.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        mContext.setStage(primaryStage);

        ScreensController controller = new ScreensController();
        controller.addScreen(Main.screen1ID, Main.screen1File);
        controller.addScreen(Main.screen2ID, Main.screen2File);
        controller.addScreen(Main.screen3ID, Main.screen3File);
        controller.addScreen(Main.screen4ID, Main.screen4File);
        controller.addScreen(Main.screen5ID, Main.screen5File);
        controller.addScreen(Main.screen6ID, Main.screen6File);
        controller.addScreen(Main.screen7ID, Main.screen7File);
        controller.addScreen(Main.screen8ID, Main.screen8File);

        controller.setScreen(Main.screen1ID);

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setMinHeight(600.0);
        root.getChildren().add(controller);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/css/style.css");
        primaryStage.setScene(scene);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

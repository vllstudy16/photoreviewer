package pdir;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;

import java.util.HashMap;

public class ScreensController extends VBox {

    // Holds the Screens to be displayed
    private HashMap<String, String> screens = new HashMap<>();

    public ScreensController() {
        super();
    }

    public void addScreen(String name, String screen) {
        screens.put(name, screen);
    }

    public String getScreen(String name) {
        return screens.get(name);
    }

    public Node loadScreen(String name) {
        String fileLoc = screens.get(name);

        if (fileLoc != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fileLoc));
                Parent loadScreen = (Parent) loader.load();
                ControlledScreen screenController = ((ControlledScreen) loader.getController());
                screenController.setScreenParent(this);
                return loadScreen;

            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } else {
            System.out.println("Name does not exist!! \n");
            return null;
        }
    }

    public boolean setScreen(final String name) {
        System.out.println("Loading screen " + name);
        Node screen = loadScreen(name);

        if (screen != null) {
            if(!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screen);
            }
            else {
                getChildren().add(screen);

            }

            return true;
        }
        else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if(screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        }
        else {
            return true;
        }
    }
}

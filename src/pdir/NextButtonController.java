package pdir;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NextButtonController {

    @FXML Button nextButton;

    public void setNextButton(EventHandler event) {
        nextButton.setOnAction(event);
    }

    public void setNextButtonText(String text) {
        nextButton.setText(text);
    }
}

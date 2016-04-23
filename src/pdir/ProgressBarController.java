package pdir;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressBarController {

    @FXML
    ProgressBar progressBar;
    @FXML
    Label topLabel;

    public void setPercentage(Double percentage) {
        progressBar.setProgress(percentage);
    }

    public void setLabel(String text) {
        topLabel.setText(text);
    }
}

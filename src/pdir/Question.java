package pdir;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class Question {

    private String text;
    private String id;
    private Options options;
    private boolean radio = false;

    public Question(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public Question(String text, String id, boolean radio) {
        this.text = text;
        this.radio = radio;
        this.id = id;
        this.options = new Options();
    }

    public Question(String text, String id, Options options) {
        this.text = text;
        this.id = id;
        this.options = options;
    }

    public JSONObject toJSON() {
        return questionToJSON();
    }

    private JSONObject questionToJSON() {
        JSONObject obj = new JSONObject();
        LinkedList l = new LinkedList();

        obj.put("id", this.id);
        obj.put("question", this.text);

        ArrayList<Option> ops = this.options.getSelected();
        for (Option o: ops) {
            l.add(o.toString());
        }
        obj.put("options", l);

        return obj;
    }

    public void addOptions(Options options) {
        this.options = options;
    }

    public Options getOptions() {
        return this.options;
    }

    public String getText() {
        return this.text;
    }

    public Node makeUI() {
        FlowPane flowPane = new FlowPane();
        VBox qVbox = new VBox();

        flowPane.getStyleClass().add("question-vbox");

        Label label = new Label(text);
        label.getStyleClass().add("question-label");

        qVbox.getChildren().add(label);

        if(radio) {
            final ToggleGroup toggleGroup = new ToggleGroup();

            for(final Option option : options.getOptions()) {
                RadioButton radioButton = new RadioButton(option.getText());

                radioButton.setToggleGroup(toggleGroup);

                radioButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        for(Option opt : options.getOptions()) {
                            opt.setSelected(false);
                        }
                        option.setSelected(true);

                        for(Option op : options.getOptions()) {
                            if(op.isSelected())
                                System.out.println("Selected Radio button: " + op.getText());
                        }
                    }
                });

                flowPane.setHgap(20.0);
                flowPane.getChildren().add(radioButton);
            }
        }

        for(final Option option : options.getOptions()) {

            if(!radio) {
                CheckBox checkBox = new CheckBox(option.getText());
                checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        option.setSelected(!option.isSelected());
                        System.out.println("Is selected? " + option.isSelected());
                    }
                });

                flowPane.setHgap(20.0);
                flowPane.getChildren().add(checkBox);
            }
        }

        qVbox.getChildren().add(flowPane);
        return qVbox;
    }


}

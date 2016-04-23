package pdir;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Options {

    private ArrayList<Option> options = new ArrayList<Option>();

    public Options (){}

    public Options (String [] opts) {
        addOptions(opts);
    }

    public void addOption(Option option) {
        options.add(option);
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void addOptions(String[] options) {
        for(String option : options) {
            this.addOption(new Option(option));
        }
    }

    public ArrayList getSelected() {

        ArrayList<Option> selectedOptions = new ArrayList<>();
        for(Option option: options) {
            if(option.isSelected()) {
                selectedOptions.add(option);
            }
        }

        return selectedOptions;
    }

    public LinkedList toList() {
        LinkedList l = new LinkedList();
        for(Option option: options) {
            if(option.isSelected()) {
                l.add(option.toString());
            }
        }

        return l;
    }

}

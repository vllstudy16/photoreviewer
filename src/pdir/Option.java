package pdir;

public class Option {

    private boolean radio = false;
    private String text;
    private boolean selected = false;

    public Option(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return text;
    }
}

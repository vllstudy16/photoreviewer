package pdir;

import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Context {

    private final static Context instance = new Context();
    private File directory = null;
    private Stage stage;
    private ArrayList<ReviewImage> images = new ArrayList<>();
    private Question screen3Question;
    private ArrayList<DeleteEvent> screen5Deletes = new ArrayList<>();
    private ArrayList<Integer> screen6Ints = new ArrayList<>();
    private ArrayList<Question> screen7Questions = new ArrayList<>();

    public static Context getInstance() {
        return instance;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public Stage getStage() {
        return this.stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addImages(ReviewImage image) {
        images.add(image);
    }
    public ArrayList<ReviewImage> getImages() {
        return images;
    }

    public Question getScreen3Question() {
        return screen3Question;
    }
    public void setScreen3Question(Question screen3Question) {
        this.screen3Question = screen3Question;
    }

    public ArrayList<DeleteEvent> getScreen5Deletes() {
        return screen5Deletes;
    }
    public void addScreen5Delete(DeleteEvent de) {
        this.screen5Deletes.add(de);
    }
    public void setScreen5Deletes(ArrayList<DeleteEvent> screen5Deletes) {
        this.screen5Deletes = screen5Deletes;
    }

    public ArrayList<Integer> getScreen6Ints() {
        return screen6Ints;
    }
    public void setScreen6Ints(ArrayList<Integer> screen6Ints) {
        this.screen6Ints = screen6Ints;
    }

    public ArrayList<Question> getScreen7Questions() {
        return screen7Questions;
    }

    public void setScreen7Questions(ArrayList<Question> screen7Questions) {
        this.screen7Questions = screen7Questions;
    }

    public void addScreen7Question(Question q) {
        this.screen7Questions.add(q);
    }
}

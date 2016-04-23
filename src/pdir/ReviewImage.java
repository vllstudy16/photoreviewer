package pdir;

import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class ReviewImage {
    private boolean delete;
    private boolean inappropriate;
    private boolean corrupt;
    private File file;
    private Date date;
    private Question notShareQuestion, angryQuestion, embarrassedQuestion, shareReason, notShareReason;
    String[] radioOptions = {"1", "2", "3", "4", "5", "6", "7"};

    String[] shareOpts = new String[] {
            "No good reason not to share", "It is a good photo", "I like how other people look/are portrayed",
            "I want others to see it", "I like how I look/am portrayed", "People in it would want me to share",
            "This picture has interesting content", "Other"
    };

    String[] notShareOpts = new String[] {
            "No good reason to share it", "Where this photo was taken", "Objects (other than people) in the photo",
            "People within the photo", "Participant was in the photo", "It had private information",
            "It has uninteresting content", "It would be embarrassing to share it", "It was a bad photo",
            "It would have violated someone else's privacy", "Other"
    };

    String[] optStrings = {"Close family and friends", "Other family and friends",
            "Co-workers, classmates and acquaintances", "Would share with everyone"};
    Options opts = new Options(optStrings);

    private boolean wouldShare = false;

    public ReviewImage(File file) {
        String id = file.getName();
        this.file = file;
        this.delete = false;
        this.corrupt = false;
        this.inappropriate = false;
        this.date = new Date(1970, 1, 1);

        String notShareString = "Who would you not feel comfortable sharing this image with?";
        this.notShareQuestion = new Question(notShareString, id, opts);

        this.angryQuestion = createRadioQuestion("Embarrassed?", radioOptions, "6e");
        this.embarrassedQuestion = createRadioQuestion("Angry?", radioOptions, "6f");

        String shareQ = "You have chosen to share this image, for which of the following reasons are" +
                " you happy to share? (You can tick multiple reasons).";
        this.shareReason = createQuestion(shareQ, shareOpts, "7a");

        String notShareQ = "You have chosen not to share this image, for which of the following" +
                " reasons do you not want to share? (You can tick multiple reasons).";
        this.notShareReason = createQuestion(notShareQ, notShareOpts, "7b");
    }

    private Question createRadioQuestion(String question, String[] opts, String id) {
        Question q = new Question(question, id, true);

        Options options = new Options();
        for(String opt : opts) {
            options.addOption(new Option(opt));
        }

        q.addOptions(options);

        return q;
    }


    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();


        obj.put("image", this.file.getName());
        obj.put("corrupt", this.corrupt);
        obj.put("inappropriate", this.inappropriate);

        obj.put("shareWith", opts.toList());

        obj.put("angry", angryQuestion.getOptions().toList());
        obj.put("embarrassed", embarrassedQuestion.getOptions().toList());
        obj.put("shareReason", shareReason.getOptions().toList());
        obj.put("notShareReason", notShareReason.getOptions().toList());
        obj.put("wouldShare", this.wouldShare);

        return obj;
    }

    public String getUri() {
        return file.toURI().toString();
    }

    public boolean isDelete() {
        return delete;
    }

    private Question createQuestion(String question, String[] opts, String id) {
        Question q = new Question(question, id);

        Options options = new Options();
        for(String opt : opts) {
            options.addOption(new Option(opt));
        }

        q.addOptions(options);

        return q;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isCorrupt() {
        return corrupt;
    }

    public void setCorrupt(boolean corrupt) {
        this.corrupt = corrupt;
    }

    public boolean isInappropriate() {
        return inappropriate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setInappropriate(boolean inappropriate) {
        this.inappropriate = inappropriate;
    }

    public Question getNotShareQuestion() {
        return notShareQuestion;
    }

    public Question getAngryQuestion() {
        return angryQuestion;
    }

    public Question getEmbarrassedQuestion() {
        return embarrassedQuestion;
    }

    public boolean isWouldShare() {
        return wouldShare;
    }

    public void setWouldShare(boolean wouldShare) {
        this.wouldShare = wouldShare;
    }

    public Question getShareReason() {
        return shareReason;
    }

    public void setShareReason(Question shareReason) {
        this.shareReason = shareReason;
    }

    public Question getNotShareReason() {
        return notShareReason;
    }

    public void setNotShareReason(Question notShareReason) {
        this.notShareReason = notShareReason;
    }

    public File getFile() {
        return file;
    }
}

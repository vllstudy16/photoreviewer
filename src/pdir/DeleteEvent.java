package pdir;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class DeleteEvent {

    private Date deleteDatetime;
    private Question question = new Question("", "delete");

    public DeleteEvent(Date date) {
        this.deleteDatetime = date;
        Options options = new Options();
        String[] opts = {"No reason", "Computer monitor/screen in photo", "I was asked to", "People in photo",
                "Participant was in photo", "Objects in photo", "Photo location", "Participant error", "Nudity", "Other"};

        for(String opt : opts) {
            options.addOption(new Option(opt));
        }
        question.addOptions(options);
    }

    public Date getDeleteDatetime() {
        return deleteDatetime;
    }

    public void setDeleteDatetime(Date deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        LinkedList l = new LinkedList();
        ArrayList<Option> ops = question.getOptions().getSelected();

        for(Option o: ops) {
            l.add(o.toString());
        }
        obj.put("id", "delete event");
        obj.put("options", l);
        obj.put("time", getDeleteDatetime().toString());


        return obj;
    }
}

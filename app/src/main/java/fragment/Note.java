package fragment;

import com.google.firebase.Timestamp;

public class Note {

    String judulNote;
    String isiNote;
    Timestamp timestamp;

    public Note(){
    }

    public String getJudulNote() {
        return judulNote;
    }

    public void setJudulNote(String judulNote) {
        this.judulNote = judulNote;
    }

    public String getIsiNote() {
        return isiNote;
    }

    public void setIsiNote(String isiNote) {
        this.isiNote = isiNote;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

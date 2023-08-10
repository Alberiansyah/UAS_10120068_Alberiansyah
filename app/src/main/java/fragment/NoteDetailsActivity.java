package fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uas_10120068_alberiansyah.R;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.uas_10120068_alberiansyah.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText judulNoteText, isiNoteText;
    ImageButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        judulNoteText = findViewById(R.id.judulNote);
        isiNoteText = findViewById(R.id.isiNote);
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener((v)-> saveNote());

    }

    void saveNote(){
        String judulNote = judulNoteText.getText().toString();
        String isiNote = isiNoteText.getText().toString();
        if(judulNote == null || judulNote.isEmpty()){
            judulNoteText.setError("Judul wajib diisi.");
            return;
        }

        Note note = new Note();
        note.setJudulNote(judulNote);
        note.setIsiNote(isiNote);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(NoteDetailsActivity.this, "Note berhasil disimpan.");
                    finish();
                }else{
                    Utility.showToast(NoteDetailsActivity.this, "Note gagal disimpan.");
                }
            }
        });

    }
}
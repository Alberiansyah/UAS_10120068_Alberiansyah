package fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uas_10120068_alberiansyah.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import adapter.FirebaseNotificationHandle;

import com.example.uas_10120068_alberiansyah.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText judulNoteText, isiNoteText;
    ImageButton saveButton;
    TextView judulText;
    String title, isi, docId;
    boolean isEditMode = false;
    Button hapusNoteButton;
    FirebaseNotificationHandle notificationHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        FirebaseMessaging.getInstance().subscribeToTopic("notes");

        notificationHandle = new FirebaseNotificationHandle(getApplicationContext());

        judulNoteText = findViewById(R.id.judulNote);
        isiNoteText = findViewById(R.id.isiNote);
        saveButton = findViewById(R.id.save);
        judulText = findViewById(R.id.judul);
        hapusNoteButton = findViewById(R.id.hapusNote);

        title = getIntent().getStringExtra("Judul");
        isi = getIntent().getStringExtra("Isi");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()){
            isEditMode = true;
        }

        judulNoteText.setText(title);
        isiNoteText.setText(isi);

        if(isEditMode){
            judulText.setText("Ubah note anda");
            hapusNoteButton.setVisibility(View.VISIBLE);
        }else{
            hapusNoteButton.setVisibility(View.GONE);
        }

        saveButton.setOnClickListener((v)->saveNote());
        hapusNoteButton.setOnClickListener((v)->hapusNote());

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
        if(isEditMode){
            //Mengubah note lama
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else{
            //Membuat note baru
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(NoteDetailsActivity.this, "Note berhasil disimpan.");
                    notificationHandle.showNotification(NoteDetailsActivity.this,"Catatan baru", "Judul : " + judulNoteText.getText());
                    finish();
                }else{
                    Utility.showToast(NoteDetailsActivity.this, "Note gagal disimpan.");
                }
            }
        });

    }

    void hapusNote(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(NoteDetailsActivity.this, "Note berhasil dihapus.");
                    finish();
                }else{
                    Utility.showToast(NoteDetailsActivity.this, "Note gagal dihapus.");
                }
            }
        });
    }
}
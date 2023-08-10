package fragment;

/**
 * NIM      : 10120068
 * Nama     : Alberiansyah
 * Kelas    : IF-2
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_10120068_alberiansyah.R;
import com.example.uas_10120068_alberiansyah.AddNote;
import com.example.uas_10120068_alberiansyah.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapter.NoteAdapter;
import database.DatabaseHelper;
import database.NoteInterfance;
import model.Note;

public class NoteFragment extends Fragment {

    private MainActivity mainActivity;
    private List<Note> note;
    private NoteInterfance noteInterface;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_note, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().hide();

        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.mynote);
        addButton = view.findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddNote.class));
        });

        read();
    }

    @Override
    public void onResume() {
        super.onResume();
        read();
    }

    private void read() {
        note = new ArrayList<Note>();
        noteInterface = new DatabaseHelper(getContext());
        Cursor cursor = noteInterface.read();
        if (cursor.moveToFirst()){
            do {
                Note n = new Note(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );

                note.add(n);
            } while (cursor.moveToNext());
        }

        noteAdapter = new NoteAdapter(note);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }
}

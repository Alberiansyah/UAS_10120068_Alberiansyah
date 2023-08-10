package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.uas_10120068_alberiansyah.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_10120068_alberiansyah.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class FragmentNote extends Fragment {

    FloatingActionButton tambahNote;
    RecyclerView recycler;
    ImageButton menuButton;
    NoteAdapter noteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        tambahNote = view.findViewById(R.id.tambahNote);
        recycler = view.findViewById(R.id.recycler);
        menuButton = view.findViewById(R.id.menuButton);


        tambahNote.setOnClickListener((v) -> startActivity(new Intent(getActivity(), NoteDetailsActivity.class)));
        menuButton.setOnClickListener((v)->showMenu());

        setupRecyclerView();
        return view;
    }

    void showMenu(){

    }

    void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>().setQuery(query, Note.class).build();
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        noteAdapter = new NoteAdapter(options, requireContext());
        recycler.setAdapter(noteAdapter);
    }

    public void onStart(){
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    public void onResume(){
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}

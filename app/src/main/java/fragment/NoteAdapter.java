package fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uas_10120068_alberiansyah.R;
import com.example.uas_10120068_alberiansyah.Utility;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {
    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.judulText.setText(note.judulNote);
        holder.isiNoteText.setText(note.isiNote);
        holder.timestampText.setText(Utility.timeStampToString(note.timestamp));
        
        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, NoteDetailsActivity.class);
            intent.putExtra("Judul", note.judulNote);
            intent.putExtra("Isi", note.isiNote);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);

        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView judulText, isiNoteText, timestampText;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            judulText = itemView.findViewById(R.id.judulNote);
            isiNoteText = itemView.findViewById(R.id.isiNote);
            timestampText = itemView.findViewById(R.id.timestamp);

        }
    }
}

package fragment;

/**
 * NIM      : 10120068
 * Nama     : Alberiansyah
 * Kelas    : IF-2
 */

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.uas_10120068_alberiansyah.Login;
import com.example.uas_10120068_alberiansyah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentInfo extends Fragment {

    FloatingActionButton tambahNote;
    RecyclerView recycler;
    ImageButton menuButton;
    NoteAdapter noteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        menuButton = view.findViewById(R.id.menuButton);
        menuButton.setOnClickListener((v)->showMenu());
        return view;
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(requireContext(), menuButton);
        popupMenu.getMenu().add("Keluar");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener((new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle() == "Keluar"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(requireContext(), Login.class));
                }
                return false;
            }
        }));
    }
}
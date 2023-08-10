package database;

/**
 * NIM      : 10120068
 * Nama     : Alberiansyah
 * Kelas    : IF-2
 */

import android.database.Cursor;

import model.Note;

public interface NoteInterfance {

    public Cursor read();
    public boolean create(Note note);
    public boolean update(Note note);
    public boolean delete(String id);
}

package com.example.legacy.database;

// Layer 1 — DB API
// Responsible only for raw SQLite operations. Has no knowledge of
// the Repository or ViewModel above it.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.legacy.models.Task;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class TaskDbHelper extends SQLiteOpenHelper implements TaskDataSource {

    private static final String DB_NAME    = "tasks.db";
    private static final int    DB_VERSION = 1;

    static final String TABLE         = "tasks";
    static final String COL_ID        = "_id";
    static final String COL_TITLE     = "title";
    static final String COL_DONE      = "done";       // 0 = false, 1 = true
    static final String COL_TIMESTAMP = "timestamp";

    // Hilt provides the application Context via @ApplicationContext.
    @Inject
    public TaskDbHelper(@ApplicationContext Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + TABLE + " ("
                + COL_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE     + " TEXT NOT NULL, "
                + COL_DONE      + " INTEGER NOT NULL DEFAULT 0, "
                + COL_TIMESTAMP + " INTEGER NOT NULL"
            + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // --- write operations ---

    public long insert(String title) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE,     title);
        cv.put(COL_DONE,      0);
        cv.put(COL_TIMESTAMP, System.currentTimeMillis());
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE, null, cv);
        db.close();
        return id;
    }

    public int setDone(long id, boolean done) {
        ContentValues cv = new ContentValues();
        cv.put(COL_DONE, done ? 1 : 0);
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.update(TABLE, cv, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public int delete(long id) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // --- read operations ---

    public List<Task> queryAll() {
        List<Task> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE, null, null, null, null, null,
                COL_TIMESTAMP + " DESC"
        );
        while (cursor.moveToNext()) {
            list.add(fromCursor(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }

    private Task fromCursor(Cursor c) {
        return new Task(
            c.getLong(c.getColumnIndexOrThrow(COL_ID)),
            c.getString(c.getColumnIndexOrThrow(COL_TITLE)),
            c.getInt(c.getColumnIndexOrThrow(COL_DONE)) == 1,
            c.getLong(c.getColumnIndexOrThrow(COL_TIMESTAMP))
        );
    }
}
package sergioandrade.blackjack.activities;

import android.content.ContentValues;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Sampler;

import sergioandrade.blackjack.R;
import sergioandrade.blackjack.database.DatabaseContract;
import sergioandrade.blackjack.database.DatabaseHelper;

public class WinActivity extends PortraitScreen {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        String username = getIntent().getStringExtra("player");
        saveInDB(username);
    }

    public void saveInDB(String username){
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        String[] projection = {DatabaseContract.FeedEntry.COLUMN_NAME_TITLE};

        Cursor cursor = db.query(DatabaseContract.FeedEntry.TABLE_NAME,
                projection,
                null,//selection,
                null,
                null,
                null,
                null,
                null);

        boolean encontre = false;
        while(cursor.moveToNext() && !encontre) {
            String item = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.COLUMN_NAME_TITLE));
            if((item != null) && (item.equals(username))) encontre = true;
        }
        db.close();
        if (!encontre) {
            db = new DatabaseHelper(this).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.FeedEntry.COLUMN_NAME_TITLE, username);
            values.put(DatabaseContract.FeedEntry.USER_COL_WIN, 1);
            db.insert(DatabaseContract.FeedEntry.TABLE_NAME, null, values);
            db.close();
        }
    }
}

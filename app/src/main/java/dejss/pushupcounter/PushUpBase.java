package dejss.pushupcounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dejss on 05.11.2017.
 */

public class PushUpBase extends SQLiteOpenHelper {

    public PushUpBase(Context context) {
        super(context, "push_up_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists Pushup (id integer primary key autoincrement, count integer, date text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package dejss.pushupcounter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Dejss on 07.11.2017.
 */
class SQLBase(context: Context) : SQLiteOpenHelper(context, "push_up_db", null, 1) {
    override fun onCreate(sql: SQLiteDatabase) {
        sql.execSQL("create table if not exists Push_ups (date text primary key, count integer);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
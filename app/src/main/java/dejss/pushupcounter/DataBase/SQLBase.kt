package dejss.pushupcounter.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Dejss on 07.11.2017.
 */
class SQLBase(context: Context) : SQLiteOpenHelper(context, "push_up_db", null, 2) {
    override fun onCreate(sql: SQLiteDatabase) {
        sql.execSQL("create table if not exists Push_ups (date text primary key, count integer, goal integer);")
    }

    override fun onUpgrade(sql: SQLiteDatabase, old_v: Int, new_v: Int) {

    }

}
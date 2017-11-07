package dejss.pushupcounter

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/**
 * Created by Dejss on 07.11.2017.
 */
class PushOperator{

    val base: SQLBase

    constructor(context: Context){
        base = SQLBase(context)
    }

    fun saveData(day: String, count: Int){
        var sql:SQLiteDatabase = base.writableDatabase
        val query = "INSERT or replace INTO Push_ups (date, count) VALUES('$day','$count')"
        Log.v("SQL_proc_insert", "Day - $day | Count - $count")
        sql.execSQL(query)
    }

    fun readDay(day:String):PushUp?{
        var sql:SQLiteDatabase = base.readableDatabase
        val query = "SELECT * FROM Push_ups WHERE date = ?"
        val args = Array<String>(1){day}
        val cursor:Cursor = sql.rawQuery(query, args)
        if(cursor.moveToFirst()){
            var day = cursor.getString(cursor.getColumnIndex("date"))
            var count = cursor.getInt(cursor.getColumnIndex("count"))
            Log.v("SQL_proc_load", "Day - $day | Count - $count")
            return PushUp(count, day)
        }
        return null
    }
}
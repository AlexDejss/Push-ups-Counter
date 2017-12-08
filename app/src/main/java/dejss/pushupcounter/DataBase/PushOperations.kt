package dejss.pushupcounter.DataBase

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.util.Log
import dejss.pushupcounter.TrainPref

/**
 * Created by Dejss on 07.11.2017.
 */
class PushOperations(context: Context) {

    private val base: SQLBase = SQLBase(context)

    fun saveDay(day: String, count: Int, goal: Int){
        var sql = base.writableDatabase
        val query = "INSERT or replace INTO Push_ups (date, count, goal) VALUES('$day','$count', '$goal')"
        sql.execSQL(query)

        Log.v("SQL_INSERT", "Day - $day | Count - $count | Goal - $goal")
    }

    fun prepareMonth(month: ArrayList<TrainPref>): Boolean{
        val sql = base.writableDatabase
        sql.beginTransaction()

        var savedDays = 0

        try {
            for(train in month){
                val query = "INSERT or replace INTO Push_ups (date, count, goal) VALUES('$train.day','$train.count', '$train.goal')"
                sql.execSQL(query)
                savedDays++
            }
        }catch ( e: SQLException){
            return false
        }

        Log.v("SQL_INSERT_MONTH", "$savedDays")

        if(savedDays == month.size)
            sql.setTransactionSuccessful()


        sql.endTransaction()
        return true

    }

    fun readDay(day:String): TrainPref?{
        var sql = base.readableDatabase

        val query = "SELECT * FROM Push_ups WHERE date = ?"
        val args = Array<String>(1){day}
        val cursor:Cursor = sql.rawQuery(query, args)

        if(cursor.moveToFirst()){

            var day = cursor.getString(cursor.getColumnIndex("date"))
            var count = cursor.getInt(cursor.getColumnIndex("count"))
            var goal = cursor.getInt(cursor.getColumnIndex("goal"))
            Log.v("SQL_proc_load", "Day - $day | Count - $count | Goal - $goal")

            cursor. close()
            return TrainPref(day, count, goal)
        }

        cursor.close()
        return null
    }

    fun readListDay(): ArrayList<TrainPref>{
        var sql = base.readableDatabase

        var list = ArrayList<TrainPref>()

        val query = "SELECT * FROM Push_ups"
        val cursor:Cursor = sql.rawQuery(query, Array<String>(0){""})

        if(cursor.moveToFirst()){
            do {

                var day = cursor.getString(cursor.getColumnIndex("date"))
                var count = cursor.getInt(cursor.getColumnIndex("count"))
                var goal = cursor.getInt(cursor.getColumnIndex("goal"))

                Log.v("SQL_proc_load_list", "Day - $day | Count - $count | Goal - $goal")

                list.add(TrainPref(day, count, goal))

            }while(cursor.moveToNext())

        }
        cursor.close()
        return list
    }


}
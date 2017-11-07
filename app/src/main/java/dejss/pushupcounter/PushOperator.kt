package dejss.pushupcounter

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/**
 * Created by Dejss on 07.11.2017.
 */
class PushOperator(val base: PushUpBase){

    fun saveData(day: String, count: Int){
        var sql:SQLiteDatabase = base.writableDatabase

    }

    private fun isExist(day: String, sql: SQLiteDatabase):Boolean{
        val query = "SELECT * FROM push_up_db WHERE date = ?"
        val args = Array<String>(1){day}
        val cursor: Cursor = sql.rawQuery(query, args)
        if(cursor.moveToFirst()){
            return true
        }
        return false
    }
}
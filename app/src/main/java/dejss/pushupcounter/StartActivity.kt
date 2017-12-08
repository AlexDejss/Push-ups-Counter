package dejss.pushupcounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dejss.pushupcounter.DataBase.PushOperations
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Thread{
            prepareApp()
        }.start()
    }

    private fun prepareApp(){
        checkBaseForDate()
    }

    //TODO replace the date present from dd-MM-yyyy to dd.MM.yyyy
    private fun checkBaseForDate(){
        val operations = PushOperations(this)
        val currentDay = SimpleDateFormat("dd.MM.yyyy", Locale.US).format(Date())
        val currDayPushUp = operations.readDay(currentDay)

        if(currDayPushUp == null){

            val year = SimpleDateFormat("yyyy", Locale.US).format(Date()).toInt()
            var month = SimpleDateFormat("MM", Locale.US).format(Date()).toInt()-1
            val day = SimpleDateFormat("dd", Locale.US).format(Date()).replace("([0])([\\d])", "$2").toInt()

            val dayOfMonth = GregorianCalendar(year, month, day).getActualMaximum(Calendar.DAY_OF_MONTH)

            var prepareMonth = ArrayList<TrainPref>()

            month++
            var dayNumber = 1
            while(dayNumber <= dayOfMonth){
                var day: String = if(dayNumber < 10) "0$dayNumber" else "$dayNumber" // WTF KOTLIN, WHAT ARE YOU SUGGEST TO USE? pervert...

                prepareMonth.add(TrainPref("$day.$month.$year", 0, 0))
                dayNumber++
            }

            for(train in prepareMonth)
                Log.v("PREPARE_MONTH", train.toString())

        }



    }
}



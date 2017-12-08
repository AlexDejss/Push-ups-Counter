package dejss.pushupcounter

import android.content.Intent
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
        val operations = PushOperations(this)

        checkBaseForDate(operations)

        val currentDayTrain = getCurrentDayTrain(operations)

        operations.debugLoadTableInConsole()

        loadMainActivity(currentDayTrain)


    }

    private fun checkBaseForDate(operations: PushOperations){
        val currentDay = SimpleDateFormat(Train.DATE_PATTERN, Locale.US).format(Date())
        val currDayPushUp = operations.readDay(currentDay)

        if(currDayPushUp == null){

            val year = SimpleDateFormat("yyyy", Locale.US).format(Date()).toInt()
            var month = SimpleDateFormat("MM", Locale.US).format(Date()).toInt()-1
            val day = SimpleDateFormat("dd", Locale.US).format(Date()).replace("([0])([\\d])", "$2").toInt()

            val dayOfMonth = GregorianCalendar(year, month, day).getActualMaximum(Calendar.DAY_OF_MONTH)

            val preparedMonth = ArrayList<TrainPref>()

            month++

            var dayNumber = 1
            while(dayNumber <= dayOfMonth){
                val monthDay: String = if(dayNumber < 10) "0$dayNumber" else "$dayNumber" // WTF KOTLIN, WHAT ARE YOU SUGGEST TO USE? pervert...

                preparedMonth.add(TrainPref("$monthDay.$month.$year", 0, 0))
                dayNumber++
            }
            //TODO handle if no each day was added
            operations.prepareMonth(preparedMonth)

            //DEBUG
            for(train in preparedMonth)
                Log.v("PREPARE_MONTH", train.toString())
        }
    }

    private fun getCurrentDayTrain(operations: PushOperations): TrainPref?{
        val currentDay = SimpleDateFormat(Train.DATE_PATTERN, Locale.US).format(Date())
        val currDayPushUp = operations.readDay(currentDay)
        if(currDayPushUp != null){
            return currDayPushUp
        }else{
            //TODO Handle error
            return null
        }
    }

    private fun loadMainActivity(currentDayTrain: TrainPref?){
        Log.v("PREPARE_DAY_TRAIN", currentDayTrain.toString())

        if(currentDayTrain != null) {

            val mainActivity = Intent(this, MainActivity::class.java)

            mainActivity.putExtra(Train.DATE, currentDayTrain.date)
            mainActivity.putExtra(Train.COUNT, currentDayTrain.count)
            mainActivity.putExtra(Train.GOAL, currentDayTrain.goal)

            runOnUiThread {
                startActivity(mainActivity)
            }
        }
        else{
            runOnUiThread {
                Log.v("PREPARE_GET_DAY", "null as a day. Exit from program")
                this.finish()
            }
        }
    }
}


